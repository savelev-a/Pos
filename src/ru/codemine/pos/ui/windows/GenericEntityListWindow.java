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

package ru.codemine.pos.ui.windows;

import com.alee.extended.layout.TableLayout;
import com.alee.extended.statusbar.WebStatusBar;
import com.alee.extended.statusbar.WebStatusLabel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.MenuBarStyle;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.toolbar.ToolbarStyle;
import com.alee.laf.toolbar.WebToolBar;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Alexander Savelev
 */

public abstract class GenericEntityListWindow extends WebFrame
{
    protected WebMenuBar menuBar;
    protected WebToolBar toolBar;
    protected WebTable table;
    protected WebStatusBar statusBar;
    protected WebStatusLabel statusLabel;
    
    protected WebMenu operationsMenu;
    protected WebMenu viewMenu;
    
    protected WebMenuItem menuItemNew;
    protected WebMenuItem menuItemEdit;
    protected WebMenuItem menuItemDelete;
    protected WebMenuItem menuItemProcess;
    protected WebMenuItem menuItemUnprocess;
    protected WebMenuItem menuItemRefresh;
    protected WebMenuItem menuItemClose;
    
    protected WebButton toolButtonNew;
    protected WebButton toolButtonEdit;
    protected WebButton toolButtonDelete;
    protected WebButton toolButtonProcess;
    protected WebButton toolButtonUnprocess;
    protected WebButton toolButtonRefresh; 
    
    protected boolean actionListenersInit;
    
    public GenericEntityListWindow()
    {
        setTitle("");
        setMinimumSize(new Dimension(800, 400));
        setLocationRelativeTo(null);
        
        TableLayout layout = new TableLayout(new double[][]{
            {TableLayout.FILL},
            {TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.FILL, TableLayout.PREFERRED}
        });
        setLayout(layout);
        
        menuBar = new WebMenuBar(MenuBarStyle.attached);
        toolBar = new WebToolBar(ToolbarStyle.attached);
        table = new WebTable();
        statusBar = new WebStatusBar();
        statusLabel = new WebStatusLabel();
        
        operationsMenu = new WebMenu("Документ");
        viewMenu = new WebMenu("Вид");
        menuBar.add(operationsMenu);
        menuBar.add(viewMenu);
        statusBar.add(statusLabel);
        
        WebScrollPane scrollPane = new WebScrollPane(table);
        
        add(menuBar,    "0, 0");
        add(toolBar,    "0, 1");
        add(scrollPane, "0, 2");
        add(statusBar,  "0, 3");
        
        menuItemNew       = new WebMenuItem("Создать",  new ImageIcon("images/icons/default/16x16/document-new.png"));
        menuItemEdit      = new WebMenuItem("Просмотр", new ImageIcon("images/icons/default/16x16/document-edit.png"));
        menuItemDelete    = new WebMenuItem("Удалить",  new ImageIcon("images/icons/default/16x16/document-delete.png"));
        menuItemProcess   = new WebMenuItem("Провести");
        menuItemUnprocess = new WebMenuItem("Отмена проведения");
        menuItemRefresh   = new WebMenuItem("Обновить", new ImageIcon("images/icons/default/16x16/button-refresh.png"));
        menuItemClose     = new WebMenuItem("Выход");
        
        toolButtonNew       = new WebButton(new ImageIcon("images/icons/default/16x16/document-new.png"));
        toolButtonEdit      = new WebButton(new ImageIcon("images/icons/default/16x16/document-edit.png"));
        toolButtonDelete    = new WebButton(new ImageIcon("images/icons/default/16x16/document-delete.png"));
        toolButtonProcess   = new WebButton(new ImageIcon("images/icons/default/16x16/document-process.png"));
        toolButtonUnprocess = new WebButton(new ImageIcon("images/icons/default/16x16/document-unprocess.png"));
        toolButtonRefresh   = new WebButton(new ImageIcon("images/icons/default/16x16/button-refresh.png"));
        
        toolButtonNew.setRolloverDecoratedOnly(true);
        toolButtonEdit.setRolloverDecoratedOnly(true);
        toolButtonDelete.setRolloverDecoratedOnly(true);
        toolButtonProcess.setRolloverDecoratedOnly(true);
        toolButtonUnprocess.setRolloverDecoratedOnly(true);
        toolButtonRefresh.setRolloverDecoratedOnly(true);
        
        operationsMenu.add(menuItemNew);
        operationsMenu.addSeparator();
        operationsMenu.add(menuItemEdit);
        operationsMenu.add(menuItemDelete);
        operationsMenu.addSeparator();
        operationsMenu.add(menuItemProcess);
        operationsMenu.add(menuItemUnprocess);
        operationsMenu.addSeparator();
        operationsMenu.add(menuItemClose);

        viewMenu.add(menuItemRefresh);
        
        toolBar.add(toolButtonNew);
        toolBar.addSeparator();
        toolBar.add(toolButtonEdit);
        toolBar.add(toolButtonDelete);
        toolBar.addSeparator();
        toolBar.add(toolButtonProcess);
        toolBar.add(toolButtonUnprocess);
        toolBar.addSeparator();
        toolBar.add(toolButtonRefresh);
        
        actionListenersInit = false;
        
        menuItemClose.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        });
    }
    
    public void setNewActionListener(ActionListener al)
    {
        menuItemNew.addActionListener(al);
        toolButtonNew.addActionListener(al);
    }
    
    public void setEditActionListener(ActionListener al)
    {
        menuItemEdit.addActionListener(al);
        toolButtonEdit.addActionListener(al);
    }
    
    public void setDeleteActionListener(ActionListener al)
    {
        menuItemDelete.addActionListener(al);
        toolButtonDelete.addActionListener(al);
    }
    
    public void setProcessActionListener(ActionListener al)
    {
        menuItemProcess.addActionListener(al);
        toolButtonProcess.addActionListener(al);
    }
    
    public void setUnprocessActionListener(ActionListener al)
    {
        menuItemUnprocess.addActionListener(al);
        toolButtonUnprocess.addActionListener(al);
    }
    
    public void setRefreshActionListener(ActionListener al)
    {
        menuItemRefresh.addActionListener(al);
        toolButtonRefresh.addActionListener(al);
    }
    
    public WebLabel getStatusLabel()
    {
        return statusLabel;
    }
    
    protected void setupSorter()
    {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
    }
    
    protected void setReadOnly(boolean b)
    {
        menuItemNew.setEnabled(!b);
        menuItemDelete.setEnabled(!b);
        menuItemProcess.setEnabled(!b);
        menuItemUnprocess.setEnabled(!b);
        
        toolButtonNew.setEnabled(!b);
        toolButtonDelete.setEnabled(!b);
        toolButtonProcess.setEnabled(!b);
        toolButtonUnprocess.setEnabled(!b);
        
        String iconStr = "images/icons/default/16x16/document-" + (b ? "view" : "edit")  + ".png";
        toolButtonEdit.setIcon(new ImageIcon(iconStr));
        menuItemEdit.setIcon(new ImageIcon(iconStr));
        
    }
    
    public void refresh()
    {
        menuItemRefresh.doClick();
    }
    
    public abstract void showWindow();
    public abstract void setupActionListeners();
}
