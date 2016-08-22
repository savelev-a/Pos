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

package ru.codemine.pos.ui.windows.stores;

import com.alee.extended.layout.TableLayout;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.label.WebLabel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.Store;
import ru.codemine.pos.service.StoreService;
import ru.codemine.pos.tablemodel.StoreStocksTableModel;
import ru.codemine.pos.ui.windows.GenericEntityWindow;
import ru.codemine.pos.ui.windows.stores.listener.DontSaveStore;
import ru.codemine.pos.ui.windows.stores.listener.SaveStore;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class StoreWindow extends GenericEntityWindow<Store>
{
    @Autowired private StoreService storeService;
    @Autowired private SaveStore saveStore;
    @Autowired private DontSaveStore dontSaveStore;
    
    private final WebLabel nameLabel;
    private final WebLabel idLabel;
    private final WebLabel storeIdField;
    private final WebTextField storeNameField;
    private final WebTable table;
    
    private Store store;
    
    public StoreWindow()
    {
        super();
        setTitle("Склад");
        
        nameLabel = new WebLabel("Название");
        idLabel = new WebLabel("№ склада");
        storeIdField = new WebLabel();
        storeNameField = new WebTextField();
        table = new WebTable();
        
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10},
            {10, TableLayout.PREFERRED,                 // Id
             10, TableLayout.PREFERRED,                 // Название
             10, TableLayout.FILL,                      // Таблица
             10, TableLayout.PREFERRED, 10}             // Кнопки
        });
        setLayout(layout);
        
        add(idLabel, "1, 1");
        add(storeIdField, "3, 1");
        add(nameLabel, "1, 3");
        add(storeNameField, "3, 3");
        add(new WebScrollPane(table), "3, 5");
        add(new WebButtonGroup(saveButton, cancelButton), "1, 7, 3, 7, C, T");
        
    }
    
    @Override
    public void showWindow(Store store)
    {
        if(!actionListenersInit) setupActionListeners();
        
        this.store = store;
        
        setTitle("Склад - " + store.getName());
        
        storeIdField.setText(store.getId() == null ? "" : store.getId().toString());
        storeNameField.setText(store.getName());
        
        table.setModel(new StoreStocksTableModel(store));
        
        if("Розница".equals(store.getName())) 
        {
            storeNameField.setEditable(false);
        }
        else
        {
            storeNameField.setEditable(true);
        }
        
        setVisible(true);
    }

    public Store getStore()
    {
        store.setName(storeNameField.getText());
        
        return store;
    }
    
    @Override
    public void setupActionListeners()
    {
        saveButton.addActionListener(saveStore);
        cancelButton.addActionListener(dontSaveStore);
        
        actionListenersInit = true;
    }
}
