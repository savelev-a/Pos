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

import com.alee.laf.button.WebButton;
import com.alee.laf.menu.WebMenuItem;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.TableColumnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.device.KkmDevice;
import ru.codemine.pos.service.kkm.KkmService;
import ru.codemine.pos.tablemodel.KkmListTableModel;
import ru.codemine.pos.ui.windows.devices.GenericDeviceListWindow;
import ru.codemine.pos.ui.windows.devices.kkm.listener.DeleteKkm;
import ru.codemine.pos.ui.windows.devices.kkm.listener.EditKkmDevice;
import ru.codemine.pos.ui.windows.devices.kkm.listener.NewKkmDevice;
import ru.codemine.pos.ui.windows.devices.kkm.listener.RefreshKkmDeviceList;
import ru.codemine.pos.ui.windows.devices.kkm.listener.SetActiveKkm;
import ru.codemine.pos.ui.windows.devices.kkm.listener.TestKkmDevice;

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
    @Autowired private EditKkmDevice editKkmDevice;
    @Autowired private DeleteKkm deleteKkm;
    @Autowired private SetActiveKkm setActiveKkm;
    @Autowired private RefreshKkmDeviceList refreshKkmDeviceList;
    @Autowired private TestKkmDevice testKkmDevice;
    
    private final WebButton toolButtonTestCheque;
    
    public KkmListWindow()
    {
        super();
        setTitle("Доступные кассовые аппараты");
        
        toolButtonTestCheque = new WebButton();
        toolButtonTestCheque.setRolloverDecoratedOnly(true);
        toolButtonTestCheque.setToolTip("Проверить работу ККМ");
        toolButtonTestCheque.setIcon(new ImageIcon("images/icons/default/16x16/test-kkm.png"));
        toolBar.addSeparator();
        toolBar.add(toolButtonTestCheque);
        
        toolBar.removeAll();
        toolBar.add(toolButtonNew);
        toolBar.add(toolButtonDelete);
        toolBar.addSeparator();
        toolBar.add(toolButtonEdit);
        toolBar.add(toolButtonProcess);
        toolBar.add(toolButtonTestCheque);
        toolBar.addSeparator();
        toolBar.add(toolButtonRefresh);
        
        operationsMenu.remove(menuItemUnprocess);
        
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
        setEditActionListener(editKkmDevice);
        setDeleteActionListener(deleteKkm);
        setProcessActionListener(setActiveKkm);
        setRefreshActionListener(refreshKkmDeviceList);
        toolButtonTestCheque.addActionListener(testKkmDevice);
        
        
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
