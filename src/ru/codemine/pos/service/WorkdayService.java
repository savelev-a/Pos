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

import java.util.List;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.dao.WorkdayDAO;
import ru.codemine.pos.dao.document.ChequeDAO;
import ru.codemine.pos.entity.User;
import ru.codemine.pos.entity.Workday;
import ru.codemine.pos.entity.document.Cheque;
import ru.codemine.pos.exception.GeneralException;
import ru.codemine.pos.exception.WorkdayAlreadyOpenedException;

/**
 *
 * @author Alexander Savelev
 */

@Service
public class WorkdayService 
{
    private static final Logger log = Logger.getLogger("WorkdayService");
    
    @Autowired
    private WorkdayDAO workdayDAO;
    
    @Autowired private ChequeDAO chequeDAO;
    
    @Autowired
    private Application application;
    
    @Transactional
    public void openNewWorkday() throws WorkdayAlreadyOpenedException, GeneralException
    {
        User currentUser = application.getActiveUser();
        if(currentUser == null) throw new GeneralException();
        if(workdayDAO.getOpen() != null) throw new WorkdayAlreadyOpenedException();
        
        Workday workday = new Workday();
        workday.setOpen(true);
        workday.setOpenTime(DateTime.now());
        workday.setOpenUser(currentUser);
        
        workdayDAO.create(workday);
    }
    
    @Transactional
    public Workday getOpenWorkday()
    {
        Workday workday = workdayDAO.getOpen();
        
        return workday;
    }
    
    @Transactional
    public boolean isWorkdayOpened()
    {
        return workdayDAO.getOpen() != null;
    }
    
    @Transactional
    public void closeWorkday() throws GeneralException
    {
        User currentUser = application.getActiveUser();
        if(currentUser == null) throw new GeneralException();
        
        Workday workday = workdayDAO.getOpen();
        if(workday != null)
        {
            workday.setOpen(false);
            workday.setCloseTime(DateTime.now());
            workday.setCloseUser(currentUser);
            
            List<Cheque> cheques = chequeDAO.getByWorkday(workday);
            Double total = 0.0;
            for(Cheque cheque : cheques)
            {
                if(cheque.isProcessed()) total += cheque.getChequeTotal();
            }
            workday.setTotal(total);
            
            workdayDAO.update(workday);
        }
    }
}
