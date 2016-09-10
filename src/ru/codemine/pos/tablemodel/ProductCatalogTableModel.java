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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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
    private Set<Integer> selectedIndexes;
    
    private static final ImageIcon ICON_IMAGE = new ImageIcon("images/icons/default/16x16/product.png");

    public ProductCatalogTableModel()
    {
        this.products = new ArrayList<>();
        this.selectedIndexes = new LinkedHashSet<>();
    }

    public ProductCatalogTableModel(List<Product> products)
    {
        this.products = products;
        this.selectedIndexes = new LinkedHashSet<>();
    }

    @Override
    public int getRowCount()
    {
        return products == null ? 0 : products.size();
    }

    @Override
    public int getColumnCount()
    {
        return 6; // icon, checkbox ,artikul, name, barcode, price
    }

    @Override
    public String getColumnName(int column)
    {
        switch(column)
        {
            case 0 : return "";
            case 1 : return "";
            case 2 : return "Артикул";
            case 3 : return "Наименование";
            case 4 : return "Штрихкод";
            case 5 : return "Цена";
        }
        
        return "";
    }

    @Override
    public Class<?> getColumnClass(int column)
    {
        switch(column)
        {
            case 0 : return ImageIcon.class;
            case 1 : return Boolean.class;
            case 2 : return String.class;
            case 3 : return String.class;
            case 4 : return String.class;
            case 5 : return Double.class;
        }
        
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {
        return column == 1;
    }
    
    @Override
    public Object getValueAt(int row, int column)
    {
        switch(column)
        {
            case 0 : return ICON_IMAGE;
            case 1 : return selectedIndexes.contains(row);
            case 2 : return products.get(row).getArtikul();
            case 3 : return products.get(row).getName();
            case 4 : return products.get(row).getBarcode();
            case 5 : return products.get(row).getPrice();
        }
        
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int row, int column)
    {
        if(column != 1) return;
        
        Boolean val = (Boolean)aValue;
        
        if(val)
            selectedIndexes.add(row);
        else
            selectedIndexes.remove(row);
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
    
    public List<Product> getSelectedProducts()
    {
        List<Product> result = new ArrayList<>();
        
        for(Integer index : selectedIndexes)
        {
            result.add(products.get(index));
        }
        
        return result;
    }
    
    
    
    
}
