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

package ru.codemine.pos.ui.docspanel.modules;

import com.alee.extended.layout.TableLayout;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;

/**
 *
 * @author Alexander Savelev
 */
public class StoresPanel extends WebPanel
{
    private final WebButton showStoresBtn;
    private final WebButton showStartBalancesBtn;
    
    public StoresPanel()
    {
        setMargin(5);
        setUndecorated(false);
        setRound(StyleConstants.largeRound);
        
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.FILL, 10},
            {5, TableLayout.PREFERRED, 10, TableLayout.PREFERRED, 10, TableLayout.PREFERRED, 10}
        });
        setLayout(layout);
        
        showStoresBtn = new WebButton("Показать склады");
        showStartBalancesBtn = new WebButton("Начальные остатки складов");
        
        add(new WebLabel("-----Склады-----"), "1, 1, C, T");
        add(showStoresBtn, "1, 3");
        add(showStartBalancesBtn, "1, 5");
        
    }
    
    public WebButton getShowStoresBtn()
    {
        return showStoresBtn;
    }
    
    public WebButton getShowStartBalancesBtn()
    {
        return showStartBalancesBtn;
    }
}
