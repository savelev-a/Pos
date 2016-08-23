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

package ru.codemine.pos.ui.windows.misc;

import com.alee.extended.layout.TableLayout;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.rootpane.WebFrame;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class ProgressFrame extends WebFrame
{
    private WebProgressBar progressBar;
    
    public ProgressFrame()
    {
        super();
        setTitle("Загрузка данных...");
        setSize(600, 100);
        setLocationRelativeTo(null);
        
        progressBar = new WebProgressBar(0, 100);
        
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.FILL, 10},
            {10, TableLayout.PREFERRED, 10}             
        });
        setLayout(layout);
        
        add(progressBar, "1, 1");
    }
    
    public void setMax(int max)
    {
        progressBar.setMaximum(max);
    }
    
    public void setVal(int val)
    {
        progressBar.setValue(val);
    }
    
    public void showWindow()
    {
        progressBar.setValue(0);
        setVisible(true);
    }
    
    public void hideWindow()
    {
        setVisible(false);
    }

}
