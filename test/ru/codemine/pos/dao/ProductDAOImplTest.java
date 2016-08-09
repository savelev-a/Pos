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

/**
 *
 * @author Alexander Savelev
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class ProductDAOImplTest 
{
    @Autowired
    private ProductDAO productDAO;
    
    private static final Logger log = Logger.getLogger("ProductDAOTest");
    
    @Test
    @Transactional
    public void testProductDao()
    {
        Product product = new Product("art001-test", "NewTestProduct", "123", 200.50);
        
        productDAO.create(product);
        
        Product p1 = productDAO.getByArtikul("art001-test");
        Assert.assertNotNull(p1);
        Assert.assertEquals("NewTestProduct", p1.getName());
        Assert.assertTrue(p1.getPrice().equals(200.50));
        
        p1.setBarcode("123456");
        productDAO.update(p1);
        
        Product p2 = productDAO.getById(Long.valueOf(1));
        Assert.assertNotNull(p2);
        
        Product p3 = productDAO.getByBarcode("123456");
        Assert.assertNotNull(p3);
        Assert.assertTrue(p3.getPrice().equals(200.50));
        
        List<Product> plist = productDAO.getAll();
        Assert.assertNotNull(plist);
        Assert.assertTrue(plist.size() > 0);
        
        
    }

}
