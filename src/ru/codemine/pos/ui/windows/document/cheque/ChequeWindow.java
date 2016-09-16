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

import com.alee.extended.layout.TableLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.table.TableColumnModel;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.GenericEntity;
import ru.codemine.pos.entity.document.Cheque;
import ru.codemine.pos.tablemodel.ChequeContentTableModel;
import ru.codemine.pos.ui.windows.document.GenericDocumentWindow;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class ChequeWindow extends GenericDocumentWindow<Cheque>
{
    
    private final WebLabel workdayLabel;
    private final WebLabel ptypeLabel;
    private final WebLabel totalLabel;
    
    private final WebTextField workdayField;
    private final WebComboBox ptypeComboBox;
    private final WebTextField totalField;
    
    private ChequeContentTableModel tableModel;
    
    public ChequeWindow()
    {
        super();
        setTitle("Просмотр чека");
        
        workdayLabel = new WebLabel("№ и дата смены");
        ptypeLabel = new WebLabel("Тип оплаты");
        totalLabel = new WebLabel("Сумма чека");
        workdayField = new WebTextField();
        ptypeComboBox = new WebComboBox();
        totalField = new WebTextField();
        
        
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10},
            {10, TableLayout.PREFERRED,                 // Id, смена
             10, TableLayout.PREFERRED,                 // Даты
             10, TableLayout.PREFERRED,                 // Создатель, тип оплаты
             10, TableLayout.PREFERRED,                 // итог чека
             10, TableLayout.PREFERRED,                 // Тулбар, заголовок таблицы
             TableLayout.FILL,                          // Таблица
             10, TableLayout.PREFERRED, 10}             // Кнопки
        });
        setLayout(layout);
        
        add(idLabel,            "1, 1");
        add(idField,            "3, 1");
        add(workdayLabel,       "5, 1");
        add(workdayField,       "7, 1");
        add(creationTimeLabel,  "1, 3");
        add(creationTimeField,  "3, 3");
        add(documentTimeLabel,  "5, 3");
        add(documentTimeField,  "7, 3");
        add(creatorLabel,       "1, 5");
        add(creatorField,       "3, 5");
        add(ptypeLabel,         "5, 5");
        add(ptypeComboBox,      "7, 5");
        add(totalLabel,         "5, 7");
        add(totalField,         "7, 7");
        add(tableToolBar,        "1, 9, 7, 9, F, F");
        add(new WebScrollPane(contentTable), "1, 10, 7, 10, F, F");
        add(buttonsGroupPanel, "1, 12, 7, 12, C, T");
        
        workdayField.setEditable(false);
        creationTimeField.setEnabled(false);
        documentTimeField.setEnabled(false);
        creatorField.setEditable(false);
        ptypeComboBox.setEnabled(false);
        totalField.setEditable(false);
        
        addItemToolButton.setEnabled(false);
        removeItemToolButton.setEnabled(false);
        setQuantityToolButton.setEnabled(false);
        
        saveButton.setEnabled(false);
        
    }

    @Override
    public void showWindow(Cheque cheque)
    {
        if(!actionListenersInit) setupActionListeners();
        
        idField.setText(cheque.getId() == null ? "" : cheque.getId().toString());
        workdayField.setText(
                "№" 
                + cheque.getWorkday().getId().toString() 
                + " от " 
                + cheque.getWorkday().getOpenTime().toString("dd.MM.YY HH:mm"));
        
        creationTimeField.setDate(cheque.getCreationTime().toDate());
        documentTimeField.setDate(cheque.getDocumentTime().toDate());
        creatorField.setText(cheque.getCreator().getUsername());
        ptypeComboBox.removeAllItems();
        ptypeComboBox.addItem(cheque.getPaymentTypeString());
        totalField.setText(cheque.getChequeTotal().toString());
        
        tableModel = new ChequeContentTableModel(cheque);
        contentTable.setModel(tableModel);
        TableColumnModel columnModel = contentTable.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(10);
        
        setVisible(true);

    }

    @Override
    public void setupActionListeners()
    {
        cancelButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        });
        
        actionListenersInit = true;
    }

    @Override
    public Cheque getDocument()
    {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void addItem(GenericEntity item)
    {
    }

}
