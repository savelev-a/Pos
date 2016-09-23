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

package ru.codemine.pos.service.kkm.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.entity.Shop;
import ru.codemine.pos.entity.document.Cheque;
import ru.codemine.pos.entity.document.ChequeLine;

/**
 *
 * @author Alexander Savelev
 */

public class JasperChequeTemplate 
{
    
    public void print(Cheque cheque, String printerName) throws JRException
    {
        List<JasperChequeRecord> records = new ArrayList<>();
        
        for(ChequeLine line : cheque.getContent())
        {
            records.add(new JasperChequeRecord(
                    line.getProduct().getName(), 
                    line.getQuantity(), 
                    "=" + line.getLineTotal()));
        }
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("titleString", cheque.getShop().getOrgName());
        parameters.put("reqs", "ИНН " + cheque.getShop().getOrgInn() + "   КПП " + cheque.getShop().getOrgKpp());
        parameters.put("currentTime", DateTime.now().toString("dd.MM.YY HH:mm"));
        parameters.put("currentUser", cheque.getCreator().getUsername());
        parameters.put("chequeNumber", "#0011");
        parameters.put("chequeTotal", "=" + cheque.getChequeTotal());
        parameters.put("paymentType", Cheque.getAvaiblePaymentTypes().get(cheque.getPaymentType()));
        parameters.put("fromClient", cheque.getChequeTotal().toString());
        parameters.put("toClient", "0.0");
        

        JasperReport report = (JasperReport)JRLoader.loadObjectFromFile("reports/ChequeTemplate.jasper");
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(records);
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);

        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();

        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(new PrinterName(printerName, null));

        JRPrintServiceExporter exporter = new JRPrintServiceExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

        SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
        configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
        configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
        configuration.setDisplayPageDialog(false);
        configuration.setDisplayPrintDialog(false);

        exporter.setConfiguration(configuration);
        exporter.exportReport();

    }
}
