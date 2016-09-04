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

/**
 *
 * @author Alexander Savelev
 */
public class ChequeListTableModel extends DefaultTableModel
{
    private List<Cheque> chequeList;
    private static final ImageIcon PROCESSED_ICON = new ImageIcon("images/icons/default/16x16/document-processed.png");
    private static final ImageIcon UNPROCESSED_ICON = new ImageIcon("images/icons/default/16x16/document-unprocessed.png");

    public ChequeListTableModel()
    {
        this.chequeList = new ArrayList<>();
    }

    public ChequeListTableModel(List<Cheque> chequeList)
    {
        this.chequeList = chequeList;
    }

    @Override
    public int getRowCount()
    {
        return chequeList == null ? 0 : chequeList.size();
    }

    @Override
    public int getColumnCount()
    {
        return 7; //icon, id, type, time, creator, workday, total
    }

    @Override
    public String getColumnName(int column)
    {
        switch(column)
        {
            case 0 : return "";
            case 1 : return "№";
            case 2 : return "Тип документа";
            case 3 : return "Время";
            case 4 : return "Продавец";
            case 5 : return "№ смены";
            case 6 : return "Сумма";
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
            case 4 : return String.class;
            case 5 : return String.class;
            case 6 : return Double.class;
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
            case 0 : return chequeList.get(row).isProcessed() ? PROCESSED_ICON : UNPROCESSED_ICON;
            case 1 : return chequeList.get(row).getId().toString();
            case 2 : return "Чек ККМ";
            case 3 : return chequeList.get(row).getCreationTime().toString("dd.MM.YY HH:mm");
            case 4 : return chequeList.get(row).getCreator().getPrintName();
            case 5 : return chequeList.get(row).getWorkday().getId().toString();
            case 6 : return chequeList.get(row).getChequeTotal();
        }
        
        return "";
    }
    
    public void setChequeList(List<Cheque> chequeList)
    {
        this.chequeList = chequeList;
    }
    
    public Cheque getChequeAt(int selectedRow)
    {
        return chequeList.get(selectedRow);
    }
}
