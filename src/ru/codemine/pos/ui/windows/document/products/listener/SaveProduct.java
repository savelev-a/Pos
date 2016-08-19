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

package ru.codemine.pos.ui.windows.document.products.listener;

import com.alee.laf.optionpane.WebOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.exception.DuplicateProductDataException;
import ru.codemine.pos.service.ProductService;
import ru.codemine.pos.ui.windows.document.products.ProductWindow;
import ru.codemine.pos.ui.windows.document.products.ProductsListWindow;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class SaveProduct implements ActionListener 
{
    @Autowired private ProductWindow productWindow;
    @Autowired private ProductsListWindow productListWindow;
    @Autowired private ProductService productService;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Product product = productWindow.getProduct();
        if(product == null) return;

        if(product.getId() == null)
        {
            try
            {
                productService.create(product);
            } 
            catch (DuplicateProductDataException ex)
            {
                WebOptionPane.showMessageDialog(productWindow, ex.getLocalizedMessage(), "Ошибка сохранения товара", WebOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else
        {
            try
            {
                productService.update(product);
            } 
            catch (DuplicateProductDataException ex)
            {
                WebOptionPane.showMessageDialog(productWindow, ex.getLocalizedMessage(), "Ошибка сохранения товара", WebOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        productWindow.setVisible(false);
        productListWindow.refresh();
    }
    
}
