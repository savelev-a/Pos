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

package ru.codemine.pos.dao.document;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import ru.codemine.pos.dao.GenericDAOImpl;
import ru.codemine.pos.entity.Store;
import ru.codemine.pos.entity.document.StartBalance;
import ru.codemine.pos.utils.HibernateUtils;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class StartBalanceDAOImpl extends GenericDAOImpl<StartBalance, Long> implements StartBalanceDAO
{

    @Override
    public StartBalance getByStore(Store store)
    {
        Query query = getSession().createQuery("FROM StartBalance sb WHERE sb.store.id = :idstore AND sb.processed = true");
        query.setInteger("idstore", store.getId());
        
        List<StartBalance> list = query.list();
        
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<StartBalance> getAllByStore(Store store)
    {
        Query query = getSession().createQuery("FROM StartBalance sb WHERE sb.store.id = :idstore");
        query.setInteger("idstore", store.getId());
        
        return query.list();
    }

    @Override
    public List<StartBalance> getAll()
    {
        Query query = getSession().createQuery("FROM StartBalance");
        
        return query.list();
    }

    @Override
    public StartBalance unproxyContents(StartBalance sb)
    {
        StartBalance startBalance = (StartBalance)getSession().merge(sb);
        HibernateUtils.initAndUnproxy(startBalance.getContents());
        
        return startBalance;
    }

}
