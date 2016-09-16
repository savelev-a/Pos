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

package ru.codemine.pos.ui.windows.devices.barcodescanner.listener;

import com.alee.laf.optionpane.WebOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.entity.device.BarcodeScannerDevice;
import ru.codemine.pos.service.device.barcodescanner.BarcodeScannerService;
import ru.codemine.pos.ui.windows.devices.barcodescanner.BarcodeScannerListWindow;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class SetActiveBarcodeScannerDevice implements ActionListener
{
    @Autowired private Application application;
    @Autowired private BarcodeScannerListWindow window;
    @Autowired private BarcodeScannerService barcodeScannerService;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        BarcodeScannerDevice device = window.getSelectedDevice();
        
        if(device != null)
        {
            if(barcodeScannerService.isCurrent(device))
            {
                WebOptionPane.showMessageDialog(window, "Это устройство уже активно", "Ошибка", WebOptionPane.WARNING_MESSAGE);
                return;
            }

            barcodeScannerService.setCurrentScanner(device);
        }
        else
        {
            WebOptionPane.showMessageDialog(window, "Не выбрано устройство!", "Ошибка", WebOptionPane.WARNING_MESSAGE);
        }
        
        window.refresh();
    }

}
