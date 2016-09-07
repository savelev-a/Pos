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

package ru.codemine.pos.ui.settingspanel;

import com.alee.extended.layout.TableLayout;
import com.alee.laf.panel.WebPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.ui.GenericPanelComponent;
import ru.codemine.pos.ui.settingspanel.listener.ShowKkmsButtonListener;
import ru.codemine.pos.ui.settingspanel.listener.ShowUsersButtonListener;
import ru.codemine.pos.ui.settingspanel.modules.CommonSettingsPanel;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class SettingsPanel extends WebPanel implements GenericPanelComponent
{
    @Autowired private ShowUsersButtonListener showUsersButtonListener;
    @Autowired private ShowKkmsButtonListener showKkmsButtonListener;
    
    private final CommonSettingsPanel commonSettingsPanel;
    
    public SettingsPanel()
    {
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.PREFERRED, 10, TableLayout.PREFERRED, 10},
            {10, TableLayout.PREFERRED, 
             10, TableLayout.PREFERRED, 
             10, TableLayout.PREFERRED, 
             10}
        });
        setLayout(layout);
        
        commonSettingsPanel = new CommonSettingsPanel();
        
        add(commonSettingsPanel, "1, 1");
    }
    
    public CommonSettingsPanel getCommonSettingsPanel()
    {
        return commonSettingsPanel;
    }

    @Override
    public void init()
    {
        setupActionListeners();
    }

    @Override
    public void setupActionListeners()
    {
        commonSettingsPanel.getShowUsersButton().addActionListener(showUsersButtonListener);
        commonSettingsPanel.getShowKkmsButton().addActionListener(showKkmsButtonListener);
    }

}
