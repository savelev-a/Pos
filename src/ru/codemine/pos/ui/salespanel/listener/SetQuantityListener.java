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

package ru.codemine.pos.ui.salespanel.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.ui.MainWindow;
import ru.codemine.pos.ui.salespanel.QuantitySetupWindow;
import ru.codemine.pos.ui.salespanel.SalesPanel;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class SetQuantityListener implements ActionListener
{
    @Autowired SalesPanel salesPanel;
    @Autowired MainWindow mainWindow;
    @Autowired QuantitySetupWindow quantitySetupWindow;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int quantity = quantitySetupWindow.getQuantity();
        int current = salesPanel.getChequeSetupPanel().getSelectedQuantity();
        Product product = salesPanel.getChequeSetupPanel().getSelectedProduct();
        
        salesPanel.getChequeSetupPanel().getTableModel().addItem(product, quantity - current);
        salesPanel.getCalcsPanel().showByCheque(salesPanel.getChequeSetupPanel().getCheque());
        
        quantitySetupWindow.setVisible(false);
        mainWindow.unblockBarcodeInput();
    }

}
