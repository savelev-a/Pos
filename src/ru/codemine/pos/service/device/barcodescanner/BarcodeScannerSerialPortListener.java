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

package ru.codemine.pos.service.device.barcodescanner;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.ui.MainWindow;
import ru.codemine.pos.ui.salespanel.SalesPanel;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class BarcodeScannerSerialPortListener implements SerialPortDataListener
{
    @Autowired private SalesPanel salesPanel;
    @Autowired private MainWindow mainWindow;
    
    private String dataStr;
    
    public BarcodeScannerSerialPortListener()
    {
        super();
        dataStr = "";
    }

    @Override
    public int getListeningEvents()
    {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }

    @Override
    public void serialEvent(SerialPortEvent ev)
    {
        if(ev.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
            return;
        
        int bytesAvaible = ev.getSerialPort().bytesAvailable();
        byte[] buffer = new byte[bytesAvaible];
        ev.getSerialPort().readBytes(buffer, bytesAvaible);
        
        dataStr += new String(buffer);
        if(dataStr.endsWith("\n"))
        {
            int tabIndex = mainWindow.getActiveTabIndex();
            boolean inputBlocked = mainWindow.isBarcodeInputBlocked();
            
            if(tabIndex == 0 && !inputBlocked)
                salesPanel.addProductByBarcode(dataStr.replace("\n", ""));

            dataStr = "";
        }
    }

}
