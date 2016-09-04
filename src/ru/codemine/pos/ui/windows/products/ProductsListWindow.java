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

package ru.codemine.pos.ui.windows.products;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.table.TableColumnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.service.ProductService;
import ru.codemine.pos.tablemodel.ProductCatalogTableModel;
import ru.codemine.pos.ui.windows.GenericEntityListWindow;
import ru.codemine.pos.ui.windows.products.listener.DeleteProduct;
import ru.codemine.pos.ui.windows.products.listener.EditProduct;
import ru.codemine.pos.ui.windows.products.listener.NewProduct;
import ru.codemine.pos.ui.windows.products.listener.RefreshProductList;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class ProductsListWindow extends GenericEntityListWindow
{
    @Autowired private ProductService productService;
    
    @Autowired NewProduct newProduct;
    @Autowired EditProduct editProduct;
    @Autowired DeleteProduct deleteProduct;
    @Autowired RefreshProductList refreshProductList;
    
    ProductCatalogTableModel tableModel;
    
    public ProductsListWindow()
    {
        super();
        setTitle("Справочник товаров");
        setSize(800, 400);
        
        menuItemProcess.setEnabled(false);
        menuItemUnprocess.setEnabled(false);
        toolButtonProcess.setEnabled(false);
        toolButtonUnprocess.setEnabled(false);
    }
    
    @Override
    public void showWindow()
    {
        List<Product> products = productService.getAll();
        
        tableModel = new ProductCatalogTableModel(products);
        table.setModel(tableModel);
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(10);
        
        if(!actionListenersInit) setupActionListeners();
        
        statusLabel.setText("Загружено " + products.size() + " строк");
        
        setupSorter();
        setVisible(true);
    }
    
    @Override
    public void setupActionListeners()
    {
        setNewActionListener(newProduct);
        setEditActionListener(editProduct);
        setDeleteActionListener(deleteProduct);
        setRefreshActionListener(refreshProductList);
        
        table.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                Point p = e.getPoint();
                int row = table.rowAtPoint(p);
                if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1)
                {
                    menuItemEdit.doClick();
                }
            }
        });
        
        actionListenersInit = true;
    }
    
    public Product getSelectedProduct()
    {
        if(table.getSelectedRow() == -1) return null;
        
        return tableModel.getProductAt(table.getSelectedRow());
    }
    
    
    public ProductCatalogTableModel getTableModel()
    {
        return tableModel;
    }
    
}
