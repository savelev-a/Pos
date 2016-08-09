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

package ru.codemine.pos.ui;

import com.alee.extended.image.WebImage;
import com.alee.extended.layout.TableLayout;
import com.alee.laf.label.WebLabel;
import com.alee.laf.progressbar.WebProgressBar;
import com.alee.laf.rootpane.WebFrame;
import java.awt.Font;


/**
 *
 * @author Alexander Savelev
 */
public class LoadingScreen 
{
    private final WebFrame frame;
    private final WebImage image;
    private final WebLabel statusLabel;
    private final WebLabel titleLabel;
    private final WebProgressBar progressBar;
    
    public LoadingScreen()
    {
        frame = new WebFrame();
        frame.setSize(400, 300);
        //frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);

        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.FILL, 10},
            {10, TableLayout.PREFERRED, 20, TableLayout.PREFERRED, TableLayout.FILL, TableLayout.PREFERRED, 5, TableLayout.PREFERRED, 10}
        });
        frame.setLayout(layout);
        
        image = new WebImage("images/loading.png");
        
        statusLabel = new WebLabel("");
        titleLabel = new WebLabel("Торговая касса v0.1");
        titleLabel.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 22));
        
        progressBar = new WebProgressBar(0, 100);
        
        frame.add(titleLabel, "1, 1, C, T");
        frame.add(image, "1, 3");
        frame.add(progressBar, "1, 5");
        frame.add(statusLabel, "1, 7, C, T");
        
    }
    
    public void show()
    {
        frame.setVisible(true);
    }
    
    public void setLoadingStatus(String message)
    {
        statusLabel.setText(message);
    }
    
    public void setLoadingStatus(String message, Integer value)
    {
        setLoadingStatus(message);
        setLoadingProgress(value);
    }
    
    public void setLoadingProgress(Integer value)
    {
        if(value >= 0 && value <= 100)
            progressBar.setValue(value);
    }
    
    public void close()
    {
        frame.setVisible(false);
    }
    
    public WebFrame getFrame()
    {
        return frame;
    }
}
