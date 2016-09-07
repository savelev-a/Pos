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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.entity.device.KkmDevice;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class KkmListTableModel extends DefaultTableModel
{
    @Autowired Application application;
    
    private List<KkmDevice> kkmDevices;
    private static final ImageIcon KKM_ICON = new ImageIcon("images/icons/default/16x16/kkm.png");

    public KkmListTableModel()
    {
        this.kkmDevices = new ArrayList<>();
    }

    public KkmListTableModel(List<KkmDevice> kkmDevices)
    {
        this.kkmDevices = kkmDevices;
    }

    @Override
    public int getRowCount()
    {
        return kkmDevices == null ? 0 : kkmDevices.size();
    }

    @Override
    public int getColumnCount()
    {
        return 7; //ico, active, id, type, s/n, k/n, description
    }

    @Override
    public String getColumnName(int column)
    {
        switch(column)
        {
            case 0 : return "";
            case 1 : return "";
            case 2 : return "№";
            case 3 : return "Тип кассы";
            case 4 : return "Серийный номер";
            case 5 : return "Номер ККМ";
            case 6 : return "Описание";
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
            case 6 : return String.class;
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
            case 0 : return KKM_ICON;
            case 1 : return application.getCurrentKkm().getDevice().equals(kkmDevices.get(row)) ? "A" : " ";
            case 2 : return kkmDevices.get(row).getId();
            case 3 : return kkmDevices.get(row).getName();
            case 4 : return kkmDevices.get(row).getSerialNumber();
            case 5 : return kkmDevices.get(row).getKkmNumber();
            case 6 : return kkmDevices.get(row).getDescription();
        }
        
        return "";
    }

    public void setKkmDevices(List<KkmDevice> kkmDevices)
    {
        this.kkmDevices = kkmDevices;
    }
    
    public KkmDevice getDeviceAt(int selectedRow)
    {
        return kkmDevices.get(selectedRow);
    }
    
    
    
    
}
