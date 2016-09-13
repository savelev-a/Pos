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

package ru.codemine.pos.service.kkm.template;

/**
 *
 * @author Alexander Savelev
 */
public class JasperChequeRecord 
{
    private String positionName;
    private Integer quantity;
    private String summ;

    public JasperChequeRecord()
    {
        this.positionName = "";
        this.quantity = 0;
        this.summ = "0.0";
    }

    public JasperChequeRecord(String positionName, Integer quantity, String summ)
    {
        this.positionName = positionName;
        this.quantity = quantity;
        this.summ = summ;
    }
    
    

    public String getPositionName()
    {
        return positionName;
    }

    public void setPositionName(String positionName)
    {
        this.positionName = positionName;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public String getSumm()
    {
        return summ;
    }

    public void setSumm(String summ)
    {
        this.summ = summ;
    }

    @Override
    public String toString()
    {
        return "JasperChequeRecord{" + "positionName=" + positionName + ", quantity=" + quantity + ", summ=" + summ + '}';
    }
    
    
}
