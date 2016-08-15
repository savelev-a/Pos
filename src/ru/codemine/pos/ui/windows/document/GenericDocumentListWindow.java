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

package ru.codemine.pos.ui.windows.document;

import com.alee.extended.layout.TableLayout;
import com.alee.extended.statusbar.WebStatusBar;
import com.alee.extended.statusbar.WebStatusLabel;
import com.alee.laf.button.WebButton;
import com.alee.laf.menu.MenuBarStyle;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.toolbar.ToolbarStyle;
import com.alee.laf.toolbar.WebToolBar;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

/**
 *
 * @author Alexander Savelev
 */
public class GenericDocumentListWindow extends WebFrame
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
    protected WebMenuItem menuItemRefresh;
    
    protected WebButton toolButtonNew;
    protected WebButton toolButtonEdit;
    protected WebButton toolButtonDelete;
    protected WebButton toolButtonRefresh; 
    
    public GenericDocumentListWindow()
    {
        setTitle("");
        setSize(640, 400);
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
        
        operationsMenu = new WebMenu("Действия");
        viewMenu = new WebMenu("Вид");
        menuBar.add(operationsMenu);
        menuBar.add(viewMenu);
        statusBar.add(statusLabel);
        
        WebScrollPane scrollPane = new WebScrollPane(table);
        
        add(menuBar,    "0, 0");
        add(toolBar,    "0, 1");
        add(scrollPane, "0, 2");
        add(statusBar,  "0, 3");
        
        menuItemNew     = new WebMenuItem("Создать",       new ImageIcon("images/icons/16x16/Document New-01.png"));
        menuItemEdit    = new WebMenuItem("Редактировать", new ImageIcon("images/icons/16x16/Tool-01.png"));
        menuItemDelete  = new WebMenuItem("Удалить",       new ImageIcon("images/icons/16x16/Document Delete-01.png"));
        menuItemRefresh = new WebMenuItem("Обновить",      new ImageIcon("images/icons/16x16/Button Refresh-01.png"));
        
        toolButtonNew     = new WebButton(new ImageIcon("images/icons/16x16/Document New-01.png"));
        toolButtonEdit    = new WebButton(new ImageIcon("images/icons/16x16/Tool-01.png"));
        toolButtonDelete  = new WebButton(new ImageIcon("images/icons/16x16/Document Delete-01.png"));
        toolButtonRefresh = new WebButton(new ImageIcon("images/icons/16x16/Button Refresh-01.png"));
        
        toolButtonNew.setRolloverDecoratedOnly(true);
        toolButtonEdit.setRolloverDecoratedOnly(true);
        toolButtonDelete.setRolloverDecoratedOnly(true);
        toolButtonRefresh.setRolloverDecoratedOnly(true);
        
        operationsMenu.add(menuItemNew);
        operationsMenu.addSeparator();
        operationsMenu.add(menuItemEdit);
        operationsMenu.add(menuItemDelete);

        viewMenu.add(menuItemRefresh);
        
        toolBar.add(toolButtonNew);
        toolBar.addSeparator();
        toolBar.add(toolButtonEdit);
        toolBar.add(toolButtonDelete);
        toolBar.addSeparator();
        toolBar.add(toolButtonRefresh);
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
    
    public void setRefreshActionListener(ActionListener al)
    {
        menuItemRefresh.addActionListener(al);
        toolButtonRefresh.addActionListener(al);
    }
}
