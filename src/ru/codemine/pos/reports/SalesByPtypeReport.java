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

package ru.codemine.pos.reports;

import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.rootpane.WebFrame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.document.Cheque;
import ru.codemine.pos.reports.reportmodel.SalesByPtypeReportRecord;
import ru.codemine.pos.service.ChequeService;
import ru.codemine.pos.ui.windows.PeriodSelectable;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class SalesByPtypeReport implements PeriodSelectable
{
    @Autowired private ChequeService chequeService;

    @Override
    public void setPeriod(LocalDate startDate, LocalDate endDate)
    {
        List<SalesByPtypeReportRecord> records = new ArrayList<>();
        for(Map.Entry<Cheque.PaymentType, String> paymentEntry : Cheque.getAvaiblePaymentTypes().entrySet())
        {
            List<Cheque> chequesByPtype = chequeService.getByPeriod(startDate, endDate, paymentEntry.getKey());
            Double sales = 0.0;
            for(Cheque c : chequesByPtype) sales += c.getChequeTotal();
            records.add(new SalesByPtypeReportRecord(paymentEntry.getValue(), chequesByPtype.size(), sales));
        }
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("startDate", startDate.toString("dd.MM.YY"));
        parameters.put("endDate", endDate.toString("dd.MM.YY"));
        
        try
        {
            JasperReport report = (JasperReport)JRLoader.loadObjectFromFile("reports/SalesByPtypeReport.jasper");
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(records);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
            WebFrame reportFrame = new WebFrame("Отчет о выручке по типу оплат");
            reportFrame.getContentPane().add(new JRViewer(jasperPrint));
            reportFrame.pack();
            reportFrame.setExtendedState(WebFrame.MAXIMIZED_BOTH);
            reportFrame.setVisible(true);
        } 
        catch (JRException ex)
        {
            WebOptionPane.showMessageDialog(null, ex.getLocalizedMessage(), "Ошибка создания отчета", WebOptionPane.ERROR_MESSAGE);
        }
        
        
    }
    
}
