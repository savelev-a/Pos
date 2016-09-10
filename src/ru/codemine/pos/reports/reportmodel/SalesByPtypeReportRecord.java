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

package ru.codemine.pos.reports.reportmodel;

/**
 *
 * @author Alexander Savelev
 */
public class SalesByPtypeReportRecord 
{
    private String ptypeName;
    private Integer cqCount;
    private Double sales;
    
    public SalesByPtypeReportRecord()
    {
        ptypeName = "";
        cqCount = 0;
        sales = 0.0;
    }

    public SalesByPtypeReportRecord(String ptypeName, Integer cqCount, Double sales)
    {
        this.ptypeName = ptypeName;
        this.cqCount = cqCount;
        this.sales = sales;
    }
    
    

    public String getPtypeName()
    {
        return ptypeName;
    }

    public void setPtypeName(String ptypeName)
    {
        this.ptypeName = ptypeName;
    }

    public Integer getCqCount()
    {
        return cqCount;
    }

    public void setCqCount(Integer cqCount)
    {
        this.cqCount = cqCount;
    }

    public Double getSales()
    {
        return sales;
    }

    public void setSales(Double sales)
    {
        this.sales = sales;
    }

    @Override
    public String toString()
    {
        return "SalesByPtypeReportRecord{" + "ptypeName=" + ptypeName + ", cqCount=" + cqCount + ", sales=" + sales + '}';
    }
    
    
}
