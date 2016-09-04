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
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.stereotype.Repository;
import ru.codemine.pos.dao.GenericDAOImpl;
import ru.codemine.pos.entity.Workday;
import ru.codemine.pos.entity.document.Cheque;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class ChequeDAOImpl extends GenericDAOImpl<Cheque, Long> implements ChequeDAO
{

    @Override
    public List<Cheque> getByWorkday(Workday wd)
    {
        Query query = getSession().createQuery("FROM Cheque c WHERE c.workday.id = :idworkday");
        query.setLong("idworkday", wd.getId());
        
        return query.list();
    }

    @Override
    public List<Cheque> getByOpenWorkday()
    {
        Query query = getSession().createQuery("FROM Cheque c WHERE c.workday.open = true");
        
        return query.list();
    }

    @Override
    public List<Cheque> getByPeriod(LocalDate startDate, LocalDate endDate)
    {
        endDate = endDate.plusDays(1); // по конечную дату включительно
        
        Query query = getSession().createQuery("FROM Cheque c WHERE c.creationTime BETWEEN :start AND :end");
        query.setDate("start", startDate.toDate());
        query.setDate("end", endDate.toDate());
        
        return query.list();
    }

}
