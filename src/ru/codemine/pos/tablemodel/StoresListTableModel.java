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

package ru.codemine.pos.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import ru.codemine.pos.entity.Store;

/**
 *
 * @author Alexander Savelev
 */
public class StoresListTableModel extends DefaultTableModel
{
    private List<Store> stores;
    private static final ImageIcon ICON_IMAGE = new ImageIcon("images/icons/16x16/Document-01.png");
    
    public StoresListTableModel(List<Store> stores)
    {
        this.stores = stores;
    }
    
    public StoresListTableModel()
    {
        this.stores = new ArrayList<>();
    }
    
    @Override
    public int getRowCount()
    {
        return stores == null ? 0 : stores.size();
    }
    
    @Override
    public int getColumnCount()
    {
        return 3; //pic, id, name
    }
    
    @Override
    public String getColumnName(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0 : return "";
            case 1 : return "№";
            case 2 : return "Наименование";
        }
        
        return "";
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnIndex == 0 ? ImageIcon.class : String.class;
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        switch(columnIndex)
        {
            case 0 : return ICON_IMAGE; 
            case 1 : return stores.get(rowIndex).getId();
            case 2 : return stores.get(rowIndex).getName();
        }
        
        return "";
    }

    public void setStoresList(List<Store> stores)
    {
        this.stores = stores;
    }

    public Store getStoreAt(int selectedRow)
    {
        return stores.get(selectedRow);
    }
}
