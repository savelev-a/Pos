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
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.entity.Store;

/**
 *
 * @author Alexander Savelev
 */
public class StoreStocksTableModel extends DefaultTableModel
{
    private static final ImageIcon ICON_IMAGE = new ImageIcon("images/icons/default/16x16/product.png");
    
    private Store store;
    private List<Map.Entry<Product, Integer>> stocks;
    
    public StoreStocksTableModel(Store store)
    {
        this.store = store;
        stocks = new ArrayList<>(store.getStocks().entrySet());
    }
    
    public StoreStocksTableModel()
    {
        store = new Store();
        stocks = new ArrayList<>(store.getStocks().entrySet());
    }
    
    @Override
    public int getRowCount()
    {
        return store == null ? 0 : store.getStocks().size();
    }
    
    @Override
    public int getColumnCount()
    {
        return 5; // icon, артикул, наименование, кол-во, цена
    }
    
    @Override
    public String getColumnName(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0 : return "";
            case 1 : return "Артикул";
            case 2 : return "Наименование";
            case 3 : return "Количество";
            case 4 : return "Цена";
        }
        
        return "";
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0 : return ImageIcon.class;
            case 1 : return String.class;
            case 2 : return String.class;
            case 3 : return Integer.class;
            case 4 : return Double.class;
        }
        
        return String.class;
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
            case 1 : return stocks.get(rowIndex).getKey().getArtikul();
            case 2 : return stocks.get(rowIndex).getKey().getName();
            case 3 : return stocks.get(rowIndex).getValue();
            case 4 : return stocks.get(rowIndex).getKey().getPrice();
        }
        
        return "";
    }
    
    public void setStore(Store store)
    {
        this.store = store;
        stocks = new ArrayList<>(store.getStocks().entrySet());
    }
}
