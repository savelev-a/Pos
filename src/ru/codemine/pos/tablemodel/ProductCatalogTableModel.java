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

package ru.codemine.pos.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import ru.codemine.pos.entity.Product;

/**
 *
 * @author Alexander Savelev
 */
public class ProductCatalogTableModel extends DefaultTableModel
{
    private List<Product> products;
    
    private static final ImageIcon ICON_IMAGE = new ImageIcon("images/icons/default/16x16/product.png");

    public ProductCatalogTableModel()
    {
        this.products = new ArrayList<>();
    }

    public ProductCatalogTableModel(List<Product> products)
    {
        this.products = products;
    }

    @Override
    public int getRowCount()
    {
        return products == null ? 0 : products.size();
    }

    @Override
    public int getColumnCount()
    {
        return 5; // icon, artikul, name, barcode, price
    }

    @Override
    public String getColumnName(int column)
    {
        switch(column)
        {
            case 0 : return "";
            case 1 : return "Артикул";
            case 2 : return "Наименование";
            case 3 : return "Штрихкод";
            case 4 : return "Цена";
        }
        
        return "";
    }

    @Override
    public Class<?> getColumnClass(int column)
    {
        switch(column)
        {
            case 0 : return ImageIcon.class;
            case 1 : return String.class;
            case 2 : return String.class;
            case 3 : return String.class;
            case 4 : return Double.class;
        }
        
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }
    
    @Override
    public Object getValueAt(int row, int column)
    {
        switch(column)
        {
            case 0 : return ICON_IMAGE;
            case 1 : return products.get(row).getArtikul();
            case 2 : return products.get(row).getName();
            case 3 : return products.get(row).getBarcode();
            case 4 : return products.get(row).getPrice();
        }
        
        return "";
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public void setProducts(List<Product> products)
    {
        this.products = products;
    }

    public Product getProductAt(int index)
    {
        return products.get(index);
    }
    
    
    
    
}
