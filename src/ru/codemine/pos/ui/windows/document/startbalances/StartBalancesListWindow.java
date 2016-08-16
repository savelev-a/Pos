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

package ru.codemine.pos.ui.windows.document.startbalances;

import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.table.TableColumnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.Store;
import ru.codemine.pos.entity.document.StartBalance;
import ru.codemine.pos.service.StartBalanceService;
import ru.codemine.pos.service.StoreService;
import ru.codemine.pos.tablemodel.StartBalancesListTableModel;
import ru.codemine.pos.ui.windows.document.GenericDocumentListWindow;
import ru.codemine.pos.ui.windows.document.startbalances.listener.DeleteSb;
import ru.codemine.pos.ui.windows.document.startbalances.listener.EditSb;
import ru.codemine.pos.ui.windows.document.startbalances.listener.NewSb;
import ru.codemine.pos.ui.windows.document.startbalances.listener.ProcessSb;
import ru.codemine.pos.ui.windows.document.startbalances.listener.RefreshSbList;
import ru.codemine.pos.ui.windows.document.startbalances.listener.UnprocessSb;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class StartBalancesListWindow extends GenericDocumentListWindow
{
    @Autowired private StartBalanceService startBalanceService;
    @Autowired private StoreService storeService;
    
    @Autowired private NewSb newSb;
    @Autowired private EditSb editSb;
    @Autowired private DeleteSb deleteSb;
    @Autowired private ProcessSb processSb;
    @Autowired private UnprocessSb unprocessSb;
    @Autowired private RefreshSbList refreshSbList;
    
    private final WebLabel storeChooseLabel;
    private final WebComboBox storeChooseBox;
    
    private StartBalancesListTableModel tableModel;
    
    public StartBalancesListWindow()
    {
        super();
        setTitle("Документы начальных остатков");
        setSize(800, 400);
        
        storeChooseLabel = new WebLabel("Показать документы по складу:");
        storeChooseBox = new WebComboBox();
        
        toolBar.addSeparator();
        toolBar.add(storeChooseLabel);
        toolBar.add(storeChooseBox);
    }
    
    public void showWindow()
    {
        List<Store> stores = storeService.getAll();
        storeChooseBox.removeAllItems();
        for(Store s : stores)
        {
            storeChooseBox.addItem(s.getName());
        }
        
        List<StartBalance> startBalances = startBalanceService.getAllByStore(stores.get(0));
        
        tableModel = new StartBalancesListTableModel(startBalances);
        table.setModel(tableModel);
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(10);
        columnModel.getColumn(1).setMaxWidth(50);
        
        if(!actionListenersInit) setupActionListeners();
        
        statusLabel.setText("Загружено " + startBalances.size() + " строк");
        
        setupSorter();
        setVisible(true);
    }

    private void setupActionListeners()
    {
        setNewActionListener(newSb);
        setEditActionListener(editSb);
        setDeleteActionListener(deleteSb);
        setProcessActionListener(processSb);
        setUnprocessActionListener(unprocessSb);
        setRefreshActionListener(refreshSbList);
        
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
    
    public WebComboBox getStoreChooseBox()
    {
        return storeChooseBox;
    }
    
    public StartBalance getSelectedDocument()
    {
        if(table.getSelectedRow() == -1) return null;
        
        return tableModel.getSbAt(table.getSelectedRow());
    }
    
    public StartBalancesListTableModel getTableModel()
    {
        return tableModel;
    }
}
