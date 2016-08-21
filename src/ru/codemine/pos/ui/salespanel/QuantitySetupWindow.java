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
import com.alee.extended.panel.WebButtonGroup;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.spinner.WebSpinner;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.ui.MainWindow;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class QuantitySetupWindow extends WebFrame
{
    @Autowired private MainWindow mainWindow;
    
    private final WebLabel quantityLabel = new WebLabel("Количество");
    private final WebLabel onStoresLabel = new WebLabel("Доступно: 0 шт.");
    private final WebSpinner spinner = new WebSpinner();
    private final WebButton okButton = new WebButton("ОК");
    private final WebButton cancelButton = new WebButton("Отмена");
    
    public QuantitySetupWindow()
    {
        setTitle("Задайте количество");
        
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10},
            {10, TableLayout.PREFERRED,         // установка кол-ва
             10, TableLayout.PREFERRED,         // доступное кол-во
             10, TableLayout.PREFERRED,10}      // кнопки
        });
        setLayout(layout);
        
        add(quantityLabel, "1, 1");
        add(spinner, "3, 1");
        add(onStoresLabel, "1, 3, 3, 3, L, T");
        add(new WebButtonGroup(okButton, cancelButton), "1, 5, 3, 5, C, T");
        
        pack();
        setResizable(false);
        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                mainWindow.unblockBarcodeInput();
                setVisible(false);
            }
        });
    }

    public void showWindow()
    {
        mainWindow.blockBarcodeInput();
        setVisible(true);
    }

}
