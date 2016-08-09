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

import com.alee.extended.image.WebImage;
import com.alee.extended.layout.TableLayout;
import com.alee.global.StyleConstants;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.table.WebTable;
import com.alee.laf.text.WebTextField;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import javax.swing.ImageIcon;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Alexander Savelev
 */
public class ChequeSetupPanel extends WebPanel
{
    private final WebTable table;
    private final WebTextField inputField;
    
    public ChequeSetupPanel()
    {
        setMargin(10);
        setUndecorated(false);
        setRound(StyleConstants.largeRound);
        
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10},
            {10, TableLayout.FILL, 5, TableLayout.PREFERRED, 10}
        });
        setLayout(layout);
        
        //temp//
        String[] headers = {"№", "Наименование товара", "Цена", "Кол-во", "Сумма"};
        String[][] data = {{"1", "Одеяло теплое", "1200.0", "2", "2400.0"}, {"2", "Подушка", "5000.0", "1", "5000.0"}};
        table = new WebTable(data, headers);
        table.setEditable(false);
        table.setFontSize(22);
        table.setRowHeight(40);
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setMinWidth(300);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        
        WebScrollPane scrollPane = new WebScrollPane(table);
        add(scrollPane, "1, 1, 3, 1");
        
        inputField = new WebTextField();
        inputField.setFontSize(18);
        inputField.setMargin(0, 2, 0, 0);
        inputField.setLeadingComponent(new WebImage("images/icons/16x16/Lens-01.png"));
        
        add(inputField, "3, 3");
        add(new WebLabel("Поиск по штрих-коду: "), "1, 3");
        
    }
    
    public WebTextField getInputField()
    {
        return inputField;
    }

}
