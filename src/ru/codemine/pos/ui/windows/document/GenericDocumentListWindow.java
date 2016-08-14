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
import com.alee.laf.menu.MenuBarStyle;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.table.WebTable;
import com.alee.laf.toolbar.ToolbarStyle;
import com.alee.laf.toolbar.WebToolBar;
import javax.swing.WindowConstants;

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
    
    protected WebMenu operationsMenu;
    protected WebMenu viewMenu;
    
    public GenericDocumentListWindow()
    {
        setTitle("");
        setSize(640, 400);
        setLocationRelativeTo(null);
        
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.FILL, 10},
            {TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED}
        });
        setLayout(layout);
        
        menuBar = new WebMenuBar(MenuBarStyle.attached);
        toolBar = new WebToolBar(ToolbarStyle.attached);
        table = new WebTable();
        statusBar = new WebStatusBar();
        
        operationsMenu = new WebMenu("Действия");
        viewMenu = new WebMenu("Вид");
        menuBar.add(operationsMenu);
        menuBar.add(viewMenu);
        
        add(menuBar, "1, 0");
        add(toolBar, "1, 1");
        add(table, "1, 2");
        add(statusBar, "1, 3");
        
        
    }
}
