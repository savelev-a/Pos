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

import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.entity.Store;

/**
 *
 * @author Alexander Savelev
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class StoreServiceTest 
{
    @Autowired private StoreService storeService;
    @Autowired private ProductService productService;
    
    private static final Logger log = Logger.getLogger("StoreServiceTest");
    
    private Store store;
    private Product p1;
    private Product p2;
    private Product p3;
    
    
    @Before
    public void init()
    {
        store = new Store("TheTestStore");
        
        p1 = new Product("art001", "name01", "123456", 10.0);
        p2 = new Product("art002", "name02", "223456", 20.0);
        p3 = new Product("art003", "name03", "323456", 40.0);
        
        try
        {
            productService.create(p1);
            productService.create(p2);
            productService.create(p3);
        } 
        catch (Exception e)
        {
            log.info(e.getLocalizedMessage());
        }
        
        store.getStocks().put(p1, 1);
        store.getStocks().put(p2, 5);
    }
    
    @Test
    public void testStoreService()
    {
        log.info("---Подготовка к тестированию StoreService---");
        
        storeService.create(store);
        
        log.info("Проверка загрузки склада из БД...");
        Store s1 = storeService.getByName("TheTestStore");
        Store unproxedS1 = storeService.unproxyStocks(s1);
        Assert.assertNotNull(unproxedS1);
        Assert.assertTrue(unproxedS1.getStocks().get(p1) == 1);
        log.info("...ok");
        
        log.info("Проверка обновления остатков склада в БД...");
        store.getStocks().put(p3, 10);
        storeService.update(store);
        Store s2 = storeService.getByName("TheTestStore");
        Store unproxedS2 = storeService.unproxyStocks(s2);
        Assert.assertNotNull(unproxedS2);
        Assert.assertTrue(unproxedS2.getStocks().get(p3) == 10);
        log.info("...ok");
        
    }
    
    @After
    public void rollback()
    {
        storeService.delete(store);
        
        productService.delete(p1);
        productService.delete(p2);
        productService.delete(p3);
    }
}
