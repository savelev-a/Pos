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
public class ChequesPanel extends WebPanel
{
    private final WebButton showOpenWdChequesBtn;
    private final WebButton showChequesByPeriodBtn;
    
    public ChequesPanel()
    {
        setMargin(5);
        setUndecorated(false);
        setRound(StyleConstants.largeRound);
        
        
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.FILL, 10},
            {5, TableLayout.PREFERRED,          // Заголовок
            10, TableLayout.PREFERRED,          // Чеки открытой смены
            10, TableLayout.PREFERRED, 10}      // Чеки за период
        });
        setLayout(layout);
        
        showOpenWdChequesBtn   = new WebButton("Чеки открытой смены");
        showChequesByPeriodBtn = new WebButton("Чеки за период");
        showOpenWdChequesBtn.setMargin(5);
        showChequesByPeriodBtn.setMargin(5);
        showOpenWdChequesBtn.setRound(StyleConstants.largeRound);
        showChequesByPeriodBtn.setRound(StyleConstants.largeRound);

        add(new WebLabel("-----Чеки-----"), "1, 1, C, T");
        add(showOpenWdChequesBtn,           "1, 3");
        add(showChequesByPeriodBtn,         "1, 5");
    }
    
    public WebButton getOpenWdChequesButton()
    {
        return showOpenWdChequesBtn;
    }
    
    public WebButton getChequesByPeriodButton()
    {
        return showChequesByPeriodBtn;
    }
}
