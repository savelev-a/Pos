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

import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.entity.Store;
import ru.codemine.pos.entity.User;
import ru.codemine.pos.entity.document.StartBalance;
import ru.codemine.pos.exception.GeneralException;

/**
 *
 * @author Alexander Savelev
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class StartBalanceServiceTest 
{
    @Autowired private StartBalanceService sbService;
    @Autowired private StoreService storeService;
    @Autowired private ProductService productService;
    @Autowired private UserService userService;
    @Autowired private Application app;
    
    private static final Logger log = Logger.getLogger("StartBalanceServiceTest");
    
    private StartBalance sb;
    private Store store;
    private User user;
    private Product p1;
    private Product p2;
    private Product p3;
    
    @Before
    public void init()
    {
        user = new User("TheTestUser", "TheTestUser");
        userService.create(user);
        app.setActiveUser(user);
        
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
        
        storeService.create(store);
        
        sb = new StartBalance(store);
    }
    
    @Test
    public void testSbService()
    {
        log.info("---Подготовка к тестированию StartBalanceService---");
        
        try
        {
            sbService.create(sb);
        } 
        catch (GeneralException ex)
        {
            log.error(ex.getLocalizedMessage());
            Assert.fail();
        }
        
        log.info("Проверка загрузки документа нач. остатков из БД...");
        StartBalance sb1 = sbService.getAllByStore(store).get(0);
        
        Assert.assertNotNull(sb1);
        log.info("...ok");
        
        
    }
    
    @After
    public void rollback()
    {
        sbService.delete(sb);
        
        storeService.delete(store);
        
        productService.delete(p1);
        productService.delete(p2);
        productService.delete(p3);
        
        userService.delete(user);
    }

}
