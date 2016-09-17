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
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.dao.device.BarcodeScannerDeviceDAO;
import ru.codemine.pos.entity.device.BarcodeScannerDevice;

/**
 *
 * @author Alexander Savelev
 */

@Service
public class BarcodeScannerService 
{
    private static final Logger log = Logger.getLogger("BarcodeScannerService");
    
    @Autowired private Application application;
    @Autowired private BarcodeScannerDeviceDAO bsdDao;
    @Autowired private BarcodeScannerSerialPortListener bssListener;

    @Transactional
    public BarcodeScannerDevice getCurrentScanner()
    {
        return application.getCurrentScanner();
    }
    
    @Transactional
    public void setCurrentScanner(BarcodeScannerDevice bsd)
    {
        application.setCurrentScanner(bsd);
    }
    
    @Transactional
    public boolean isCurrent(BarcodeScannerDevice bsd)
    {
        return (bsd != null && bsd.equals(getCurrentScanner()));
    }
    
    @Transactional
    public List<BarcodeScannerDevice> getAllScanners()
    {
        return bsdDao.getAll();
    }
    
    @Transactional
    public void createDevice(BarcodeScannerDevice bsd)
    {
        if(bsd == null) return;
        
        bsdDao.create(bsd);
    }
    @Transactional
    public void updateDevice(BarcodeScannerDevice bsd)
    {
        if(bsd == null) return;
        
        bsdDao.update(bsd);
    }
    
    @Transactional
    public void deleteDevice(BarcodeScannerDevice bsd)
    {
        bsdDao.delete(bsd);
    }
    
    public void initCurrentDevice()
    {
        initDevice(getCurrentScanner());
        
    }
    
    public void initDevice(BarcodeScannerDevice device)
    {
        if(device == null) return;
        
        switch(device.getType())
        {
            case KEYBOARD_SCANNER :
                log.info("Настраивается эмуляция клавиатуры");
                break;
            case SERIAL_SCANNER :
                log.info("Настраивается сканер для последовательного порта " + device.getPort());
                initSerialScanner(device);
                break;
        }
    }
    
    private void initSerialScanner(BarcodeScannerDevice device)
    {
        SerialPort serialPort = SerialPort.getCommPort(device.getPort());
        serialPort.openPort();
        serialPort.addDataListener(bssListener);
    }
}
