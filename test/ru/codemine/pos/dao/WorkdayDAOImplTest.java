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
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.pos.entity.User;
import ru.codemine.pos.entity.Workday;

/**
 *
 * @author Alexander Savelev
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class WorkdayDAOImplTest 
{
    @Autowired
    private WorkdayDAO workdayDAO;
    
    @Autowired
    private UserDAO userDAO;
    
    private static final Logger log = Logger.getLogger("WorkdayDAOTest");
    
    @Test
    @Transactional
    public void testWorkdayDao()
    {
        
        if(workdayDAO.getOpen() != null) Assert.fail("Закройте активную смену для этого теста!");
        
        User user = new User("workdaytestuser", "workdaytestuser");
        userDAO.create(user);
        
        Workday day1 = new Workday();
        day1.setOpenTime(DateTime.now());
        day1.setCloseTime(DateTime.now().plusDays(1));
        day1.setOpenUser(user);
        day1.setCloseUser(user);
        day1.setOpen(true);
        
        workdayDAO.create(day1);
        

        Workday retrWorkday = workdayDAO.getOpen();
        Assert.assertNotNull(retrWorkday);
        Assert.assertTrue(retrWorkday.getCloseTime().equals(day1.getCloseTime()));
        log.info("Выборка открытой смены: ОК");
        
        
        List<Workday> workdays = workdayDAO.getAll();
        Assert.assertNotNull(workdays);
        Assert.assertTrue(workdays.size() >= 1);
        log.info("Выборка всех смен: ОК");
        
        log.info("Тест выборки смен завершен");
    }

}
