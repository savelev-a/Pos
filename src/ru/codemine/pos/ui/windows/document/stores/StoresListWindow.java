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

package ru.codemine.pos.ui.windows.document.stores;

import com.alee.laf.label.WebLabel;
import com.alee.laf.table.WebTable;
import java.util.List;
import javax.swing.table.TableColumnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.Store;
import ru.codemine.pos.service.StoreService;
import ru.codemine.pos.tablemodel.StoresListTableModel;
import ru.codemine.pos.ui.windows.document.GenericDocumentListWindow;
import ru.codemine.pos.ui.windows.document.stores.listener.DeleteStore;
import ru.codemine.pos.ui.windows.document.stores.listener.EditStore;
import ru.codemine.pos.ui.windows.document.stores.listener.RefreshStoreList;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class StoresListWindow extends GenericDocumentListWindow
{
    @Autowired private StoreService storeService;
    
    @Autowired private EditStore editStore;
    @Autowired private DeleteStore deleteStore;
    @Autowired private RefreshStoreList refreshStoreList;
    
    private StoresListTableModel tableModel;
    
    public StoresListWindow()
    {
        super();
        setTitle("Склады");
    }
    
    public void showWindow()
    {
        List<Store> stores = storeService.getAll();
        
        tableModel = new StoresListTableModel(stores);
        table.setModel(tableModel);
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(10);
        columnModel.getColumn(1).setMaxWidth(50);
        
        statusLabel.setText("Загружено " + stores.size() + " строк");
        
        setVisible(true);
        
        setEditActionListener(editStore);
        setDeleteActionListener(deleteStore);
        setRefreshActionListener(refreshStoreList);
    }
    
    public StoresListTableModel getTableModel()
    {
        return tableModel;
    }

    public WebLabel getStatusLabel()
    {
        return statusLabel;
    }

    public WebTable getTable()
    {
        return table;
    }

    public Store getSelectedStore()
    {
        if(table.getSelectedRow() == -1) return null;
        
        return tableModel.getStoreAt(table.getSelectedRow());
    }

    public void refresh()
    {
        menuItemRefresh.doClick();
    }
}
