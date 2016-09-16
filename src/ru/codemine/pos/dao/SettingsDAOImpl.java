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

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.codemine.pos.entity.Settings;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class SettingsDAOImpl implements SettingsDAO
{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Settings getSettings()
    {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Settings s WHERE s.id = 1");
        
        return (Settings)query.uniqueResult();
    }

    @Override
    public void saveSettings(Settings settings)
    {
        Query deleteQuery = sessionFactory.getCurrentSession().createQuery("DELETE FROM Settings");
        deleteQuery.executeUpdate();
        
        sessionFactory.getCurrentSession().save(settings);
        
    }

}
