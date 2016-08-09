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
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import ru.codemine.pos.entity.User;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class UserDAOImpl extends GenericDAOImpl<User, Integer> implements UserDAO
{

    @Override
    public User getByUsername(String username)
    {
        Query query = getSession().createQuery("FROM User u WHERE u.username = :username");
        query.setString("username", username);
        
        return (User)query.uniqueResult();
    }
    
    @Override
    public List<User> getActive()
    {
        Query query = getSession().createQuery("FROM User u WHERE u.active = true");
        
        return query.list();
    }

    @Override
    public List<User> getAll()
    {
        Query query = getSession().createQuery("FROM User");
        
        return query.list();
    }

}
