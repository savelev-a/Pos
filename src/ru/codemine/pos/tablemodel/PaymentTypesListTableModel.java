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

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import ru.codemine.pos.entity.document.Cheque;

/**
 *
 * @author Alexander Savelev
 */
public class PaymentTypesListTableModel extends DefaultTableModel
{
    private static final ImageIcon ICON = new ImageIcon("images/icons/default/16x16/payment-type.png");

    @Override
    public int getRowCount()
    {
        return Cheque.PaymentType.values().length;
    }

    @Override
    public int getColumnCount()
    {
        return 3;
    }

    @Override
    public String getColumnName(int column)
    {
        switch(column)
        {
            case 0 : return "";
            case 1 : return "№";
            case 2 : return "Тип оплаты";
        }
        
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0 : return ImageIcon.class;
            default : return String.class;
        }
        
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
            case 0 : return ICON;
            case 1 : return row;
            case 2 : return Cheque.getAvaiblePaymentTypes().get(Cheque.PaymentType.values()[row]);
        }
        
        return "";
    }
    
    public Cheque.PaymentType getPtypeAt(int selectedRow)
    {
        return Cheque.PaymentType.values()[selectedRow];
    }
    
    
    
    

}
