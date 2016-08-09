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
import ru.codemine.pos.entity.User;

/**
 *
 * @author Alexander Savelev
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class UserDAOImplTest 
{
    
    @Autowired
    private UserDAO userDAO;
    
    @Test
    @Transactional
    public void testUserDAO()
    {
        User user = new User("testUser", "testUserPrintname");
        userDAO.create(user);
        
        User gotUser = userDAO.getByUsername("testUser");
        Assert.assertNotNull(gotUser);
        Assert.assertEquals("testUserPrintname", gotUser.getPrintName());
        
        User byIdUser = userDAO.getById(1);
        Assert.assertNotNull(byIdUser);
        
        List<User> allUsers = userDAO.getAll();
        Assert.assertNotNull(allUsers);
        Assert.assertTrue(allUsers.size() > 0);
        
        
    }
    

}
