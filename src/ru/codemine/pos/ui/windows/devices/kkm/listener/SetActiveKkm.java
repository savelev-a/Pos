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
import ru.codemine.pos.application.Application;
import ru.codemine.pos.entity.device.KkmDevice;
import ru.codemine.pos.service.WorkdayService;
import ru.codemine.pos.service.kkm.KkmService;
import ru.codemine.pos.ui.windows.devices.kkm.KkmListWindow;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class SetActiveKkm implements ActionListener
{
    @Autowired private Application application;
    @Autowired private KkmListWindow window;
    @Autowired private KkmService kkmService;
    @Autowired private WorkdayService wdService;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        KkmDevice device = window.getSelectedDevice();
        
        if(device != null)
        {
            if(device.isEnabled())
            {
                WebOptionPane.showMessageDialog(window, "Эта ККМ уже активна", "Ошибка", WebOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if(wdService.getOpenWorkday() != null)
            {
                WebOptionPane.showMessageDialog(window, "Невозможно сменить ККМ при открытой кассовой смене.", "Закройте смену", WebOptionPane.WARNING_MESSAGE);
                return;
            }
            
            kkmService.setActiveKkm(device);
            application.setCurrentKkm(kkmService.getCurrentKkm());
        }
        else
        {
            WebOptionPane.showMessageDialog(window, "Не выбрана ККМ!", "Ошибка", WebOptionPane.WARNING_MESSAGE);
        }
        
        window.refresh();
    }

}
