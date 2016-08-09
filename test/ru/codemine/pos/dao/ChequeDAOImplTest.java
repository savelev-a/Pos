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
import ru.codemine.pos.dao.document.ChequeDAO;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.entity.Store;
import ru.codemine.pos.entity.User;
import ru.codemine.pos.entity.Workday;
import ru.codemine.pos.entity.document.Cheque;

/**
 *
 * @author Alexander Savelev
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class ChequeDAOImplTest 
{
    @Autowired private ChequeDAO chequeDAO;
    @Autowired private ProductDAO productDAO;
    @Autowired private StoreDAO storeDAO;
    @Autowired private UserDAO userDAO;
    @Autowired private WorkdayDAO workdayDAO;
    
    private static final Logger log = Logger.getLogger("ChequeDAOTest");
    
    @Test
    @Transactional
    public void testChequeDAO()
    {
        log.info("--Подготовка к тестированию ChequeDAO---");
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
        
        if(workdayDAO.getOpen() != null) Assert.fail("Закройте активную смену для этого теста!");
        
        Workday wd = new Workday();
        wd.setOpen(true);
        wd.setOpenTime(DateTime.now());
        wd.setOpenUser(user);
        workdayDAO.create(wd);
        
        Cheque cheque = new Cheque(wd);
        cheque.setCreationTime(DateTime.now());
        cheque.setCreator(user);
        cheque.setDocumentTime(DateTime.now());
        cheque.setActive(true);
        cheque.getContents().put(p1, 5);
        cheque.getContents().put(p2, 4);
        
        chequeDAO.create(cheque);
        
        log.info("Тестирование загрузки из открытой смены в БД...");
        Cheque c1 = chequeDAO.getByOpenWorkday().get(0);
        Assert.assertNotNull(c1);
        Assert.assertTrue(c1.getContents().get(p1) == 5);
        log.info("...ok");
        
        log.info("Тестирование загрузки из закрытой смены в БД...");
        wd.setCloseTime(DateTime.now());
        wd.setCloseUser(user);
        wd.setOpen(false);
        workdayDAO.update(wd);
        Cheque c2 = chequeDAO.getByWorkday(wd).get(0);
        Assert.assertNotNull(c2);
        Assert.assertTrue(c2.getContents().get(p2) == 4);
        log.info("...ok");
        
    }

}
