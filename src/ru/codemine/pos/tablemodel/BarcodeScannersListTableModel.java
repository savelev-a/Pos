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
import ru.codemine.pos.entity.device.BarcodeScannerDevice;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class BarcodeScannersListTableModel extends DefaultTableModel
{
    @Autowired private Application application;
    
    private List<BarcodeScannerDevice> devices;
    private static final ImageIcon SCANNER_ICON = new ImageIcon("images/icons/default/16x16/scanner.png");

    public BarcodeScannersListTableModel()
    {
        this.devices = new ArrayList<>();
    }

    public BarcodeScannersListTableModel(List<BarcodeScannerDevice> devices)
    {
        this.devices = devices;
    }

    @Override
    public int getRowCount()
    {
        return devices == null ? 0 : devices.size();
    }

    @Override
    public int getColumnCount()
    {
        return 5; //ico, active, id, type, description
    }
    
    @Override
    public String getColumnName(int column)
    {
        switch(column)
        {
            case 0 : return "";
            case 1 : return "";
            case 2 : return "№";
            case 3 : return "Тип сканера";
            case 4 : return "Описание";

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
            case 0 : return SCANNER_ICON;
            case 1 : return application.getCurrentScanner().equals(devices.get(row)) ? "A" : " ";
            case 2 : return devices.get(row).getId();
            case 3 : return devices.get(row).getName();
            case 4 : return devices.get(row).getDescription();
        }
        
        return "";
    }
    
    public void setBarcodeScannerDevices(List<BarcodeScannerDevice> devices)
    {
        this.devices = devices;
    }
    
    public BarcodeScannerDevice getDeviceAt(int selectedRow)
    {
        return devices.get(selectedRow);
    }
    
    
}
