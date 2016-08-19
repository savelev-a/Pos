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
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.entity.Store;

/**
 *
 * @author Alexander Savelev
 */
public class ProductByStoresTableModel extends DefaultTableModel
{
    private Product product;
    private List<Store> stores;
    
    private static final ImageIcon ICON_IMAGE = new ImageIcon("images/icons/default/16x16/store.png");
    
    public ProductByStoresTableModel()
    {
        this.product = new Product();
        this.stores= new ArrayList<>();
    }

    public ProductByStoresTableModel(Product product, List<Store> stores)
    {
        this.product = product;
        this.stores = stores;
    }

    @Override
    public int getRowCount()
    {
        return stores == null ? 0 : stores.size();
    }

    @Override
    public int getColumnCount()
    {
        return 3; //pic, store, quantity
    }

    @Override
    public String getColumnName(int column)
    {
        switch(column)
        {
            case 0 : return "";
            case 1 : return "Склад";
            case 2 : return "Остаток";
        }
        
        return "";
    }

    @Override
    public Class<?> getColumnClass(int column)
    {
        switch(column)
        {
            case 0 : return ImageIcon.class;
            case 1 : return String.class;
            case 2 : return Double.class;
        }
        
        return String.class;
    }
    
    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }

    @Override
    public Object getValueAt(int row, int column)
    {
        switch(column)
        {
            case 0 : return ICON_IMAGE;
            case 1 : return stores.get(row).getName();
            case 2 : return stores.get(row).getStocks().get(product);
        }
        
        return "";
    }
    
    public void setData(Product product, List<Store> stores)
    {
        this.product = product;
        this.stores = stores;
    }

}
