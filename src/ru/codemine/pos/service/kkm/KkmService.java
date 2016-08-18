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

package ru.codemine.pos.service.kkm;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.codemine.pos.entity.Workday;
import ru.codemine.pos.entity.document.Cheque;
import ru.codemine.pos.exception.KkmException;
import ru.codemine.pos.service.ChequeService;
import ru.codemine.pos.service.WorkdayService;

/**
 *
 * @author Alexander Savelev
 */

@Service
public class KkmService 
{
    @Autowired private WorkdayService workdayService;
    @Autowired private ChequeService chequeService;
    
    public void printXReport(Kkm kkm) throws KkmException
    {
        Workday currentWorkday = workdayService.getOpenWorkday();
        List<Cheque> cheques = chequeService.getByOpenWorkday();
        
        kkm.printXReport(currentWorkday, cheques);
    }
}