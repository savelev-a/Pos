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

package ru.codemine.pos.entity.device;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "kkm")
public class KkmDevice extends GenericDevice
{
    public enum KkmType
    {
        SYSLOG_PRINTER,
        CHEQUE_PRINTER
    }
    
    @Column(name = "serialNumber", nullable = false, unique = true)
    private String serialNumber;
    
    @Column(name = "kkmNumber", nullable = false)
    private String kkmNumber;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "type", nullable = false)
    private KkmType type;
    
    @Column(name = "port", nullable = true)
    private String port;
    
    @Column(name = "speed", nullable = true)
    private Integer speed;
    
    @Column(name = "sysprinter", nullable = true)
    private String sysprinter;
    
    @Column(name = "description", nullable = false, columnDefinition="TEXT")
    private String description;

    public KkmDevice()
    {
        this.type = KkmType.SYSLOG_PRINTER;
        this.name = "Печать в syslog (только для отладки)";
        this.speed = 0;
    }
    
    public String getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public String getKkmNumber()
    {
        return kkmNumber;
    }

    public void setKkmNumber(String kkmNumber)
    {
        this.kkmNumber = kkmNumber;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public KkmType getType()
    {
        return type;
    }

    public void setType(KkmType type)
    {
        this.type = type;
        this.name = getAvaibleTypes().get(type);
    }

    public String getPort()
    {
        return port;
    }

    public void setPort(String port)
    {
        this.port = port;
    }

    public Integer getSpeed()
    {
        return speed;
    }

    public void setSpeed(Integer speed)
    {
        this.speed = speed;
    }

    public String getSysprinter()
    {
        return sysprinter;
    }

    public void setSysprinter(String sysprinter)
    {
        this.sysprinter = sysprinter;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.serialNumber);
        hash = 89 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final KkmDevice other = (KkmDevice) obj;
        if (!Objects.equals(this.serialNumber, other.serialNumber))
        {
            return false;
        }
        if (this.type != other.type)
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "KkmDevice{" 
                + "serialNumber=" + serialNumber 
                + ", kkmNumber=" + kkmNumber 
                + ", name=" + name 
                + ", type=" + type 
                + ", port=" + port 
                + ", speed=" + speed 
                + ", sysname=" + sysprinter + '}';
    }

    @Override
    public DeviceType getDeviceType()
    {
        return DeviceType.DEVICE_KKM;
    }
    
    public static Map<KkmType, String> getAvaibleTypes()
    {
        Map<KkmType, String> result = new LinkedHashMap<>();
        result.put(KkmType.SYSLOG_PRINTER ,"Печать в syslog (только для отладки)");
        result.put(KkmType.CHEQUE_PRINTER ,"Чековый принтер");
        
        return result;
    }

}
