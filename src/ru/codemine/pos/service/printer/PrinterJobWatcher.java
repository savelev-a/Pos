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

import javax.print.DocPrintJob;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

/**
 *
 * @author Alexander Savelev
 */
public class PrinterJobWatcher 
{
    private boolean printFinished;
    
    public PrinterJobWatcher(DocPrintJob job)
    {
        printFinished = false;
        
        job.addPrintJobListener(new PrintJobAdapter() {
            @Override
            public void printJobCanceled(PrintJobEvent ev)
            {
                System.out.println("Print canceled!");
                printFinished = true;
            }
            
            @Override
            public void printJobCompleted(PrintJobEvent ev)
            {
                System.out.println("Print completed!");
                printFinished = true;
            }
            
            @Override
            public void printJobFailed(PrintJobEvent ev)
            {
                System.out.println("Print failed!");
                printFinished = true;
            }
            
            @Override
            public void printJobNoMoreEvents(PrintJobEvent ev)
            {
                System.out.println("No more events");
                printFinished = true;
            }
        });
    }
    
    
    
    public synchronized void waitForFinish()
    {
        try
        {
            while(!printFinished) wait();
        } 
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }

}
