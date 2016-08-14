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

import com.alee.laf.menu.WebMenuItem;

/**
 *
 * @author Alexander Savelev
 */
public class StoresListWindow extends GenericDocumentListWindow
{
    private final WebMenuItem newStoreMenuItem;
    private final WebMenuItem editStoreMenuItem;
    private final WebMenuItem deleteStoreMenuItem;
    
    public StoresListWindow()
    {
        super();
        
        newStoreMenuItem = new WebMenuItem("Новый склад");
        editStoreMenuItem = new WebMenuItem("Редактировать склад");
        deleteStoreMenuItem = new WebMenuItem("Удалить склад");
        
        operationsMenu.add(newStoreMenuItem);
        operationsMenu.addSeparator();
        operationsMenu.add(editStoreMenuItem);
        operationsMenu.add(deleteStoreMenuItem);
    }
}
