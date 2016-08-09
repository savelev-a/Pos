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

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.codemine.pos.entity.User;

/**
 *
 * @author Alexander Savelev
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserServiceTest 
{
    @Autowired
    private UserService userService;
    
    private static final Logger log = Logger.getLogger("UserServiceTest");
    
    private User user;
    
    @Before
    public void init()
    {
        user = new User("testuser", "testuser");
        user.setPassword("123qweAS");
    }
    
    @Test
    public void testLogin()
    {
        userService.create(user);
        
        Assert.assertTrue(userService.performLogin("testuser", "123qweAS"));
        Assert.assertFalse(userService.performLogin("testuser", "wrongpass"));
        Assert.assertFalse(userService.performLogin("wronguser", "123qweAS"));
        
        user.setActive(false);
        userService.update(user);
        
        Assert.assertFalse(userService.performLogin("testuser", "123qweAS"));
        
        user.setActive(true);
        userService.update(user);
        
        Assert.assertTrue(userService.performLogin("testuser", "123qweAS"));
    }
    
    @After
    public void rollback()
    {
        userService.delete(user);
    }
}
