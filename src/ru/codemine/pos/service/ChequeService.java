/*
 * Copyright (C) 2016 Alexander Savelev
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package ru.codemine.pos.service;

import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.dao.StoreDAO;
import ru.codemine.pos.dao.WorkdayDAO;
import ru.codemine.pos.dao.document.ChequeDAO;
import ru.codemine.pos.entity.Store;
import ru.codemine.pos.entity.Workday;
import ru.codemine.pos.entity.document.Cheque;
import ru.codemine.pos.entity.document.ChequeLine;
import ru.codemine.pos.exception.ChequeProcessByKkmException;
import ru.codemine.pos.exception.DocumentAlreadyActiveException;
import ru.codemine.pos.exception.KkmException;
import ru.codemine.pos.exception.NotEnoughGoodsException;
import ru.codemine.pos.exception.WorkdayNotOpenedException;
import ru.codemine.pos.service.kkm.Kkm;

/**
 *
 * @author Alexander Savelev
 */

@Service
public class ChequeService 
{
    @Autowired private ChequeDAO chequeDAO;
    @Autowired private WorkdayDAO workdayDAO;
    @Autowired private Application application;
    @Autowired private StoreDAO storeDAO;
    
    /**
     * Пробивает чек без ККМ. 
     * Используется для устранения расхождений, когда чек прошел по ККМ, и не прошел по базе.
     * Чек пробивается в открытую смену, время пробития устанавливается на момент обработки.
     * @param cheque чек для проведения
     * @throws WorkdayNotOpenedException если не открыта смена
     * @throws NotEnoughGoodsException если недостаточно товара на складе
     * @throws DocumentAlreadyActiveException если это попытка провести уже пробитый чек
     */
    @Transactional
    public void checkoutWithoutKKM(Cheque cheque) throws WorkdayNotOpenedException, NotEnoughGoodsException, DocumentAlreadyActiveException
    {
        // Подготовка - проверка валидности чека, наличия смены, итд //
        boolean checkoutNewCheque = true; // истина - пробивается новый чек, ложь - ранее сохраненный
        
        Workday currentWorkday = workdayDAO.getOpen();
        if(currentWorkday == null) throw new WorkdayNotOpenedException();
        
        if(cheque.getId() != null && chequeDAO.getById(cheque.getId()) != null)
        {
            if(chequeDAO.getById(cheque.getId()).isProcessed())
                throw new DocumentAlreadyActiveException();
            else
                checkoutNewCheque = false; //Чек в базе имеется, но не проведен
        }
        Store retailStore = storeDAO.getByName("Розница");
        for(ChequeLine line : cheque.getContent())
        {
            Integer quantityAfterProcess = retailStore.getStocks().get(line.getProduct()) - line.getQuantity();
            if(quantityAfterProcess < 0)
            {
                throw new NotEnoughGoodsException(retailStore, line.getProduct(), quantityAfterProcess);
            }
            else
            {
                // Без ККМ можно сразу уменьшать остатки на складе
                retailStore.getStocks().put(line.getProduct(), quantityAfterProcess);
                if(retailStore.getStocks().get(line.getProduct()) == 0)
                    retailStore.getStocks().remove(line.getProduct());
            }
        }
        
        // Заполнение метаданных чека //
        cheque.setCreationTime(DateTime.now());
        cheque.setDocumentTime(cheque.getCreationTime());
        cheque.setCreator(application.getActiveUser());
        cheque.setWorkday(currentWorkday);
        cheque.setProcessed(true); 
        cheque.recalculateCheque();
        
        currentWorkday.setTotal(currentWorkday.getTotal() + cheque.getChequeTotal());
        
        // Сохранение чека
        if(checkoutNewCheque) 
            chequeDAO.create(cheque);
        else
            chequeDAO.update(cheque);

        // Сохранение остатков
        storeDAO.update(retailStore);
    }
    
    @Transactional
    public void checkoutWithKKM(Cheque cheque, Kkm kkm) throws WorkdayNotOpenedException, DocumentAlreadyActiveException, NotEnoughGoodsException, ChequeProcessByKkmException
    {
       // Подготовка - проверка валидности чека, наличия смены, итд //
        
        Workday currentWorkday = workdayDAO.getOpen();
        if(currentWorkday == null) throw new WorkdayNotOpenedException();
        
        if(cheque.getId() != null && chequeDAO.getById(cheque.getId()) != null)
            throw new DocumentAlreadyActiveException();

        Store retailStore = storeDAO.getByName("Розница");
        for(ChequeLine line : cheque.getContent())
        {
            Integer quantityAfterProcess = retailStore.getStocks().get(line.getProduct()) - line.getQuantity();
            if(quantityAfterProcess < 0)
            {
                throw new NotEnoughGoodsException(retailStore, line.getProduct(), quantityAfterProcess);
            }
            else
            {
                // Уменьшение остатков и удаление пустых позиций
                retailStore.getStocks().put(line.getProduct(), quantityAfterProcess);
                if(retailStore.getStocks().get(line.getProduct()) == 0)
                    retailStore.getStocks().remove(line.getProduct());
            }

        }
        
        // Заполнение метаданных чека //
        cheque.setCreationTime(DateTime.now());
        cheque.setDocumentTime(cheque.getCreationTime());
        cheque.setCreator(application.getActiveUser());
        cheque.setWorkday(currentWorkday);
        cheque.setProcessed(true); 
        cheque.recalculateCheque();
        
        currentWorkday.setTotal(currentWorkday.getTotal() + cheque.getChequeTotal());
        
        //Пробитие чека по ККМ
        try
        {
            kkm.printCheque(cheque);
        } 
        catch (KkmException e)
        {
            storeDAO.evict(retailStore);
            workdayDAO.evict(currentWorkday);
            throw new ChequeProcessByKkmException(e.getLocalizedMessage());
        }
        
        
        // Сохранение чека
        chequeDAO.create(cheque);

        // Сохранение остатков
        storeDAO.update(retailStore);
    }
    
    /**
     * Возвращает все чеки заданной смены
     * @param wd смена (открытая или закрытая)
     * @return список чеков
     */
    @Transactional
    public List<Cheque> getByWorkday(Workday wd)
    {
        return chequeDAO.getByWorkday(wd);
    }
    
    /**
     * Возвращает список чеков активной смены
     * @return чписок чеков
     */
    @Transactional
    public List<Cheque> getByOpenWorkday()
    {
        return chequeDAO.getByOpenWorkday();
    }

    @Transactional
    public List<Cheque> getByPeriod(LocalDate startDate, LocalDate endDate)
    {
        return chequeDAO.getByPeriod(startDate, endDate);
    }
    
    @Transactional
    public List<Cheque> getByPeriod(LocalDate startDate, LocalDate endDate, Cheque.PaymentType type)
    {
        return chequeDAO.getByPeriod(startDate, endDate, type);
    }
    
}
