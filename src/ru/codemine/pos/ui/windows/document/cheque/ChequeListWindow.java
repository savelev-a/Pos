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

package ru.codemine.pos.ui.windows.document.cheque;

import java.util.List;
import javax.swing.table.TableColumnModel;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.document.Cheque;
import ru.codemine.pos.service.ChequeService;
import ru.codemine.pos.tablemodel.ChequeListTableModel;
import ru.codemine.pos.ui.windows.document.GenericDocumentListWindow;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class ChequeListWindow extends GenericDocumentListWindow
{
    @Autowired private ChequeService chequeService;
    
    private ChequeListTableModel tableModel;
    
    public ChequeListWindow()
    {
        super();
        setTitle("Чеки");
        setSize(800, 400);
        setReadOnly(true);
    }

    @Override
    public void showWindow()
    {
        if(!actionListenersInit) setupActionListeners();
        setVisible(true);
    }
    
    public void showWindow(List<Cheque> chequeList) 
    {
        tableModel = new ChequeListTableModel(chequeList);
        table.setModel(tableModel);
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(10);
        columnModel.getColumn(1).setMaxWidth(50);
        
        statusLabel.setText("Загружено " + chequeList.size() + " строк");
        setupSorter();
        showWindow();
    }

    @Override
    public void setupActionListeners()
    {
        actionListenersInit = true;
    }

    @Override
    public void setPeriod(LocalDate startDate, LocalDate endDate)
    {
        List<Cheque> cheques = chequeService.getByPeriod(startDate, endDate);
        showWindow(cheques);
    }

}
