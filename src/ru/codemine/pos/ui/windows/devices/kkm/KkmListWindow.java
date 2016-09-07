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

package ru.codemine.pos.ui.windows.devices.kkm;

import java.util.List;
import javax.swing.table.TableColumnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.device.KkmDevice;
import ru.codemine.pos.service.kkm.KkmService;
import ru.codemine.pos.tablemodel.KkmListTableModel;
import ru.codemine.pos.ui.windows.devices.GenericDeviceListWindow;
import ru.codemine.pos.ui.windows.devices.kkm.listener.NewKkmDevice;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class KkmListWindow extends GenericDeviceListWindow
{
    @Autowired private KkmService kkmService;
    @Autowired private KkmListTableModel tableModel;
    
    @Autowired private NewKkmDevice newKkmDevice;
    
    public KkmListWindow()
    {
        super();
        setTitle("Доступные кассовые аппараты");
        //menuItemProcess.setEnabled(false); change icons & description, move to upper class
        //menuItemUnprocess.setEnabled(false);
        //toolButtonProcess.setEnabled(false);
        //toolButtonUnprocess.setEnabled(false);
    }

    @Override
    public void showWindow()
    {
        List<KkmDevice> kkms = kkmService.getAllKkmDevices();
        
        tableModel.setKkmDevices(kkms);
        table.setModel(tableModel);
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(10);
        columnModel.getColumn(1).setMaxWidth(10);
        columnModel.getColumn(2).setMaxWidth(50);
        
        if(!actionListenersInit) setupActionListeners();
        
        statusLabel.setText("Загружено " + kkms.size() + " строк");
        
        setupSorter();
        setVisible(true);
        
    }

    @Override
    public void setupActionListeners()
    {
        setNewActionListener(newKkmDevice);
        actionListenersInit = true;
    }
    
    public KkmDevice getSelectedDevice()
    {
        if(table.getSelectedRow() == -1) return null;
        
        return tableModel.getDeviceAt(table.getSelectedRow());
    }

    public KkmListTableModel getTableModel()
    {
        return tableModel;
    }

}
