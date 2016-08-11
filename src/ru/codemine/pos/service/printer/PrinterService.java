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

package ru.codemine.pos.service.printer;

import java.util.ArrayList;
import java.util.List;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

/**
 *
 * @author Alexander Savelev
 */


public class PrinterService 
{

    /**
     * Возвращает список имен всех принтеров
     * @return
     */
    public List<String> getAvaiblePrinterNames()
    {
        List<String> result = new ArrayList<>();
        
        PrintService[] services = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.AUTOSENSE, null);
        for(PrintService service : services)
        {
            result.add(service.getName());
        }
        
        return result;
    }
    
    /**
     * Возвращает объект PrintService по имени принтера
     * @param name имя принтера
     * @return
     */
    public PrintService getPrinterByName(String name)
    {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.AUTOSENSE, null);
        for(PrintService service : services)
        {
            if(service.getName().equals(name))
                return service;
        }
        
        return null;
    }
    
    public void printPlainText(String data, String printerName) throws PrintException
    {
        PrintService service = getPrinterByName(printerName);
        Doc doc = new SimpleDoc(data, DocFlavor.STRING.TEXT_PLAIN, null);
        
        DocPrintJob job = service.createPrintJob();
        
        PrinterJobWatcher watcher = new PrinterJobWatcher(job);
        job.print(doc, null);
        watcher.waitForFinish();
    }
}
