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

package ru.codemine.pos.dao;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.pos.dao.document.StartBalanceDAO;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.entity.Store;
import ru.codemine.pos.entity.User;
import ru.codemine.pos.entity.document.StartBalance;

/**
 *
 * @author Alexander Savelev
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class StartBalanceDAOImplTest 
{
    @Autowired private StartBalanceDAO startBalanceDAO;
    @Autowired private ProductDAO productDAO;
    @Autowired private StoreDAO storeDAO;
    @Autowired private UserDAO userDAO;
    
    private static final Logger log = Logger.getLogger("SBDAOTest");
    
    @Test
    @Transactional
    public void testStartBalanceDAO()
    {
        log.info("--Подготовка к тестированию StartBalanceDAO---");
        User user = new User("testuser001", "testuser001");
        userDAO.create(user);
        
        Store store = new Store("TestStore");
        storeDAO.create(store);
        
        Product p1 = new Product("art001", "name01", "123456", 10.0);
        Product p2 = new Product("art002", "name02", "223456", 20.0);
        Product p3 = new Product("art003", "name03", "323456", 40.0);
        
        productDAO.create(p1);
        productDAO.create(p2);
        productDAO.create(p3);
        
        StartBalance sb = new StartBalance(store);
        sb.getContents().put(p1, 2);
        sb.getContents().put(p2, 4);
        sb.setCreationTime(DateTime.now());
        sb.setDocumentTime(DateTime.now());
        sb.setCreator(user);
        sb.setProcessed(true);
        
        startBalanceDAO.create(sb);
        
        log.info("Тестирование загрузки из БД...");
        StartBalance sb1 = startBalanceDAO.getByStore(store);
        Assert.assertNotNull(sb1);
        Assert.assertTrue(sb1.getContents().get(p1) == 2);
        log.info("...ok");
        
        log.info("Проверка обновления документа...");
        sb.getContents().put(p3, 6);
        startBalanceDAO.update(sb);
        StartBalance sb2 = startBalanceDAO.getByStore(store);
        Assert.assertNotNull(sb2);
        Assert.assertTrue(sb2.getContents().get(p3) == 6);
        log.info("...ok");
        
        log.info("Проверка удаления документа...");
        startBalanceDAO.delete(sb);
        StartBalance sb3 = startBalanceDAO.getByStore(store);
        Assert.assertNull(sb3);
        log.info("...ok");
        
    }
}
