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

package ru.codemine.pos.service.kkm;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.dao.device.KkmDeviceDAO;
import ru.codemine.pos.entity.Workday;
import ru.codemine.pos.entity.device.GenericDevice;
import ru.codemine.pos.entity.device.KkmDevice;
import ru.codemine.pos.entity.document.Cheque;
import ru.codemine.pos.exception.DuplicateDeviceDataException;
import ru.codemine.pos.exception.GeneralException;
import ru.codemine.pos.exception.KkmException;
import ru.codemine.pos.service.ChequeService;
import ru.codemine.pos.service.WorkdayService;

/**
 *
 * @author Alexander Savelev
 */

@Service
public class KkmService 
{
    @Autowired private Application application;
    @Autowired private WorkdayService workdayService;
    @Autowired private ChequeService chequeService;
    @Autowired private KkmDeviceDAO kkmDeviceDAO;
    
    @Transactional
    public Kkm getCurrentKkm()
    {
        return application.getCurrentKkm();
    }
    
    @Transactional
    public void setCurrentKkm(KkmDevice device)
    {
        application.setCurrentKkm(createKkm(device));
    }
    
    @Transactional
    public boolean isCurrent(Kkm kkm)
    {
        if(kkm == null || kkm.getDevice() == null) return false;
        if(getCurrentKkm() == null) return false;
        
        return (kkm.getDevice().equals(getCurrentKkm().getDevice()));
    }
    
    @Transactional
    public boolean isCurrent(KkmDevice device)
    {
        if(device == null) return false;
        if(getCurrentKkm() == null) return false;
        
        return (device.equals(getCurrentKkm().getDevice()));
    }
    
    public Kkm createKkm(KkmDevice device)
    {
        Kkm kkm;
        if(device == null) return null;
        
        switch(device.getType())
        {
            case SYSLOG_PRINTER : 
                kkm = new SyslogChequePrinter();
                break;
            case CHEQUE_PRINTER : 
                kkm = new ChequePrinter();
                break;
            default :
                kkm = new SyslogChequePrinter();
        }
        
        kkm.setDevice(device);
        
        return kkm;
    }
    
    public void printXReport() throws KkmException 
    {
        printXReport(application.getCurrentKkm());
    }
    
    public void printXReport(Kkm kkm) throws KkmException
    {
        if(kkm == null) return;
        
        Workday currentWorkday = workdayService.getOpenWorkday();
        List<Cheque> cheques = chequeService.getByOpenWorkday();
        
        kkm.printXReport(currentWorkday, cheques);
    }
    
    @Transactional
    public void printZReport() throws KkmException, GeneralException
    {
        Kkm kkm = application.getCurrentKkm();
        Workday currentWorkday = workdayService.getOpenWorkday();
        List<Cheque> cheques = chequeService.getByOpenWorkday();
        
        if(kkm == null || currentWorkday == null) return;
        
        kkm.printZReport(currentWorkday, cheques);
        
        workdayService.closeWorkday();
        
    }
    
    @Transactional
    public List<KkmDevice> getAllKkmDevices()
    {
        return kkmDeviceDAO.getAll();
    }

    @Transactional
    public void createDevice(KkmDevice device) throws DuplicateDeviceDataException
    {
        if(device == null) return;
        
        if(kkmDeviceDAO.getBySerialNumber(device.getSerialNumber()) != null) 
            throw new DuplicateDeviceDataException(GenericDevice.DeviceType.DEVICE_KKM, 
                    "Серийный номер",
                    device.getSerialNumber());
        
        kkmDeviceDAO.create(device);
    }

    @Transactional
    public void updateDevice(KkmDevice device) throws DuplicateDeviceDataException
    {
        if(device == null) return;
        
        KkmDevice testSerialNumber = kkmDeviceDAO.getBySerialNumber(device.getSerialNumber());
        
        if(testSerialNumber != null && !testSerialNumber.getId().equals(device.getId())) 
            throw new DuplicateDeviceDataException(GenericDevice.DeviceType.DEVICE_KKM, 
                                                   "Серийный номер", 
                                                   device.getSerialNumber());
        
        kkmDeviceDAO.evict(testSerialNumber);
        kkmDeviceDAO.update(device);
        
    }

    @Transactional
    public void deleteDevice(KkmDevice device)
    {
        kkmDeviceDAO.delete(device);
    }
}
