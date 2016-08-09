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
import ru.codemine.pos.entity.Workday;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class WorkdayDAOImpl extends GenericDAOImpl<Workday, Long> implements WorkdayDAO
{

    @Override
    public Workday getOpen()
    {
        Query query = getSession().createQuery("FROM Workday w WHERE w.open = true");
        List<Workday> list = query.list();
        
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Workday> getAll()
    {
        Query query = getSession().createQuery("FROM Workday");
        
        return query.list();
    }

}
