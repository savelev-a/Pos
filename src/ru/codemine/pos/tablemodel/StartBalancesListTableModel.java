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
import ru.codemine.pos.entity.document.StartBalance;

/**
 *
 * @author Alexander Savelev
 */
public class StartBalancesListTableModel extends DefaultTableModel
{
    private List<StartBalance> sblist;
    private static final ImageIcon PROCESSED_ICON = new ImageIcon("images/icons/default/16x16/document-processed.png");
    private static final ImageIcon UNPROCESSED_ICON = new ImageIcon("images/icons/default/16x16/document-unprocessed.png");
    
    public StartBalancesListTableModel(List<StartBalance> sblist)
    {
        this.sblist = sblist;
    }
    
    public StartBalancesListTableModel()
    {
        this.sblist = new ArrayList<>();
    }
    
    @Override
    public int getRowCount()
    {
        return sblist == null ? 0 : sblist.size();
    }
    
    @Override
    public int getColumnCount()
    {
        return 7; //pic, id, desc, cr_time, doc_time, cr_username, doc_sum
    }
    
    @Override
    public String getColumnName(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0 : return "";
            case 1 : return "№";
            case 2 : return "Тип документа";
            case 3 : return "Дата создания";
            case 4 : return "Дата документа";
            case 5 : return "Создал пользователь";
            case 6 : return "Сумма документа";
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
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        switch(columnIndex)
        {
            case 0 : return sblist.get(rowIndex).isProcessed() ? PROCESSED_ICON : UNPROCESSED_ICON;
            case 1 : return sblist.get(rowIndex).getId().toString();
            case 2 : return "Начальные остатки";
            case 3 : return sblist.get(rowIndex).getCreationTime().toString("dd.MM.YY HH:mm");
            case 4 : return sblist.get(rowIndex).getDocumentTime().toString("dd.MM.YY HH:mm");
            case 5 : return sblist.get(rowIndex).getCreator().getUsername();
            case 6 : return sblist.get(rowIndex).getTotal();
        }
        
        return "";
    }
    
    public void setStartBalancesList(List<StartBalance> sblist)
    {
        this.sblist = sblist;
    }
    
    public StartBalance getSbAt(int selectedRow)
    {
        return sblist.get(selectedRow);
    }
}
