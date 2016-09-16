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
import ru.codemine.pos.entity.document.Cheque;
import ru.codemine.pos.entity.document.ChequeLine;

/**
 *
 * @author Alexander Savelev
 */
public class ChequeContentTableModel extends DefaultTableModel
{
    private static final ImageIcon ICON_IMAGE = new ImageIcon("images/icons/default/16x16/product.png");
    
    private Cheque cheque;
    private List<ChequeLine> lines;

    public ChequeContentTableModel()
    {
        this.cheque = new Cheque();
        this.lines = new ArrayList<>(cheque.getContent());
    }

    public ChequeContentTableModel(Cheque cheque)
    {
        this.cheque = cheque;
        this.lines = new ArrayList<>(cheque.getContent());
    }

    @Override
    public int getRowCount()
    {
        return lines == null ? 0 : lines.size();
    }

    @Override
    public int getColumnCount()
    {
        return 6; //icon, artikul, name, quantity, price, total
    }

    @Override
    public String getColumnName(int column)
    {
        switch(column)
        {
            case 0 : return "";
            case 1 : return "Артикул";
            case 2 : return "Наименование";
            case 3 : return "Количество";
            case 4 : return "Цена";
            case 5 : return "Сумма";               
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
            case 5 : return Double.class;               
        }
        
        return String.class;
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }

    @Override
    public Object getValueAt(int row, int column)
    {
        switch(column)
        {
            case 0 : return ICON_IMAGE;
            case 1 : return lines.get(row).getProduct().getArtikul();
            case 2 : return lines.get(row).getProduct().getName();
            case 3 : return lines.get(row).getQuantity();
            case 4 : return lines.get(row).getPrice();
            case 5 : return lines.get(row).getLineTotal();
        }
        
        return "";
    }

}
