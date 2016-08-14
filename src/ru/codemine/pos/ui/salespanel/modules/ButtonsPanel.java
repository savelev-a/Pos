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

/**
 *
 * @author Alexander Savelev
 */
public class ButtonsPanel extends WebPanel
{
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
                add(buttons[i][j], "" + (i * 2) + ", " + (j * 2));
            }
        }
        
        buttons[0][0].setText("Открыть смену (F2)");
        buttons[0][1].setText("Закрыть смену (Z-отчет) (F3)");
        buttons[1][0].setText("Пробить чек (F5)");
        
        
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

    public WebButton getChequeProcessButton()
    {
        return buttons[1][0];
    }
}
