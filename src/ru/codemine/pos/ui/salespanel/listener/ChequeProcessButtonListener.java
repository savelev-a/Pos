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

import com.alee.laf.optionpane.WebOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.document.Cheque;
import ru.codemine.pos.exception.DocumentAlreadyActiveException;
import ru.codemine.pos.exception.NotEnoughGoodsException;
import ru.codemine.pos.exception.WorkdayNotOpenedException;
import ru.codemine.pos.service.ChequeService;
import ru.codemine.pos.ui.MainWindow;
import ru.codemine.pos.ui.salespanel.SalesPanel;
import ru.codemine.pos.ui.salespanel.modules.ButtonsPanel;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class ChequeProcessButtonListener implements ActionListener
{
    @Autowired private ChequeService chequeService;
    @Autowired private MainWindow mainWindow;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        ButtonsPanel buttonsPanel = mainWindow.getSalesPanel().getButtonsPanel();
        SalesPanel salesPanel = mainWindow.getSalesPanel();
        try
        {
            buttonsPanel.getChequeProcessButton().setEnabled(false);
            Cheque cheque = salesPanel.getChequeSetupPanel().getCheque();
            if (!cheque.getContents().isEmpty())
            {
                chequeService.CheckoutWithoutKKM(cheque);
                salesPanel.getChequeSetupPanel().newCheque();
                salesPanel.getCalcsPanel().showByCheque(salesPanel.getChequeSetupPanel().getCheque());
                salesPanel.requestFocus();
            }
            

        } 
        catch (WorkdayNotOpenedException | NotEnoughGoodsException | DocumentAlreadyActiveException ex)
        {
            WebOptionPane.showMessageDialog(mainWindow.getRootPane(), ex.getLocalizedMessage(), "Ошибка", WebOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            buttonsPanel.getChequeProcessButton().setEnabled(true);
            mainWindow.getSalesPanel().requestFocus();
        }
    }

}
