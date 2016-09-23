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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.device.BarcodeScannerDevice;
import ru.codemine.pos.ui.windows.devices.barcodescanner.BarcodeScannerWindow;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class ChangeScannerType implements ActionListener
{
    @Autowired private BarcodeScannerWindow window;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String typeStr = window.getScannerTypeStr();
        
        BarcodeScannerDevice.BarcodeScannerType type = BarcodeScannerDevice.BarcodeScannerType.KEYBOARD_SCANNER;
        for(Map.Entry<BarcodeScannerDevice.BarcodeScannerType, String> entry : BarcodeScannerDevice.getAvaibleTypes().entrySet())
        {
            if(entry.getValue().equals(typeStr)) type = entry.getKey();
        }
        
        switch(type)
        {
            case KEYBOARD_SCANNER : 
                window.setScannerTypeDescription("Этот сканер штрихкодов работает как виртуальная клавиатура.\n "
                        + "Так работают сканеры, подключенные в разрыв порта клавиатуры, либо в порт usb (в соответствующем режиме).\n"
                        + "Данный тип сканеров не требует особой настройки");
                window.setPortVisible(false);
                break;
            case SERIAL_SCANNER : 
                window.setScannerTypeDescription("Этот сканер штрихкодов подключен через последовательный порт (COM).\n"
                        + "Также поддерживаются USB-сканеры в режиме виртуального com-порта.\n"
                        + "Данный тип сканеров требует выбора порта и скорости.\n\n"
                        + "Примечание: При изменении настроек активного сканера необходим перезапуск приложения.");
                window.setPortVisible(true);
                break;
        }
    }

}
