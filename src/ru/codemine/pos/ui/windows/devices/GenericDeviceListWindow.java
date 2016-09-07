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

package ru.codemine.pos.ui.windows.devices;

import javax.swing.ImageIcon;
import ru.codemine.pos.ui.windows.GenericEntityListWindow;

/**
 *
 * @author Alexander Savelev
 */

public abstract class GenericDeviceListWindow extends GenericEntityListWindow
{
    public GenericDeviceListWindow()
    {
        super();
        operationsMenu.setText("Устройство");
        menuItemProcess.setText("Включить");
        menuItemUnprocess.setText("Отключить");
        menuItemEdit.setText("Настроить устройство");
        menuItemNew.setIcon(new ImageIcon("images/icons/default/16x16/device-add.png"));
        menuItemDelete.setIcon(new ImageIcon("images/icons/default/16x16/device-remove.png"));
        menuItemEdit.setIcon(new ImageIcon("images/icons/default/16x16/device-config.png"));
        
        
        toolButtonNew.setIcon(new ImageIcon("images/icons/default/16x16/device-add.png"));
        toolButtonDelete.setIcon(new ImageIcon("images/icons/default/16x16/device-remove.png"));
        toolButtonEdit.setIcon(new ImageIcon("images/icons/default/16x16/device-config.png"));
    }
}
