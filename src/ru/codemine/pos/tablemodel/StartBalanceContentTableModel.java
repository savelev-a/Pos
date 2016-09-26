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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.entity.document.StartBalance;

/**
 *
 * @author Alexander Savelev
 */
public class StartBalanceContentTableModel extends DefaultTableModel
{
    private static final ImageIcon ICON_IMAGE = new ImageIcon("images/icons/default/16x16/product.png");
    
    private StartBalance startBalance;
    private List<Map.Entry<Product, Integer>> stocks;
    
    public StartBalanceContentTableModel()
    {
        startBalance = new StartBalance();
        stocks = new ArrayList<>(startBalance.getContent().entrySet());
    }
    
    public StartBalanceContentTableModel(StartBalance sb)
    {
        this.startBalance = sb;
        stocks = new ArrayList<>(sb.getContent().entrySet());
    }

    @Override
    public int getRowCount()
    {
        return startBalance == null ? 0 : startBalance.getContent().size();
    }

    @Override
    public int getColumnCount()
    {
        return 5; //icon, artikul, name, barcode, quantity
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0 : return "";
            case 1 : return "Артикул";
            case 2 : return "Наименование";
            case 3 : return "Штрихкод";
            case 4 : return "Количество";
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
            case 3 : return String.class;
            case 4 : return Integer.class;
        }
        
        return String.class;
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return columnIndex == 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        switch(columnIndex)
        {
            case 0 : return ICON_IMAGE;
            case 1 : return stocks.get(rowIndex).getKey().getArtikul();
            case 2 : return stocks.get(rowIndex).getKey().getName();
            case 3 : return stocks.get(rowIndex).getKey().getBarcode();
            case 4 : return stocks.get(rowIndex).getValue();
        }
        
        return "";
    }
    
    @Override
    public void setValueAt(Object aValue, int row, int column)
    {
        Integer value = (Integer)aValue;
        if(value == null) return;
        
        Product product = stocks.get(row).getKey();
        startBalance.getContent().put(product, value);
        stocks = new ArrayList<>(startBalance.getContent().entrySet());
        
    }
    
    public void setStartBalance(StartBalance sb)
    {
        this.startBalance = sb;
        stocks = new ArrayList<>(sb.getContent().entrySet());
    }
    
    public Map<Product, Integer> getContentMap()
    {
        Map<Product, Integer> result = new HashMap<>();
        for(Map.Entry<Product, Integer> entry : stocks)
        {
            result.put(entry.getKey(), entry.getValue());
        }
        
        return result;
    }

    public int getProductIndex(Product product)
    {
        for(int i = 0; i <= stocks.size(); i++)
        {
            Product p = stocks.get(i).getKey();
            if(p.equals(product))
                return i;
        }
        
        return -1;
    }
    
    public void deleteRow(int row)
    {
        Product p = stocks.get(row).getKey();
        
        startBalance.getContent().remove(p);
        startBalance.calculateTotals();
        stocks = new ArrayList<>(startBalance.getContent().entrySet());
    }
    
    

}
