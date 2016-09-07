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

package ru.codemine.pos.ui.windows.devices.kkm.listener;

import com.alee.laf.optionpane.WebOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.device.KkmDevice;
import ru.codemine.pos.exception.DuplicateDeviceDataException;
import ru.codemine.pos.service.kkm.KkmService;
import ru.codemine.pos.ui.windows.devices.kkm.KkmListWindow;
import ru.codemine.pos.ui.windows.devices.kkm.KkmWindow;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class SaveKkmDevice implements ActionListener
{
    @Autowired private KkmListWindow kkmListWindow;
    @Autowired private KkmWindow kkmWindow;
    @Autowired private KkmService kkmService;
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        KkmDevice device = kkmWindow.getKkmDevice();
        
        if(device == null) return;
        
        if(device.getId() == null)
        {
            try
            {
                kkmService.createDevice(device);
            } 
            catch (DuplicateDeviceDataException ex)
            {
                WebOptionPane.showMessageDialog(kkmWindow, ex.getLocalizedMessage(), "Ошибка сохранения устройства", WebOptionPane.ERROR_MESSAGE);
                return;  
            }
        }
        else
        {
            //try
            //{
            //    kkmService.updateDevice(device);
            //} 
            //catch (DuplicateDeviceDataException ex)
            //{
            //    WebOptionPane.showMessageDialog(kkmWindow, ex.getLocalizedMessage(), "Ошибка сохранения устройства", WebOptionPane.ERROR_MESSAGE);
            //    return;  
            //}
        }
        
        kkmWindow.setVisible(false);
        kkmListWindow.refresh();
    }

}
