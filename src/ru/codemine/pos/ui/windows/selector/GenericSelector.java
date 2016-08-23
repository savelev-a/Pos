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

package ru.codemine.pos.ui.windows.selector;

import com.alee.extended.layout.TableLayout;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.statusbar.WebStatusBar;
import com.alee.extended.statusbar.WebStatusLabel;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import com.alee.laf.toolbar.WebToolBar;
import javax.swing.ImageIcon;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import ru.codemine.pos.ui.windows.document.GenericDocumentWindow;

/**
 *
 * @author Alexander Savelev
 */
public abstract class GenericSelector extends WebFrame
{
    
    protected WebToolBar toolBar;
    protected WebTextField searchField;
    protected WebTable table;
    protected TableRowSorter<TableModel> sorter;
    protected WebStatusBar statusBar;
    protected WebStatusLabel statusLabel;
    
    protected WebButton selectButton;
    protected WebButton cancelButton;
    protected GroupPanel buttonsGroupPanel;
    
    protected boolean actionListenersInit;
    
    protected GenericDocumentWindow clientWindow;

    public GenericSelector()
    {
        setTitle("");
        setSize(600, 300);
        setLocationRelativeTo(null);
        
        toolBar = new WebToolBar();
        toolBar.setFloatable(false);
        
        searchField = new WebTextField();
        searchField.setMinimumWidth(200);
        searchField.setMaximumWidth(300);
        
        table = new WebTable();
        statusBar = new WebStatusBar();
        statusLabel = new WebStatusLabel();
        
        statusBar.add(statusLabel);
        
        selectButton   = new WebButton("Выбрать", new ImageIcon("images/icons/default/16x16/button-ok.png"));
        cancelButton = new WebButton("Отмена",    new ImageIcon("images/icons/default/16x16/button-cancel.png"));
        selectButton.setMargin(5);
        cancelButton.setMargin(5);
        selectButton.setRound(StyleConstants.largeRound);
        cancelButton.setRound(StyleConstants.largeRound);
        buttonsGroupPanel = new GroupPanel(10, selectButton, cancelButton);
        
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.FILL, 10},
            {10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10, TableLayout.PREFERRED, 10, TableLayout.PREFERRED}
        });
        setLayout(layout);
        
        add(searchField, "1, 1, R, T");
        add(new WebScrollPane(table), "0, 3, 2, 3, F, F");
        add(buttonsGroupPanel, "1, 5, C, T");
        add(statusBar, "0, 7, 2, 7, F, F");
    }
    
    protected void setupSorter()
    {
        sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
    }
    
    public abstract void selectFor(GenericDocumentWindow window);
    public abstract void setupActionListeners();
}
