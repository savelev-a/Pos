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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.service.ProductService;
import ru.codemine.pos.ui.windows.document.products.ProductsListWindow;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class DeleteProduct implements ActionListener
{
    @Autowired private ProductsListWindow window;
    @Autowired private ProductService productService;
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Product product = window.getSelectedProduct();
        if(product != null)
        {
            try
            {
                productService.delete(product);
            } 
            catch (DataIntegrityViolationException ex)
            {
                WebOptionPane.showMessageDialog(window, "Невозможно удалить данный товар - по нему имеются остатки и/или движения!", "Ошибка", WebOptionPane.WARNING_MESSAGE);
            }
            
        }
        else
        {
            WebOptionPane.showMessageDialog(window, "Не выбран склад!", "Ошибка", WebOptionPane.WARNING_MESSAGE);
        }
        
        window.refresh();
    }

}
