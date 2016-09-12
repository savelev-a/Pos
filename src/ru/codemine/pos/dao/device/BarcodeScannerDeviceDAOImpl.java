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
import ru.codemine.pos.entity.device.BarcodeScannerDevice;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class BarcodeScannerDeviceDAOImpl extends GenericDAOImpl<BarcodeScannerDevice, Long> implements BarcodeScannerDeviceDAO
{

    @Override
    public BarcodeScannerDevice getActive()
    {
        Query query = getSession().createQuery("FROM BarcodeScannerDevice d WHERE d.enabled = true");
        
        return (BarcodeScannerDevice)query.uniqueResult();
    }

    @Override
    public void setActive(BarcodeScannerDevice device)
    {
        if(device == null || device.getId() == null) return;
        
        device.setEnabled(true);
        
        Query querySetFalse = getSession().createQuery("UPDATE BarcodeScannerDevice SET enabled = FALSE");
        Query querySetTrue = getSession().createQuery("UPDATE BarcodeScannerDevice SET enabled = TRUE WHERE id = :id");
        querySetTrue.setLong("id", device.getId());
        
        querySetFalse.executeUpdate();
        querySetTrue.executeUpdate();
        
    }

    @Override
    public List<BarcodeScannerDevice> getByType(BarcodeScannerDevice.BarcodeScannerType type)
    {
        Query query = getSession().createQuery("FROM BarcodeScannerDevice d WHERE d.type = :type");
        query.setInteger("type", type.ordinal());
        
        return query.list();
    }

    @Override
    public List<BarcodeScannerDevice> getAll()
    {
        Query query = getSession().createQuery("FROM BarcodeScannerDevice");
        
        return query.list();
    }
    

}
