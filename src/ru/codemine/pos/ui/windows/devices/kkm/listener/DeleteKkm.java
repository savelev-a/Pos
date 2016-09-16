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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.device.KkmDevice;
import ru.codemine.pos.service.kkm.KkmService;
import ru.codemine.pos.ui.windows.devices.kkm.KkmListWindow;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class DeleteKkm implements ActionListener
{
    @Autowired private KkmListWindow window;
    @Autowired private KkmService kkmService;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        KkmDevice device = window.getSelectedDevice();
        
        if(device != null)
        {
            if(kkmService.isCurrent(device))
            {
                WebOptionPane.showMessageDialog(window, "Невозможно удалить активную ККМ!", "Ошибка", WebOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try
            {
                kkmService.deleteDevice(device);
            } 
            catch (DataIntegrityViolationException ex)
            {
                WebOptionPane.showMessageDialog(window, "Невозможно удалить данную ККМ!", "Ошибка", WebOptionPane.WARNING_MESSAGE);
            }
        }
        else
        {
            WebOptionPane.showMessageDialog(window, "Не выбрана ККМ!", "Ошибка", WebOptionPane.WARNING_MESSAGE);
        }
        
        window.refresh();
    }

}
