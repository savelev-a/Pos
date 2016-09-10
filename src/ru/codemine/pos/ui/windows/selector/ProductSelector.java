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

package ru.codemine.pos.ui.windows.selector;

import com.alee.laf.optionpane.WebOptionPane;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.table.TableColumnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.service.ProductService;
import ru.codemine.pos.tablemodel.ProductCatalogTableModel;
import ru.codemine.pos.ui.windows.document.GenericDocumentWindow;

/**
 *
 * @author Alexander Savelev
 */
@Component
public class ProductSelector extends GenericSelector
{
    @Autowired ProductService productService;
    
    ProductCatalogTableModel tableModel;
    
    public ProductSelector()
    {
        super();
        actionListenersInit = false;
    }

    @Override
    public void selectFor(GenericDocumentWindow window)
    {
        this.clientWindow = window;
        if(!actionListenersInit) setupActionListeners();
        
        List<Product> products = productService.getAll();
        tableModel = new ProductCatalogTableModel(products);
        table.setModel(tableModel);
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(10);
        columnModel.getColumn(1).setMaxWidth(10);
        
        setTitle("Выберите товар для добавления");
        statusLabel.setText("Загружено " + products.size() + " строк");
        
        setupSorter();
        setVisible(true);
    }

    @Override
    public void setupActionListeners()
    {
        selectButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                sorter.setRowFilter(null); //иначе будет выделена не та строка
                
                List<Product> products = tableModel.getSelectedProducts();
                if(products.isEmpty())
                {
                    WebOptionPane.showMessageDialog(rootPane, "Не выбрано товаров для добавления.\nДля выбора воспользуйтесь флажками слева от артикула", "Не выбран ни один товар", WebOptionPane.WARNING_MESSAGE);
                    return;
                }
                clientWindow.addItems(products);
                
                setVisible(false);
            }
        });
        
        finishSetupActionListeners();
    }

}
