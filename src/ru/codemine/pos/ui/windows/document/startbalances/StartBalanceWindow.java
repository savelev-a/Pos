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

package ru.codemine.pos.ui.windows.document.startbalances;

import com.alee.extended.layout.TableLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.scroll.WebScrollPane;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.TableColumnModel;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.entity.Store;
import ru.codemine.pos.entity.document.StartBalance;
import ru.codemine.pos.service.StartBalanceService;
import ru.codemine.pos.service.StoreService;
import ru.codemine.pos.tablemodel.StartBalanceContentTableModel;
import ru.codemine.pos.ui.windows.document.GenericDocumentWindow;
import ru.codemine.pos.ui.windows.document.startbalances.listener.DontSaveSb;
import ru.codemine.pos.ui.windows.document.startbalances.listener.SaveSb;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class StartBalanceWindow extends GenericDocumentWindow<StartBalance>
{
    @Autowired private Application application;
    @Autowired private StoreService storeService;
    @Autowired private StartBalanceService sbservice;
    
    @Autowired private SaveSb saveSb;
    @Autowired private DontSaveSb dontSaveSb;
    
    private final WebLabel storeLabel;
    private final WebComboBox storeFieldComboBox;
    private final WebButton loadFromFileToolButton;
    
    private StartBalance startBalance;
    
    public StartBalanceWindow()
    {
        super();
        setTitle("Документ начальных остатков");
        
        storeLabel = new WebLabel("Склад");
        storeFieldComboBox = new WebComboBox();
        loadFromFileToolButton = new WebButton(new ImageIcon("images/icons/default/16x16/toolbutton-loadff.png"));
        
        loadFromFileToolButton.setRolloverDecoratedOnly(true);
        tableToolBar.addSeparator();
        tableToolBar.add(loadFromFileToolButton);
        
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10},
            {10, TableLayout.PREFERRED,                 // Id, склад
             10, TableLayout.PREFERRED,                 // Название, времена
             10, TableLayout.PREFERRED,                 // Создатель
             10, TableLayout.PREFERRED,                 // Тулбар, заголовок таблицы
             TableLayout.FILL,                      // Таблица
             10, TableLayout.PREFERRED, 10}             // Кнопки
        });
        setLayout(layout);
        
        add(idLabel,            "1, 1");
        add(idField,            "3, 1");
        add(storeLabel,         "5, 1");
        add(storeFieldComboBox, "7, 1");
        add(creationTimeLabel,  "1, 3");
        add(creationTimeField,  "3, 3");
        add(documentTimeLabel,  "5, 3");
        add(documentTimeField,  "7, 3");
        add(creatorLabel,       "1, 5");
        add(creatorField,       "3, 5");
        //add(contentLabel,       "1, 7, L, T");
        add(tableToolBar,        "1, 7, 7, 7, F, F");
        add(new WebScrollPane(documentContent), "1, 8, 7, 8, F, F");
        add(buttonsGroupPanel, "1, 10, 7, 10, C, T");
        
        creationTimeField.setEnabled(false);
        creatorField.setEditable(false);
        
    }

    @Override
    public void showWindow(StartBalance startBalance)
    {
        if(!actionListenersInit) setupActionListeners();
        
        this.startBalance = startBalance;
        
        List<Store> stores = storeService.getAll();
        
        idField.setText(startBalance.getId() == null ? "" : startBalance.getId().toString());
        creationTimeField.setDate(startBalance.getCreationTime().toDate());
        documentTimeField.setDate(startBalance.getDocumentTime().toDate());
        creatorField.setText(startBalance.getCreator() == null 
                ? application.getActiveUser().getUsername() 
                : startBalance.getCreator().getUsername());
        
        storeFieldComboBox.removeAllItems();
        for(Store s : stores)
        {
            storeFieldComboBox.addItem(s.getName());
        }
        
        
        documentContent.setModel(new StartBalanceContentTableModel(startBalance));
        TableColumnModel columnModel = documentContent.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(10);
        
        setVisible(true);
    }

    @Override
    public void setupActionListeners()
    {
        saveButton.addActionListener(saveSb);
        cancelButton.addActionListener(dontSaveSb);
        
        actionListenersInit = true;
    }
    
    @Override
    public StartBalance getDocument()
    {
        startBalance.setStore(storeService.getByName((String)storeFieldComboBox.getSelectedItem()));
        startBalance.setDocumentTime(new DateTime(documentTimeField.getDate()));
        //TODO закончил тут
        
        return null;
    }

}
