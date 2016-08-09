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

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Alexander Savelev
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SettingsServiceTest 
{
    @Autowired
    private SettingsService settingsService;
    
    @Before
    public void init()
    {
        
    }
    
    @Test
    public void testSettings()
    {
        settingsService.updateSettings("test_key", "TheTestValue");
        String s1 = settingsService.getByKey("test_key");
        Assert.assertNotNull(s1);
        Assert.assertTrue("TheTestValue".equals(s1));
        
        settingsService.updateSettings("test_key", "TheAnotherValue");
        String s2 = settingsService.getByKey("test_key");
        Assert.assertNotNull(s2);
        Assert.assertTrue("TheAnotherValue".equals(s2));
        
        Map<String, String> settingsMap = new HashMap<>();
        settingsMap.put("test_key2", "123");
        settingsMap.put("test_key3", "345");
        settingsMap.put("test_key", "000");
        settingsService.updateSettings(settingsMap);
        String s3 = settingsService.getByKey("test_key");
        Assert.assertNotNull(s3);
        Assert.assertTrue("000".equals(s3));
        
        Map<String, String> allSettings = settingsService.getAll();
        Assert.assertNotNull(allSettings);
        Assert.assertTrue(allSettings.size() >= 3);
        
    }
    
    @After
    public void rollback()
    {
        settingsService.deleteSettings("test_key");
        settingsService.deleteSettings("test_key2");
        settingsService.deleteSettings("test_key3");
    }
    

}
