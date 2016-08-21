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
import com.alee.laf.panel.WebPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.ui.GenericPanelComponent;
import ru.codemine.pos.ui.salespanel.listener.ChequeTableKeyListener;
import ru.codemine.pos.ui.salespanel.modules.ButtonsPanel;
import ru.codemine.pos.ui.salespanel.modules.CalcsPanel;
import ru.codemine.pos.ui.salespanel.modules.ChequeSetupPanel;
import ru.codemine.pos.ui.salespanel.modules.UpperStatusPanel;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class SalesPanel extends WebPanel implements GenericPanelComponent
{
    @Autowired
    private ButtonsPanel buttonsPanel;
    
    private final UpperStatusPanel upperStatusPanel;
    private final ChequeSetupPanel chequeSetupPanel;
    private final CalcsPanel calcsPanel;
    
    
    public SalesPanel()
    {
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.FILL, 10, TableLayout.PREFERRED, 10},
            {10, TableLayout.PREFERRED, 
             10, TableLayout.FILL, 
             10, TableLayout.PREFERRED, 
             10, TableLayout.PREFERRED, 10}
        });
        setLayout(layout);
        
        upperStatusPanel = new UpperStatusPanel();
        chequeSetupPanel = new ChequeSetupPanel();
        calcsPanel = new CalcsPanel();
        
        add(upperStatusPanel, "1, 1, 3, 1");
        add(chequeSetupPanel, "1, 3");
        add(calcsPanel, "3, 3");
        
    }
    
    @Override
    public void init()
    {
        add(buttonsPanel, "1, 5, 3, 5");
        buttonsPanel.init();
        
        setupActionListeners();
    }

    public UpperStatusPanel getUpperStatusPanel()
    {
        return upperStatusPanel;
    }

    public ChequeSetupPanel getChequeSetupPanel()
    {
        return chequeSetupPanel;
    }

    public CalcsPanel getCalcsPanel()
    {
        return calcsPanel;
    }

    @Override
    public void setupActionListeners()
    {
        chequeSetupPanel.getTable().addKeyListener(new ChequeTableKeyListener(this));
    }

}
