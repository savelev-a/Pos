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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import org.joda.time.DateTime;

/**
 *
 * @author Alexander Savelev
 */
public class UpperStatusPanel extends WebPanel
{
    
    private final WebLabel currentUserStatus;
    private final WebLabel currentDateTime;
    private final WebLabel centerTopStatus;
    private final WebLabel centerBottomStatus;
    private final WebLabel rightTopStatus;
    private final WebLabel leftBottomStatus;
    
    public UpperStatusPanel()
    {
        setMargin(10);
        setUndecorated(false);
        setRound(StyleConstants.largeRound);
        
        TableLayout layout = new TableLayout(new double[][]{
            {TableLayout.PREFERRED, 10, TableLayout.FILL, 10, TableLayout.PREFERRED},
            {TableLayout.PREFERRED, 10, TableLayout.PREFERRED}
        });
        setLayout(layout);
        
        currentUserStatus = new WebLabel();
        currentDateTime = new WebLabel();
        centerTopStatus = new WebLabel();
        centerBottomStatus = new WebLabel();
        rightTopStatus = new WebLabel();
        leftBottomStatus = new WebLabel();
        
        add(currentUserStatus, "0, 0, L, T");
        add(currentDateTime, "4, 2, R, T");
        add(leftBottomStatus, "0, 2, L, T");
        add(centerTopStatus, "2, 0, C, T");
        add(centerBottomStatus, "2, 2, C, T");
        add(rightTopStatus, "4, 0, R, T");
        
        
        SetupTimer();
    }
    
    public void setCurrentUserStatus(String text)
    {
        currentUserStatus.setText("<html>Текущий пользователь: <b>" + text + "</b></html>");
    }
    
    public void setCenterTopStatus(String text)
    {
        centerTopStatus.setText(text);
    }
    
    public void setCenterBottomStatus(String text)
    {
        centerBottomStatus.setText(text);
    }
    
    public void setRightTopStatus(String text)
    {
        rightTopStatus.setText(text);
    }
    
    public void setLeftBottomStatus(String text)
    {
        leftBottomStatus.setText(text);
    }
    
    
    private void SetupTimer()
    {
        Timer timer = new Timer(1000, new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                currentDateTime.setText(DateTime.now().toString("dd.MM.YY HH:mm:ss"));
            }
        });
        
        timer.start();
    }
}
