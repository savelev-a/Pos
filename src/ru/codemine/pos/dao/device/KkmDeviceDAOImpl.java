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

package ru.codemine.pos.dao.device;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import ru.codemine.pos.dao.GenericDAOImpl;
import ru.codemine.pos.entity.device.KkmDevice;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class KkmDeviceDAOImpl extends GenericDAOImpl<KkmDevice, Long> implements KkmDeviceDAO
{

    @Override
    public KkmDevice getBySerialNumber(String serial)
    {
        Query query = getSession().createQuery("FROM KkmDevice d WHERE d.serialNumber = :serial");
        query.setString("serial", serial);
        
        return (KkmDevice)query.uniqueResult();
    }

    @Override
    public KkmDevice getActive()
    {
        Query query = getSession().createQuery("FROM KkmDevice d WHERE d.enabled = true");
        
        return (KkmDevice)query.uniqueResult();
    }

    @Override
    public void setActive(KkmDevice kkmDevice)
    {
        if(kkmDevice == null || kkmDevice.getSerialNumber() == null) return;
        
        kkmDevice.setEnabled(true);
        Query querySetFalse = getSession().createQuery("UPDATE KkmDevice SET KKMDEVICE.enabled = FALSE");
        
        Query querySetTrue = getSession().createQuery("UPDATE KkmDevice SET KKMDEVICE.enabled = TRUE WHERE KKMDEVICE.serialNumber = :serial");
        querySetTrue.setString("serial", kkmDevice.getSerialNumber());
        
        querySetFalse.executeUpdate();
        querySetTrue.executeUpdate();
    }

    @Override
    public List<KkmDevice> getByType(KkmDevice.KkmType type)
    {
        Query query = getSession().createQuery("FROM KkmDevice d WHERE d.type = :type");
        query.setInteger("type", type.ordinal());
        
        return query.list();
        
    }

    @Override
    public List<KkmDevice> getAll()
    {
        Query query = getSession().createQuery("FROM KkmDevice");
        
        return query.list();
    }
    
}
