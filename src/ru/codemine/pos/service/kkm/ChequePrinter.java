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
import java.util.logging.Level;
import javax.print.PrintException;
import org.apache.log4j.Logger;
import ru.codemine.pos.entity.Workday;
import ru.codemine.pos.entity.document.Cheque;
import ru.codemine.pos.exception.KkmException;
import ru.codemine.pos.service.kkm.template.SimpleChequeTemplate;
import ru.codemine.pos.service.kkm.template.SimpleXReportTemplate;
import ru.codemine.pos.service.kkm.template.SimpleZReportTemplate;
import ru.codemine.pos.service.device.printer.PrinterService;

/**
 *
 * @author Alexander Savelev
 */


public class ChequePrinter extends Kkm
{
    
    private static final Logger log = Logger.getLogger("ChequePrinterModule");


    @Override
    public void printCheque(Cheque cheque) throws KkmException
    {
        SimpleChequeTemplate template = new SimpleChequeTemplate(cheque);

        try
        {
            PrinterService.printPlainText(template.toString(), getDevice().getSysprinter());
        } 
        catch (PrintException ex)
        {
            log.error("Ошибка печати чека!");
            log.error(ex.getLocalizedMessage());
            
            throw new KkmException(ex.getLocalizedMessage());
        }

    }

    @Override
    public void printXReport(Workday currentWorkday, List<Cheque> cheques) throws KkmException
    {
        SimpleXReportTemplate template = new SimpleXReportTemplate(currentWorkday, cheques);
        
        try
        {
            PrinterService.printPlainText(template.toString(), getDevice().getSysprinter());
        } 
        catch (PrintException ex)
        {
            throw new KkmException(ex.getLocalizedMessage());
        }
    }

    @Override
    public void printZReport(Workday currentWorkday, List<Cheque> cheques) throws KkmException
    {
        SimpleZReportTemplate template = new SimpleZReportTemplate(currentWorkday, cheques);
        
        try
        {
            PrinterService.printPlainText(template.toString(), getDevice().getSysprinter());
        } 
        catch (PrintException ex)
        {
            throw new KkmException(ex.getLocalizedMessage());
        }
    }

}
