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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.device.KkmDevice;
import ru.codemine.pos.ui.windows.devices.kkm.KkmWindow;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class ChangeKkmType implements ActionListener
{
    @Autowired private KkmWindow window;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String typeStr = window.getKkmTypeStr();
        
        KkmDevice.KkmType type = KkmDevice.KkmType.SYSLOG_PRINTER;
        for(Map.Entry<KkmDevice.KkmType, String> entry : KkmDevice.getAvaibleTypes().entrySet())
        {
            if(entry.getValue().equals(typeStr)) type = entry.getKey();
        }
        
        switch(type)
        {
            case SYSLOG_PRINTER : 
                window.setKkmDescription("Это виртуальный кассовый аппарат, используемый для отладки программы.\n"
                                       + "Не требует настроек и печатает содержимое чека в системную консоль.");
                window.setPortVisible(false);
                window.setSysnameVisible(false);
                break;
            case  CHEQUE_PRINTER : 
                window.setKkmDescription("Чековый принтер - печать чеков через системный принтер.\n"
                                       + "Для настройки необходимо указать имя принтера.");
                window.setPortVisible(false);
                window.setSysnameVisible(true);
                break;
        }
    }

}
