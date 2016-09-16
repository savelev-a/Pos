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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.pos.entity.Settings;
import ru.codemine.pos.entity.device.BarcodeScannerDevice;
import ru.codemine.pos.entity.device.KkmDevice;

/**
 *
 * @author Alexander Savelev
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class SettingsDAOTest 
{
    @Autowired
    private SettingsDAO settingsDAO;
    
    @Test
    @Transactional
    public void testSettingsDao()
    {
        Settings settings = new Settings();
        
        
        settingsDAO.saveSettings(settings);
        
        Settings s1 = settingsDAO.getSettings();
        Assert.assertNotNull(s1);
        Assert.assertTrue(s1.getId() == 1);
        Assert.assertNull(s1.getCurrentKkmDevice());
        Assert.assertNull(s1.getCurrentScannerDevice());
        
        
    }

}
