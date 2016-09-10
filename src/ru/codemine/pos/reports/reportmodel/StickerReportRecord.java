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

import ru.codemine.pos.entity.Product;

/**
 *
 * @author Alexander Savelev
 */
public class StickerReportRecord 
{
    private String productName;
    private String artikul;
    private String priceRub;
    private String priceKop;
    private String barcode;

    public StickerReportRecord()
    {
        this.productName = "";
        this.artikul = "";
        this.priceRub = "0";
        this.priceKop = "00";
        this.barcode = "";
    }

    public StickerReportRecord(Product product)
    {
        this.productName = product.getName();
        this.artikul = product.getArtikul();
        this.priceRub = Integer.toString(product.getPrice().intValue());
        this.priceKop = Double.toString(product.getPrice() % 100);
        this.barcode = product.getBarcode();
    }

    
    
    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getArtikul()
    {
        return artikul;
    }

    public void setArtikul(String artikul)
    {
        this.artikul = artikul;
    }

    public String getPriceRub()
    {
        return priceRub;
    }

    public void setPriceRub(String priceRub)
    {
        this.priceRub = priceRub;
    }

    public String getPriceKop()
    {
        return priceKop;
    }

    public void setPriceKop(String priceKop)
    {
        this.priceKop = priceKop;
    }

    public String getBarcode()
    {
        return barcode;
    }

    public void setBarcode(String barcode)
    {
        this.barcode = barcode;
    }

    @Override
    public String toString()
    {
        return "StickerReportRecord{" + "productName=" + productName + ", artikul=" + artikul + ", priceRub=" + priceRub + ", priceKop=" + priceKop + ", barcode=" + barcode + '}';
    }
    
    
}
