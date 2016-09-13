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

package ru.codemine.pos.ui.windows.devices.barcodescanner;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.table.TableColumnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.device.BarcodeScannerDevice;
import ru.codemine.pos.service.device.barcodescanner.BarcodeScannerService;
import ru.codemine.pos.tablemodel.BarcodeScannersListTableModel;
import ru.codemine.pos.ui.windows.devices.GenericDeviceListWindow;
import ru.codemine.pos.ui.windows.devices.barcodescanner.listener.DeleteBarcodeScannerDevice;
import ru.codemine.pos.ui.windows.devices.barcodescanner.listener.EditBarcodeScannerDevice;
import ru.codemine.pos.ui.windows.devices.barcodescanner.listener.NewBarcodeScannerDevice;
import ru.codemine.pos.ui.windows.devices.barcodescanner.listener.RefreshBarcodeScannerDeviceList;
import ru.codemine.pos.ui.windows.devices.barcodescanner.listener.SetActiveBarcodeScannerDevice;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class BarcodeScannerListWindow extends GenericDeviceListWindow
{
    @Autowired private BarcodeScannerService barcodeScannerService;
    @Autowired private BarcodeScannersListTableModel tableModel;
    
    @Autowired private NewBarcodeScannerDevice newBarcodeScannerDevice;
    @Autowired private EditBarcodeScannerDevice editBarcodeScannerDevice;
    @Autowired private DeleteBarcodeScannerDevice deleteBarcodeScannerDevice;
    @Autowired private SetActiveBarcodeScannerDevice setActiveBarcodeScannerDevice;
    @Autowired private RefreshBarcodeScannerDeviceList refreshBarcodeScannerDeviceList;

    public BarcodeScannerListWindow()
    {
        super();
        setTitle("Сканеры ШК");
        
        toolBar.removeAll();
        toolBar.add(toolButtonNew);
        toolBar.add(toolButtonDelete);
        toolBar.addSeparator();
        toolBar.add(toolButtonEdit);
        toolBar.add(toolButtonProcess);
        toolBar.addSeparator();
        toolBar.add(toolButtonRefresh);
        
        operationsMenu.remove(menuItemUnprocess);
    }
    
    

    @Override
    public void showWindow()
    {
        List<BarcodeScannerDevice> devices = barcodeScannerService.getAllScanners();
        
        tableModel.setBarcodeScannerDevices(devices);
        table.setModel(tableModel);
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(10);
        columnModel.getColumn(1).setMaxWidth(10);
        columnModel.getColumn(2).setMaxWidth(50);
        
        if(!actionListenersInit) setupActionListeners();
        
        statusLabel.setText("Загружено " + devices.size() + " строк");
        
        setupSorter();
        setVisible(true);
    }

    @Override
    public void setupActionListeners()
    {
        setNewActionListener(newBarcodeScannerDevice);
        setEditActionListener(editBarcodeScannerDevice);
        setDeleteActionListener(deleteBarcodeScannerDevice);
        setProcessActionListener(setActiveBarcodeScannerDevice);
        setRefreshActionListener(refreshBarcodeScannerDeviceList);
        
        table.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                Point p = e.getPoint();
                int row = table.rowAtPoint(p);
                if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1)
                {
                    menuItemEdit.doClick();
                }
            }
        });
        
        actionListenersInit = true;
    }
    
    public BarcodeScannerDevice getSelectedDevice()
    {
        if(table.getSelectedRow() == -1) return null;
        
        return tableModel.getDeviceAt(table.getSelectedRow());
    }
    
    public BarcodeScannersListTableModel getTableModel()
    {
        return tableModel;
    }

}
