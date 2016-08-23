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

package ru.codemine.pos.ui.windows.stores.listener;

import com.alee.laf.optionpane.WebOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.Store;
import ru.codemine.pos.exception.DuplicateStoreDataException;
import ru.codemine.pos.service.StoreService;
import ru.codemine.pos.ui.windows.stores.StoreWindow;
import ru.codemine.pos.ui.windows.stores.StoresListWindow;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class SaveStore implements ActionListener
{
    @Autowired private StoreWindow storeWindow;
    @Autowired private StoresListWindow storesListWindow;
    @Autowired private StoreService storeService;
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Store store = storeWindow.getStore();
        if(store.getId() == null)
        {
            try
            {
                storeService.create(store);
            } 
            catch (DuplicateStoreDataException ex)
            {
                WebOptionPane.showMessageDialog(storeWindow, ex.getLocalizedMessage(), "Ошибка сохранения склада", WebOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else
        {
            try
            {
                storeService.update(store);
            } 
            catch (DuplicateStoreDataException ex)
            {
                WebOptionPane.showMessageDialog(storeWindow, ex.getLocalizedMessage(), "Ошибка сохранения склада", WebOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        storeWindow.setVisible(false);
        storesListWindow.refresh();
        
    }

}