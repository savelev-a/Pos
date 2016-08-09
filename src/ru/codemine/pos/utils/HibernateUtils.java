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

package ru.codemine.pos.utils;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

/**
 *
 * @author Alexander Savelev
 */
public class HibernateUtils 
{
    public static <T> T initAndUnproxy(T entity)
    {
        if (entity == null) throw new NullPointerException("Переданный класс сущности является null!");
        
        Hibernate.initialize(entity);
        if(entity instanceof HibernateProxy)
        {
            entity = (T)((HibernateProxy)entity).getHibernateLazyInitializer().getImplementation();
        }
        
        return entity;
    }
}
