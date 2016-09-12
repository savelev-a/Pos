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

import ru.codemine.pos.service.device.printer.PrinterService;
import javax.print.PrintException;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.entity.document.Cheque;
import ru.codemine.pos.service.kkm.template.SimpleChequeTemplate;

/**
 *
 * @author Alexander Savelev
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PrinterServiceTest 
{
    @Autowired private PrinterService printerService;
    
    private static final Logger log = Logger.getLogger("PrinterServiceTest");
    
    @Test
    public void testPrinterService()
    {
        
        try
        {
            Cheque cheque = new Cheque();
            cheque.addItem(new Product("123456", "Рога пластиковые настенные", "123", 1200.0), 1);
            cheque.addItem(new Product("1234576", "Копыта стальные кухонные", "1237", 2400.0), 1);
            printerService.printPlainText(new SimpleChequeTemplate(cheque).toString(), "Star-TSP100");
            log.info("Printing: ok");
        } 
        catch (PrintException ex)
        {
            log.error(ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

}
