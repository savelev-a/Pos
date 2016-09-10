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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.reports.reportmodel.StickerReportRecord;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class StickersReport 
{
    public void showReport(List<Product> products)
    {
        List<StickerReportRecord> records = new ArrayList<>();
        
        for(Product product : products) records.add(new StickerReportRecord(product));
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("datetime", DateTime.now().toString("dd.MM.YY HH:mm"));
        
        try
        {
            JasperReport report = (JasperReport)JRLoader.loadObjectFromFile("reports/StickerReport.jasper");
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(records);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
            WebFrame reportFrame = new WebFrame("Печать этикеток");
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
