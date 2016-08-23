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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.dao.ProductDAO;
import ru.codemine.pos.dao.StoreDAO;
import ru.codemine.pos.dao.document.StartBalanceDAO;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.entity.Store;
import ru.codemine.pos.entity.User;
import ru.codemine.pos.entity.document.Document;
import ru.codemine.pos.entity.document.StartBalance;
import ru.codemine.pos.exception.ActiveDocumentEditException;
import ru.codemine.pos.exception.DuplicateProcessedDocumentException;
import ru.codemine.pos.exception.GeneralException;
import ru.codemine.pos.exception.NegativeQuantityOnDeactivateException;

/**
 *
 * @author Alexander Savelev
 */

@Service
public class StartBalanceService
{
    @Autowired
    private StartBalanceDAO sbDAO;
    
    @Autowired
    private StoreDAO storeDAO;
    
    @Autowired
    private ProductDAO productDAO;
    
    @Autowired
    private Application application;
      
    @Transactional
    public void create(StartBalance sb) throws GeneralException
    {
        User currentUser = application.getActiveUser();
        if(currentUser == null) throw new GeneralException("Отсутствует зарегистрированный пользователь");
        
        sb.setProcessed(false);
        sb.setCreationTime(DateTime.now());
        if(sb.getDocumentTime() == null) sb.setDocumentTime(sb.getCreationTime());
        sb.setCreator(currentUser);
        
        sb.setTotal(0.0);
        for(Map.Entry<Product, Integer> entry : sb.getContents().entrySet())
        {
            sb.setTotal(sb.getTotal() + entry.getKey().getPrice() * entry.getValue());
        }
        
        sbDAO.create(sb);
    }
    
    @Transactional
    public void delete(StartBalance sb)
    {
        sbDAO.delete(sb);
    }
    
    
    @Transactional
    public void update(StartBalance sb) throws ActiveDocumentEditException 
    {
        if(sb.isProcessed()) throw new ActiveDocumentEditException();
        
        sb = sbDAO.unproxyContents(sb);
        sb.setTotal(0.0);
        for(Map.Entry<Product, Integer> entry : sb.getContents().entrySet())
        {
            sb.setTotal(sb.getTotal() + entry.getKey().getPrice() * entry.getValue());
        }
        
        sbDAO.update(sb);
    }
    
    @Transactional
    public Document getById(Long id)
    {
        return sbDAO.getById(id);
    }
    
    @Transactional
    public List<StartBalance> getAllByStore(Store store)
    {
        return sbDAO.getAllByStore(store);
    }
    
    @Transactional
    public StartBalance getByStore(Store store)
    {
        return sbDAO.getByStore(store);
    }

    @Transactional
    public void process(StartBalance sb) throws DuplicateProcessedDocumentException
    {
        Store store = sb.getStore();
        
        StartBalance test = sbDAO.getByStore(store);
        if(test != null) throw new DuplicateProcessedDocumentException(
                "Текущий документ начальных остатков по данному складу: №" 
                + test.getId() + " от " + test.getDocumentTime().toString("dd.MM.YY"));
        if(sb.isProcessed()) return;
        
        store = storeDAO.unproxyStocks(store);
        sb = sbDAO.unproxyContents(sb);
        
        for(Map.Entry<Product, Integer> docStocks : sb.getContents().entrySet())
        {
            Product product = docStocks.getKey();
            if(store.getStocks().containsKey(product))
            {
                //Если данная позиция уже есть на складе - обновляем ее, добавляя значение из документа
                store.getStocks().put(product, 
                        store.getStocks().get(product) + docStocks.getValue());
            }
            else
            {
                store.getStocks().put(product, docStocks.getValue());
            }
        }
        
        sb.setProcessed(true);
        
        sbDAO.update(sb);
        storeDAO.update(store);
        
    }

    @Transactional
    public void unprocess(StartBalance sb) throws GeneralException, NegativeQuantityOnDeactivateException
    {
        Store store = sb.getStore();
        
        if(!sb.isProcessed()) return;
        
        store = storeDAO.unproxyStocks(store);
        sb = sbDAO.unproxyContents(sb);
        
        for(Map.Entry<Product, Integer> docStocks : sb.getContents().entrySet())
        {
            Product product = docStocks.getKey();
            if(store.getStocks().containsKey(product))
            {
                if(store.getStocks().get(product) >= docStocks.getValue())
                {
                    //Если хватает остатков на складе - уменьшаем кол-во
                    store.getStocks().put(product, 
                            store.getStocks().get(product) - docStocks.getValue());
                    //Если остаток ноль - убираем позицию
                    if(store.getStocks().get(product) == 0)
                        store.getStocks().remove(product);
                }
                else
                {
                    throw new NegativeQuantityOnDeactivateException(product, docStocks.getValue(), store.getStocks().get(product));
                }
            }
            else
            {
                throw new NegativeQuantityOnDeactivateException("Ошибка склада: " + store.getName() 
                        + ". В документе обнаружен артикул, отсутствующий на складе. Артикул: " 
                        + product.getArtikul());
            }
        }
        
        sb.setProcessed(false);
        
        sbDAO.update(sb);
        storeDAO.update(store);
    }
    
    /**
     * Депроксирует и загружает данные по содержимому документа
     * @param sb Документ начальных остатков, содержимое которого нужно депроксировать
     * @return документ с депроксированным содержимым
     */
    @Transactional
    public StartBalance unproxyContents(StartBalance sb)
    {
        return sbDAO.unproxyContents(sb);
    }

    @Transactional
    public StartBalance loadFromCsv(StartBalance sb, String filename) throws IOException
    {
        sb.setContents(new HashMap<Product, Integer>());
        sb.setTotal(0.0);
        
        InputStream is = new FileInputStream(filename);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        String errors = "";
        int lineNumber = 0;

        
        while((line = br.readLine()) != null)
        {
            lineNumber++;
            
            String[] lineParts = line.split(";");
            if(lineParts.length != 5)
            {
                errors = errors + "Ошибка обработки строки " + String.valueOf(lineNumber) + ": неверная длина \n";
                continue;
            }
            
            String artikulStr = lineParts[0];
            String barcodeStr = lineParts[1];
            String nameStr = lineParts[2];
            String priceStr = lineParts[3];
            String quantStr = lineParts[4];
            
            double priceDouble;
            int quantInt;
            
            try
            {
                priceDouble = Double.parseDouble(priceStr);
            } 
            catch (Exception e)
            {
                errors = errors + "Ошибка обработки строки " + String.valueOf(lineNumber) + ": неверная цена \n";
                continue;
            }
            
            try
            {
                quantInt = Integer.parseInt(quantStr);
            } 
            catch (Exception e)
            {
                errors = errors + "Ошибка обработки строки " + String.valueOf(lineNumber) + ": неверное количество \n";
                continue;
            }

            Product product = new Product(artikulStr, nameStr, barcodeStr, priceDouble);
            Product testArtikul = productDAO.getByArtikul(artikulStr);
            Product testBarcode = productDAO.getByBarcode(barcodeStr);
            if(testArtikul == null && testBarcode == null)
            {
                // Товара нет в базе
                productDAO.evict(testArtikul);
                productDAO.evict(testBarcode);
                
                productDAO.create(product);
            }
            else
            {
                // Товар в базе уже есть
                product = testArtikul;
                productDAO.evict(testBarcode);
            }
            
            sb.getContents().put(product, quantInt);
            sb.setTotal(sb.getTotal() + priceDouble * quantInt);
            

        }

        System.out.println(errors);
        
        return sb;
    }

}
