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

import com.alee.extended.panel.GroupPanel;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.rootpane.WebFrame;
import javax.swing.ImageIcon;
import ru.codemine.pos.entity.document.Document;

/**
 *
 * @author Alexander Savelev
 * @param <T>
 */
public abstract class GenericEntityWindow<T extends Object> extends WebFrame
{
    
    protected boolean actionListenersInit;
    
    protected WebButton saveButton;
    protected WebButton cancelButton;
    protected GroupPanel buttonsGroupPanel;
    
    public GenericEntityWindow()
    {
        setTitle("");
        setSize(640, 400);
        setLocationRelativeTo(null);
        
        actionListenersInit = false;
                
        saveButton   = new WebButton("Ок",     new ImageIcon("images/icons/default/16x16/button-ok.png"));
        cancelButton = new WebButton("Отмена", new ImageIcon("images/icons/default/16x16/button-cancel.png"));
        saveButton.setMargin(5);
        cancelButton.setMargin(5);
        saveButton.setRound(StyleConstants.largeRound);
        cancelButton.setRound(StyleConstants.largeRound);
        buttonsGroupPanel = new GroupPanel(10, saveButton, cancelButton);
    }
    
    public abstract void showWindow(T t);
    public abstract void setupActionListeners();
    
}
