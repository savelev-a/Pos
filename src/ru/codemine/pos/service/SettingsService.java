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
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.pos.dao.SettingsDAO;
import ru.codemine.pos.entity.Settings;

/**
 *
 * @author Alexander Savelev
 */

@Service
public class SettingsService 
{
    private static final Logger log = Logger.getLogger("SettingsService");

    @Autowired
    private SettingsDAO settingsDAO;
    
    /**
     * Сохраняет или обновляет в БД заданную настройку
     * @param key ключ настройки
     * @param value значение настройки
     */
    @Transactional
    public void updateSettings(String key, String value)
    {
        Settings settings = settingsDAO.getByKey(key);
        if(settings == null)
        {
            settingsDAO.create(new Settings(key, value));
        }
        else
        {
            settings.setValue(value);
            settingsDAO.update(settings);
        }
    }
    
    /**
     * Сохраняет или обновляет в БД заданные настройки
     * @param settings пары ключ-значение
     */
    @Transactional
    public void updateSettings(Map<String, String> settings)
    {
        for(Map.Entry<String, String> entry : settings.entrySet())
        {
            Settings s = settingsDAO.getByKey(entry.getKey());
            if(s == null)
            {
                settingsDAO.create(new Settings(entry.getKey(), entry.getValue()));
            }
            else
            {
                s.setValue(entry.getValue());
                settingsDAO.update(s);
            }
        }
    }
    
    /**
     * Удаляет настройку из БД по ключу
     * @param key ключ
     */
    @Transactional
    public void deleteSettings(String key)
    {
        Settings settings = settingsDAO.getByKey(key);
        if(settings != null) settingsDAO.delete(settings);
    }
    
    /**
     * Возвращает значение настройки по ее ключу
     * @param key ключ настройки
     * @return значение настройки (строка)
     */
    @Transactional
    public String getByKey(String key)
    {
        Settings s = settingsDAO.getByKey(key);
        return s == null ? "" : s.getValue();
    }
    
    /**
     * Возвращает парами ключ-значение все сохраненные в БД настройки
     * @return пары ключ-значение
     */
    @Transactional
    public Map<String, String> getAll()
    {
        List<Settings> settingsList = settingsDAO.getAll();
        Map<String, String> result = new HashMap<>();
        for(Settings s : settingsList)
        {
            result.put(s.getKey(), s.getValue());
        }
        
        return result;
    }
}
