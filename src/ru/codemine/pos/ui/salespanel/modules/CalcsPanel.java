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
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;

/**
 *
 * @author Alexander Savelev
 */
public class CalcsPanel extends WebPanel
{
    private final WebLabel[][] labels;
    private final WebLabel bottomLabel;
    public CalcsPanel()
    {
        setMargin(10);
        setUndecorated(false);
        setMinimumWidth(300);
        setRound(StyleConstants.largeRound);
        
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10},
            {10, TableLayout.PREFERRED,
             10, TableLayout.PREFERRED,
             10, TableLayout.PREFERRED,
             10, TableLayout.PREFERRED,
             10, TableLayout.PREFERRED,
             10, TableLayout.PREFERRED,
             10, TableLayout.PREFERRED,10}
        });
        setLayout(layout);
        
        labels = new WebLabel[2][6];
        for(int i = 0; i < 2; i++)
        {
            for(int j = 0; j < 6; j++)
            {
                labels[i][j] = new WebLabel("");
                labels[i][j].setFontSize(16);
                add(labels[i][j], "" + (i * 2 + 1) + ", " + (j * 2 + 1) + (i == 0 ? ", L, T" : ", R, T"));
            }
        }

        bottomLabel = new WebLabel("");
        bottomLabel.setFontSize(18);
        add(bottomLabel, "1, 13, 3, 13");
        
        
        labels[0][0].setText("Сумма:");
        labels[1][0].setText("<html><b>7400.0 руб.</b></html>");
        labels[0][1].setText("НДС:");
        labels[1][1].setText("<html><b>1332.0 руб.</b></html>");
        labels[0][2].setText("Скидки:");
        labels[1][2].setText("<html><b>0.0 руб.</b></html>");
        
        bottomLabel.setText("Итого: 7400.0 руб.");
    }
}
