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

package ru.codemine.pos.ui.windows.selector;

import com.alee.laf.optionpane.WebOptionPane;
import com.alee.utils.swing.DocumentChangeListener;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.table.TableColumnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.document.Cheque;
import ru.codemine.pos.tablemodel.PaymentTypesListTableModel;
import ru.codemine.pos.ui.MainWindow;
import ru.codemine.pos.ui.salespanel.SalesPanel;
import ru.codemine.pos.ui.windows.document.GenericDocumentWindow;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class PaymentTypeSelector extends GenericSelector
{
    @Autowired private MainWindow mainWindow;
    @Autowired private SalesPanel salesPanel;
    
    PaymentTypesListTableModel tableModel;

    public PaymentTypeSelector()
    {
        super();
        actionListenersInit = false;
    }

    @Override
    public void selectFor(GenericDocumentWindow window)
    {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    public void selectForMainWindow()
    {
        if(!actionListenersInit) setupActionListeners();
        mainWindow.blockBarcodeInput();
        
        tableModel = new PaymentTypesListTableModel();
        table.setModel(tableModel);
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(10);
        columnModel.getColumn(1).setMaxWidth(50);
        
        setTitle("Выберите тип оплаты");
        statusLabel.setText("Загружено " + tableModel.getRowCount() + " строк");
        
        
        setupSorter();
        setVisible(true);
        table.setSelectedRow(0);
    }

    @Override
    public void setupActionListeners()
    {
        selectButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                sorter.setRowFilter(null); //иначе будет выделена не та строка
                
                int row = table.getSelectedRow();
                if(row == -1)
                {
                    WebOptionPane.showMessageDialog(rootPane, "Выберите тип оплаты", "Не выбран тип оплаты", WebOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                Cheque.PaymentType type = tableModel.getPtypeAt(table.getSelectedRow());
                Cheque mainCheque = salesPanel.getChequeSetupPanel().getCheque();
                mainCheque.setPaymentType(type);
                salesPanel.getCalcsPanel().showByCheque(mainCheque);
                
                mainWindow.unblockBarcodeInput();
                setVisible(false);
            }
        });
        
        finishSetupActionListeners();
    }

}
