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

package ru.codemine.pos.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.codemine.pos.entity.device.BarcodeScannerDevice;
import ru.codemine.pos.entity.device.KkmDevice;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "settings")
public class Settings implements Serializable
{
    @Id
    private Integer id;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id", nullable = true)
    private Shop currentShop;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kkm_id", nullable = true)
    private KkmDevice currentKkmDevice;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "scanner_id", nullable = true)
    private BarcodeScannerDevice currentScannerDevice;

    public Settings()
    {
        this.id = 1;
    }

    public Integer getId()
    {
        return 1;
    }

    public void setId(Integer id)
    {
        this.id = 1;
    }

    public KkmDevice getCurrentKkmDevice()
    {
        return currentKkmDevice;
    }

    public void setCurrentKkmDevice(KkmDevice currentKkmDevice)
    {
        this.currentKkmDevice = currentKkmDevice;
    }

    public BarcodeScannerDevice getCurrentScannerDevice()
    {
        return currentScannerDevice;
    }

    public void setCurrentScannerDevice(BarcodeScannerDevice currentScannerDevice)
    {
        this.currentScannerDevice = currentScannerDevice;
    }

    public Shop getCurrentShop()
    {
        return currentShop;
    }

    public void setCurrentShop(Shop currentShop)
    {
        this.currentShop = currentShop;
    }

    @Override
    public String toString()
    {
        return "Settings{" + "id=" + id + ", currentShop=" + currentShop + ", currentKkmDevice=" + currentKkmDevice + ", currentScannerDevice=" + currentScannerDevice + '}';
    }
    
    

}
