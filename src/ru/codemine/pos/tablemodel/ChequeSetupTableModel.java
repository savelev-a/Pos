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
import javax.swing.table.DefaultTableModel;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.entity.document.Cheque;

/**
 *
 * @author Alexander Savelev
 */
public class ChequeSetupTableModel extends DefaultTableModel
{
    private Cheque cheque;
    private List<Map.Entry<Product, Integer>> entrylist;

    public ChequeSetupTableModel()
    {
        this.cheque = new Cheque();
        this.entrylist = new ArrayList<>(cheque.getContents().entrySet());
    }
    public ChequeSetupTableModel(Cheque cheque)
    {
        this.cheque = cheque;
        this.entrylist = new ArrayList<>(cheque.getContents().entrySet());
    }
    
    @Override
    public int getRowCount()
    {
        return cheque == null ? 0 :cheque.getContents().size();
    }

    @Override
    public int getColumnCount()
    {
        return 5; //в видимой таблице 5 колонок
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0 : return "№";
            case 1 : return "Наименование";
            case 2 : return "Цена";
            case 3 : return "Количество";
            case 4 : return "Стоимость";
                
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0 : return Integer.class;
            case 1 : return String.class;
            case 2 : return Double.class;
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
            case 0 : return rowIndex + 1;
            case 1 : return entrylist.get(rowIndex).getKey().getName();
            case 2 : return entrylist.get(rowIndex).getKey().getPrice();
            case 3 : return entrylist.get(rowIndex).getValue();
            case 4 : return entrylist.get(rowIndex).getKey().getPrice() * entrylist.get(rowIndex).getValue();
        }
        
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        if(columnIndex != 3) return;
        Product p = entrylist.get(rowIndex).getKey();
        Integer quantity = (Integer)aValue;
        cheque.getContents().put(p, quantity);
        refreshEntryList();
    }

    
    public Cheque getCheque()
    {
        return cheque;
    }
    
    public void setCheque(Cheque cheque)
    {
        this.cheque = cheque;
        refreshEntryList();
    }
    
    public void addItem(Product product, Integer quantity)
    {

        cheque.getContents().put(product, quantity);
        refreshEntryList();
    }
    
    public void deleteRow(int row)
    {
        Product p = entrylist.get(row).getKey();
        
        cheque.getContents().remove(p);
        refreshEntryList();
    }
    
    private void refreshEntryList()
    {
        this.entrylist = new ArrayList<>(cheque.getContents().entrySet());
    }

    public void newCheque()
    {
        cheque = new Cheque();
        refreshEntryList();
    }

}
