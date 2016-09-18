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

package ru.codemine.pos.ui.salespanel;

import com.alee.extended.layout.TableLayout;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.entity.document.Cheque;
import ru.codemine.pos.service.ProductService;
import ru.codemine.pos.service.StoreService;
import ru.codemine.pos.tablemodel.ChequeSetupTableModel;
import ru.codemine.pos.ui.GenericPanelComponent;
import ru.codemine.pos.ui.salespanel.listener.ChequeTableKeyListener;
import ru.codemine.pos.ui.salespanel.modules.ButtonsPanel;
import ru.codemine.pos.ui.salespanel.modules.CalcsPanel;
import ru.codemine.pos.ui.salespanel.modules.ChequeSetupPanel;
import ru.codemine.pos.ui.salespanel.modules.UpperStatusPanel;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class SalesPanel extends WebPanel implements GenericPanelComponent
{
    @Autowired private ChequeSetupPanel chequeSetupPanel;
    @Autowired private ButtonsPanel buttonsPanel;
    @Autowired private ProductService productService;
    @Autowired private StoreService storeService;
    
    private final UpperStatusPanel upperStatusPanel;
    private final CalcsPanel calcsPanel;
    
    
    public SalesPanel()
    {
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.FILL, 10, TableLayout.PREFERRED, 10},
            {10, TableLayout.PREFERRED, 
             10, TableLayout.FILL, 
             10, TableLayout.PREFERRED, 
             10, TableLayout.PREFERRED, 10}
        });
        setLayout(layout);
        
        upperStatusPanel = new UpperStatusPanel();
        calcsPanel = new CalcsPanel();
        
        add(upperStatusPanel, "1, 1, 3, 1");
        add(calcsPanel, "3, 3");
        
    }
    
    @Override
    public void init()
    {
        add(buttonsPanel, "1, 5, 3, 5");
        buttonsPanel.init();
        
        add(chequeSetupPanel, "1, 3");
        chequeSetupPanel.init();
        
        setupActionListeners();
    }

    public UpperStatusPanel getUpperStatusPanel()
    {
        return upperStatusPanel;
    }

    public ChequeSetupPanel getChequeSetupPanel()
    {
        return chequeSetupPanel;
    }

    public CalcsPanel getCalcsPanel()
    {
        return calcsPanel;
    }

    @Override
    public void setupActionListeners()
    {
        chequeSetupPanel.getTable().addKeyListener(new ChequeTableKeyListener(this));
    }
    
    public void addProductByBarcode(String barcode)
    {
        Cheque setupCheque = getChequeSetupPanel().getTableModel().getCheque();
        ChequeSetupTableModel tableModel = getChequeSetupPanel().getTableModel();
        
        Product product = productService.getByBarcode(barcode);
            if(product != null)
            {
                // Товар найден, проверка остатков
                Integer quantity = setupCheque.getQuantityOf(product) + 1;

                if(storeService.checkStocks("Розница", product, quantity))
                {
                    tableModel.addItem(product, 1);
                    calcsPanel.showByCheque(setupCheque);
                    tableModel.fireTableDataChanged();
                }
                else
                {
                    WebOptionPane.showMessageDialog(getRootPane(), "Недостаточно товара на складе");
                }
            }
            else
            {
                WebOptionPane.showMessageDialog(getRootPane(), "Товар по штрихкоду " + barcode + " не найден!");
            }
    }

}
