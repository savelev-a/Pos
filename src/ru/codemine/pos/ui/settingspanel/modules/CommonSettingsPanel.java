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

package ru.codemine.pos.ui.settingspanel.modules;

import com.alee.extended.layout.TableLayout;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;

/**
 *
 * @author Alexander Savelev
 */
public class CommonSettingsPanel extends WebPanel
{
    private final WebButton showUsersBtn;
    private final WebButton showShopBtn;
    
    public CommonSettingsPanel()
    {
        setMargin(5);
        setUndecorated(false);
        setRound(StyleConstants.largeRound);
        
        
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.FILL, 10},
            {5, TableLayout.PREFERRED, 
            10, TableLayout.PREFERRED,          //users
            10, TableLayout.PREFERRED, 10}      //shop
        });
        setLayout(layout);
        
        showUsersBtn = new WebButton("Настройки пользователей");
        showShopBtn = new WebButton("Настройки торговой точки");
        showUsersBtn.setMargin(5);
        showShopBtn.setMargin(5);
        showUsersBtn.setRound(StyleConstants.largeRound);
        showShopBtn.setRound(StyleConstants.largeRound);

        add(new WebLabel("-----Общие настройки-----"), "1, 1, C, T");
        add(showUsersBtn, "1, 3");
        add(showShopBtn, "1, 5");
    }
    
    public WebButton getShowUsersButton()
    {
        return showUsersBtn;
    }
    
    public WebButton getShowShopButton()
    {
        return showShopBtn;
    }
    
}
