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

import com.alee.extended.date.WebDateField;
import com.alee.extended.panel.GroupPanel;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import com.alee.laf.toolbar.ToolbarStyle;
import com.alee.laf.toolbar.WebToolBar;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import ru.codemine.pos.entity.GenericEntity;
import ru.codemine.pos.entity.document.Document;

/**
 *
 * @author Alexander Savelev
 * @param <T>
 */
public abstract class GenericDocumentWindow<T extends Document> extends WebFrame
{
    protected WebLabel idLabel;
    protected WebLabel creationTimeLabel;
    protected WebLabel documentTimeLabel;
    protected WebLabel creatorLabel;
    protected WebLabel contentLabel;
    
    protected WebLabel idField;
    protected WebDateField creationTimeField;
    protected WebDateField documentTimeField;
    protected WebTextField creatorField;
    protected WebTable contentTable;
    
    protected WebToolBar tableToolBar;
    protected WebButton addItemToolButton;
    protected WebButton removeItemToolButton;
    protected WebButton setQuantityToolButton;
    
    protected boolean actionListenersInit;
    
    protected WebButton saveButton;
    protected WebButton cancelButton;
    protected GroupPanel buttonsGroupPanel;
    
    public GenericDocumentWindow()
    {
        setTitle("");
        setMinimumSize(new Dimension(800, 400));
        setLocationRelativeTo(null);
        
        idLabel = new WebLabel("№ документа");
        creationTimeLabel = new WebLabel("Дата создания");
        documentTimeLabel = new WebLabel("Дата документа");
        creatorLabel = new WebLabel("Создатель");
        contentLabel = new WebLabel("");
    
        idField = new WebLabel();
        creationTimeField = new WebDateField();
        documentTimeField = new WebDateField();
        creatorField = new WebTextField();
    
        contentTable = new WebTable();
        
        tableToolBar          = new WebToolBar();
        tableToolBar.setToolbarStyle(ToolbarStyle.attached);
        tableToolBar.setFloatable(false);
        tableToolBar.setUndecorated(true);
        addItemToolButton     = new WebButton(new ImageIcon("images/icons/default/16x16/toolbutton-add.png"));
        removeItemToolButton  = new WebButton(new ImageIcon("images/icons/default/16x16/toolbutton-remove.png"));
        setQuantityToolButton = new WebButton(new ImageIcon("images/icons/default/16x16/toolbutton-setq.png"));
        
        addItemToolButton.setRolloverDecoratedOnly(true);
        removeItemToolButton.setRolloverDecoratedOnly(true);
        setQuantityToolButton.setRolloverDecoratedOnly(true);
        
        tableToolBar.add(addItemToolButton);
        tableToolBar.add(removeItemToolButton);
        tableToolBar.addSeparator();
        tableToolBar.add(setQuantityToolButton);
        
        actionListenersInit = false;
                
        saveButton   = new WebButton("Сохранить", new ImageIcon("images/icons/default/16x16/button-ok.png"));
        cancelButton = new WebButton("Отмена",    new ImageIcon("images/icons/default/16x16/button-cancel.png"));
        saveButton.setMargin(5);
        cancelButton.setMargin(5);
        saveButton.setRound(StyleConstants.largeRound);
        cancelButton.setRound(StyleConstants.largeRound);
        buttonsGroupPanel = new GroupPanel(10, saveButton, cancelButton);
        
        
    }
    
    public abstract void showWindow(T t);
    public abstract void setupActionListeners();
    public abstract T getDocument();
    public abstract void addItem(GenericEntity item);
}
