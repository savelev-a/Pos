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

package ru.codemine.pos.ui.keydispatcher;

import com.alee.laf.text.WebTextField;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.entity.device.BarcodeScannerDevice;
import ru.codemine.pos.ui.MainWindow;
import ru.codemine.pos.ui.salespanel.SalesPanel;
import ru.codemine.pos.ui.salespanel.modules.ButtonsPanel;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class MainKeyDispatcher implements KeyEventDispatcher
{
    @Autowired private Application application;
    @Autowired private MainWindow mainWindow;
    @Autowired private SalesPanel salesPanel;
    @Autowired private ButtonsPanel buttonsPanel;

    @Override
    public boolean dispatchKeyEvent(KeyEvent e)
    {
        WebTextField inputField = salesPanel.getChequeSetupPanel().getInputField();
        
        int tabIndex = mainWindow.getActiveTabIndex();
        boolean inputBlocked = mainWindow.isBarcodeInputBlocked();

        //Если на вводе число, при этом активно и является видимым окно набора чеков - 
        //вводим данное число в строку поиска
        if(tabIndex == 0 
                && !inputBlocked 
                && application.gerCurrentScanner().getType() == BarcodeScannerDevice.BarcodeScannerType.KEYBOARD_SCANNER
                && ("1234567890".indexOf(e.getKeyChar()) >= 0))
        {
            e.setSource(inputField);
        }
        //Если на вводе Enter и в строке поиска что-то есть
        //Ищем по ШК позицию и вставляем в чек
        else if(tabIndex == 0 
                && !inputBlocked 
                && !"".equals(inputField.getText())
                && (e.getKeyCode() == KeyEvent.VK_ENTER))
        {
            String barcode = inputField.getText();
            inputField.clear();
            salesPanel.addProductByBarcode(barcode);
        }
        else
        {
            if(tabIndex == 0 && !inputBlocked)
            {
                switch(e.getKeyCode())
                {
                    //case KeyEvent.VK_F2 : buttonsPanel.getOpenWorkdayButton().doClick();
                    //    break;
                    //case KeyEvent.VK_F3 : buttonsPanel.getCloseWorkdayButton().doClick();
                    //    break;
                    case KeyEvent.VK_F4 : buttonsPanel.getQuantitySetupButton().doClick();
                        break;
                    case KeyEvent.VK_F5 : buttonsPanel.getChequeProcessButton().doClick();
                }
            }
        }
        
        return false;
    }
}


