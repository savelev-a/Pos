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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.pos.dao.StoreDAO;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.entity.Store;
import ru.codemine.pos.exception.DuplicateStoreDataException;

/**
 *
 * @author Alexander Savelev
 */

@Service
public class StoreService 
{
    private static final Logger log = Logger.getLogger("StoreService");
    
    @Autowired
    private StoreDAO storeDAO;
    
    
    /**
     * Создает новый склад и сохраняет его в БД
     * @param store склад
     * @throws ru.codemine.pos.exception.DuplicateStoreDataException при совпадении уникальных данных склада
     */
    @Transactional
    public void create(Store store) throws DuplicateStoreDataException
    {
        if(store == null) return;
        
        if(storeDAO.getByName(store.getName()) != null) throw new DuplicateStoreDataException("Имя", store.getName());
        storeDAO.create(store);
    }
    
    /**
     * Удаляет из БД указанный склад
     * @param store склад
     */
    @Transactional
    public void delete(Store store)
    {
        storeDAO.delete(store);
    }
    
    /**
     * Удаляет из БД указанный склад
     * @param id идентификатор склада
     */
    @Transactional
    public void delete(Integer id)
    {
        storeDAO.delete(id);
    }
    
    
    /**
     * Обновляет склад в БД
     * @param store склад
     * @throws ru.codemine.pos.exception.DuplicateStoreDataException при совпадении уникальных данных склада
     */
    @Transactional
    public void update(Store store) throws DuplicateStoreDataException
    {
        if(store == null) return;
        
        Store testName = storeDAO.getByName(store.getName());
        if(testName != null && !testName.getId().equals(store.getId())) throw new DuplicateStoreDataException("Имя", store.getName());
        storeDAO.update(store);
    }
    
    /**
     * Возвращает склад по его названию
     * @param name название склада
     * @return
     */
    @Transactional
    public Store getByName(String name)
    {
        return storeDAO.getByName(name);
    }
    
    /**
     * Возвращает список всех складов
     * @return
     */
    @Transactional
    public List<Store> getAll()
    {
        return storeDAO.getAll();
    }
    
    /**
     * Проверяет имеется ли достаточно товара на складе
     * @param store склад
     * @param product товар
     * @param quantity запрашиваемое количество
     * @return истина, если товара больше или равно запрашиваемому кол-ву
     */
    @Transactional
    public boolean checkStocks(Store store, Product product, Integer quantity)
    {
        Store st = storeDAO.unproxyStocks(store);
        return st.getStocks().get(product) != null && st.getStocks().get(product) >= quantity;
    }
    
    /**
     * Проверяет имеется ли достаточно товара на складе
     * @param storeName название склада
     * @param product товар
     * @param quantity запрашиваемое количество
     * @return истина, если товара больше или равно запрашиваемому кол-ву
     */
    @Transactional
    public boolean checkStocks(String storeName, Product product, Integer quantity)
    {
        Store store = storeDAO.getByName(storeName);
        if(store == null) return false;
        
        Store st = storeDAO.unproxyStocks(store);
        return st.getStocks().get(product) != null && st.getStocks().get(product) >= quantity;
    }
    
    /**
     * Депроксирует и загружает данные по остаткам склада
     * @param store склад, остатки которого нужно депроксировать
     * @return склад с депроксированными остатками
     */
    @Transactional
    public Store unproxyStocks(Store store)
    {
        return storeDAO.unproxyStocks(store);
    }

    @Transactional
    public Integer getAvaibleStocksOnRetail(Product product)
    {
        Store store = storeDAO.getByName("Розница");
        if(store == null) return 0;
        
        Store st = storeDAO.unproxyStocks(store);
        return st.getStocks().get(product);
    }

}
