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
import com.alee.utils.swing.DocumentChangeListener;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
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
                
                int row = table.getSelectedRow();
                if(row == -1)
                {
                    WebOptionPane.showMessageDialog(rootPane, "Выберите товар для добавления", "Не выбран товар", WebOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                Product product = tableModel.getProductAt(table.getSelectedRow());
                clientWindow.addItem(product);
                setVisible(false);
            }
        });
        
        cancelButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        });
        
        searchField.getDocument().addDocumentListener(new DocumentChangeListener()
        {

            @Override
            public void documentChanged(DocumentEvent de)
            {
                String text = searchField.getText();
                if(text.length() == 0)
                {
                    sorter.setRowFilter(null);
                }
                else
                {
                    sorter.setRowFilter(RowFilter.regexFilter(text));
                }
            }
        });
        
        table.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                Point p = e.getPoint();
                int row = table.rowAtPoint(p);
                if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1)
                {
                    selectButton.doClick();
                }
            }
        });
        
        actionListenersInit = true;
    }

}
