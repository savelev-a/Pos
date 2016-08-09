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

import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.entity.Store;

/**
 *
 * @author Alexander Savelev
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class StoreDAOImplTest 
{
    @Autowired
    private StoreDAO storeDAO;
    
    @Autowired
    private ProductDAO productDAO;
    
    private static final Logger log = Logger.getLogger("StoreDAOTest");
    
    @Test
    @Transactional
    public void TestStoreDAO()
    {
        Store store1 = new Store("NewStore");
        Product p1 = new Product("artikul001", "TheNewProduct", "123456", 120.0);
        Product p2 = new Product("artikul002", "TheNewestProduct", "654321", 220.0);
        
        productDAO.create(p1);
        productDAO.create(p2);
        
        store1.getStocks().put(p1, 12);
        store1.getStocks().put(p2, 22);
        
        storeDAO.create(store1);
        
        List<Store> allstores = storeDAO.getAll();
        Assert.assertNotNull(allstores);
        Assert.assertTrue(allstores.size() > 0);
        
        Store retrievedStore = storeDAO.getByName("NewStore");
        Assert.assertNotNull(retrievedStore);
        Assert.assertNotNull(retrievedStore.getStocks());
        Assert.assertTrue(retrievedStore.getStocks().get(p2) == 22);
    }
}
