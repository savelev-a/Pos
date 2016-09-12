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
@Table(name = "barcodeSc")
public class BarcodeScannerDevice extends GenericDevice
{
    public enum BarcodeScannerType
    {
        KEYBOARD_SCANNER,
        SERIAL_SCANNER
    }
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "type", nullable = false)
    private BarcodeScannerType type;
    
    @Column(name = "port", nullable = true)
    private String port;
    
    @Column(name = "speed", nullable = true)
    private Integer speed;
    
    @Column(name = "description", nullable = false, columnDefinition="TEXT")
    private String description;
    
    public BarcodeScannerDevice()
    {
        this.type = BarcodeScannerType.KEYBOARD_SCANNER;
        this.name = "USB сканер (эмуляция клавиатуры)";
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public BarcodeScannerType getType()
    {
        return type;
    }

    public void setType(BarcodeScannerType type)
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
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.type);
        hash = 89 * hash + Objects.hashCode(this.port);
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
        final BarcodeScannerDevice other = (BarcodeScannerDevice) obj;
        if (this.type != other.type)
        {
            return false;
        }
        if (!Objects.equals(this.port, other.port))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "barcodeScannerDevice{" 
                + "name=" + name 
                + ", type=" + type 
                + ", port=" + port 
                + ", speed=" + speed 
                + ", description=" + description + '}';
    }
    
    

    @Override
    public DeviceType getDeviceType()
    {
        return DeviceType.DEVICE_BARCODE_SCANNER;
    }
    
    public static Map<BarcodeScannerType, String> getAvaibleTypes()
    {
        Map<BarcodeScannerType, String> result = new LinkedHashMap<>();
        
        result.put(BarcodeScannerType.KEYBOARD_SCANNER, "USB сканер (эмуляция клавиатуры)");
        result.put(BarcodeScannerType.SERIAL_SCANNER, "Сканер для последовательного порта");
        
        return result;
    }

}
