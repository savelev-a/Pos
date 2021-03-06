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

package ru.codemine.pos.ui.salespanel.modules;

import com.alee.extended.layout.TableLayout;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import javax.swing.ImageIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.ui.GenericPanelComponent;
import ru.codemine.pos.ui.salespanel.listener.ChequeProcessButtonListener;
import ru.codemine.pos.ui.salespanel.listener.CloseWorkdayButtonListener;
import ru.codemine.pos.ui.salespanel.listener.OpenWorkdayButtonListener;
import ru.codemine.pos.ui.salespanel.listener.PaymentTypeButtonListener;
import ru.codemine.pos.ui.salespanel.listener.ShowQuantitySetupWindowListener;
import ru.codemine.pos.ui.salespanel.listener.XReportButtonListener;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class ButtonsPanel extends WebPanel implements GenericPanelComponent
{
    @Autowired private OpenWorkdayButtonListener openWorkdayButtonListener;
    @Autowired private CloseWorkdayButtonListener closeWorkdayButtonListener;
    @Autowired private ShowQuantitySetupWindowListener quantitySetupListener;
    @Autowired private ChequeProcessButtonListener chequeProcessButtonListener;
    @Autowired private XReportButtonListener xReportButtonListener;
    @Autowired private PaymentTypeButtonListener paymentTypeButtonListener;
    
    private final WebButton[][] buttons;
    public ButtonsPanel()
    {
        setMargin(10);
        setUndecorated(false);
        setRound(StyleConstants.largeRound);
        
        TableLayout layout = new TableLayout(new double[][]{
            {TableLayout.FILL, 
             10, TableLayout.FILL,
             10, TableLayout.FILL,
             10, TableLayout.FILL},
            {TableLayout.PREFERRED, 10, TableLayout.PREFERRED}
        });
        setLayout(layout);
        
        buttons = new WebButton[4][2];
        
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 2; j++)
            {
                buttons[i][j] = new WebButton("button");
                buttons[i][j].setMinimumHeight(50);
                buttons[i][j].setMinimumWidth(150);
                buttons[i][j].setRound(StyleConstants.largeRound);
                add(buttons[i][j], "" + (i * 2) + ", " + (j * 2));
            }
        }
        
        buttons[0][0].setText("Открыть смену");
        buttons[0][0].setIcon(new ImageIcon("images/icons/default/32x32/open-workday.png"));
        
        buttons[0][1].setText("Закрыть смену");
        buttons[0][1].setIcon(new ImageIcon("images/icons/default/32x32/close-workday.png"));
        
        buttons[1][0].setText("Количество (F4)");
        buttons[1][0].setIcon(new ImageIcon("images/icons/default/32x32/set-quantity.png"));
        
        buttons[1][1].setText("Отчет без гашения");
        buttons[1][1].setIcon(new ImageIcon("images/icons/default/32x32/x-report.png"));
        
        buttons[2][0].setText("Пробить чек (F5)");
        buttons[2][0].setIcon(new ImageIcon("images/icons/default/32x32/cheque-process.png"));
        
        buttons[2][1].setText("Выбрать тип оплаты (F3)");
        buttons[2][1].setIcon(new ImageIcon("images/icons/default/32x32/payment-type.png"));
        
    }
    
    @Override
    public void setupActionListeners()
    {
        getOpenWorkdayButton().addActionListener(openWorkdayButtonListener);
        getCloseWorkdayButton().addActionListener(closeWorkdayButtonListener);
        getQuantitySetupButton().addActionListener(quantitySetupListener);
        getChequeProcessButton().addActionListener(chequeProcessButtonListener);
        getXReportButton().addActionListener(xReportButtonListener);
        getPaymentChooseButton().addActionListener(paymentTypeButtonListener);
    }
    
    public WebButton[][] getButtons()
    {
        return buttons;
    }
    
    public WebButton getOpenWorkdayButton()
    {
        return buttons[0][0];
    }
    
    public WebButton getCloseWorkdayButton()
    {
        return buttons[0][1];
    }

    public WebButton getQuantitySetupButton()
    {
        return buttons[1][0];
    }
    
    public WebButton getXReportButton()
    {
        return buttons[1][1];
    }
    
    public WebButton getChequeProcessButton()
    {
        return buttons[2][0];
    }
    
    public WebButton getPaymentChooseButton()
    {
        return buttons[2][1];
    }

    @Override
    public void init()
    {
        setupActionListeners();
    }
}
