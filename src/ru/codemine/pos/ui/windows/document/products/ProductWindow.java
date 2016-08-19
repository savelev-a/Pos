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

package ru.codemine.pos.ui.windows.document.products;

import com.alee.extended.layout.TableLayout;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.TableColumnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.entity.Store;
import ru.codemine.pos.service.ProductService;
import ru.codemine.pos.service.StoreService;
import ru.codemine.pos.tablemodel.ProductByStoresTableModel;
import ru.codemine.pos.ui.windows.document.GenericDocumentWindow;
import ru.codemine.pos.ui.windows.document.products.listener.DontSaveProduct;
import ru.codemine.pos.ui.windows.document.products.listener.SaveProduct;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class ProductWindow extends GenericDocumentWindow
{
    @Autowired private StoreService storeService;
    
    @Autowired private SaveProduct saveProduct;
    @Autowired private DontSaveProduct dontSaveProduct;
    
    private final WebLabel idLabel;
    private final WebLabel artikulLabel;
    private final WebLabel nameLabel;
    private final WebLabel barcodeLabel;
    private final WebLabel priceLabel;
    
    private final WebLabel idField;
    private final WebTextField artikulField;
    private final WebTextField nameField;
    private final WebTextField barcodeField;
    private final WebTextField priceField;
    
    private final WebLabel stocksLabel;
    private final WebTable stocksTable;
    
    private Product product;
    
    public ProductWindow()
    {
        super();
        setTitle("Товар");
        
        idLabel      = new WebLabel("Код товара");
        artikulLabel = new WebLabel("Артикул");
        nameLabel    = new WebLabel("Наименование");
        barcodeLabel = new WebLabel("Штрихкод");
        priceLabel   = new WebLabel("Цена");
        
        idField = new WebLabel();
        artikulField = new WebTextField();
        nameField = new WebTextField();
        barcodeField = new WebTextField();
        priceField = new WebTextField();
        
        stocksLabel = new WebLabel("Остатки по складам");
        stocksTable = new WebTable();
        
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10},
            {10, TableLayout.PREFERRED,                 // 1  - Id
             10, TableLayout.PREFERRED,                 // 3  - Артикул
             10, TableLayout.PREFERRED,                 // 5  - Наименование
             10, TableLayout.PREFERRED,                 // 7  - Штрихкод
             10, TableLayout.PREFERRED,                 // 9  - Цена
             10, TableLayout.FILL,                      // 11 - Таблица
             10, TableLayout.PREFERRED,                 // 13 - Кнопки
             10}             
        });
        setLayout(layout);
        
        add(idLabel,      "1, 1");
        add(idField,      "3, 1");
        add(artikulLabel, "1, 3");
        add(artikulField, "3, 3");
        add(nameLabel,    "1, 5");
        add(nameField,    "3, 5");
        add(barcodeLabel, "1, 7");
        add(barcodeField, "3, 7");
        add(priceLabel,   "1, 9");
        add(priceField,   "3, 9");
        add(stocksLabel, "1, 11, L, T");
        add(new WebScrollPane(stocksTable), "3, 11");
        add(new WebButtonGroup(saveButton, cancelButton), "1, 13, 3, 13, C, T");
    }
    
    public void showWindow(Product product)
    {
        if(!actionListenersInit) setupActionListeners();
        
        this.product = product;
        
        setTitle("Товар - " + product.getName());
        
        idField.setText(product.getId() == null ? "" : product.getId().toString());
        artikulField.setText(product.getArtikul());
        nameField.setText(product.getName());
        barcodeField.setText(product.getBarcode());
        priceField.setText(product.getPrice() == null ? "0" : product.getPrice().toString());
        
        if(product.getId() == null)
        {
            artikulField.setEditable(true);
        }
        else
        {
            artikulField.setEditable(false);
        }
        
        List<Store> proxedStores = storeService.getAll();
        List<Store> stores = new ArrayList<>();
        for(Store s : proxedStores)
        {
            stores.add(storeService.unproxyStocks(s));
        }
        stocksTable.setModel(new ProductByStoresTableModel(product, stores));
        
        TableColumnModel columnModel = stocksTable.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(10);
        
        setVisible(true);
    }
    
    private void setupActionListeners()
    {
        saveButton.addActionListener(saveProduct);
        cancelButton.addActionListener(dontSaveProduct);
        actionListenersInit = true;
    }

    public Product getProduct()
    {
        product.setArtikul(artikulField.getText());
        product.setBarcode(barcodeField.getText());
        product.setName(nameField.getText());
        try
        {
            product.setPrice(Double.parseDouble(priceField.getText()));
        } 
        catch (NumberFormatException ex)
        {
            WebOptionPane.showMessageDialog(rootPane, "Цена должна быть числом, копейки отделены точкой", "Неверный формат цены", WebOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        return product;
    }
    
}
