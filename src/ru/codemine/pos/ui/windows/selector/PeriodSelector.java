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

package ru.codemine.pos.ui.windows.selector;

import com.alee.extended.date.WebDateField;
import com.alee.extended.layout.TableLayout;
import com.alee.extended.panel.GroupPanel;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.rootpane.WebFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;
import ru.codemine.pos.ui.windows.PeriodSelectable;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class PeriodSelector extends WebFrame
{
    private final WebLabel dateStartLabel;
    private final WebLabel dateEndLabel;
    private final WebDateField dateStartField;
    private final WebDateField dateEndField;
    
    protected WebButton selectButton;
    protected WebButton cancelButton;
    protected GroupPanel buttonsGroupPanel;
    
    protected boolean actionListenersInit;
    
    protected PeriodSelectable clientWindow;
    
    public PeriodSelector()
    {
        setTitle("Выберите период");
        setSize(400, 200);
        setLocationRelativeTo(null);
        
        dateStartLabel = new WebLabel("Начало периода");
        dateEndLabel = new WebLabel("Конец периода");
        
        dateStartField = new WebDateField(LocalDate.now().toDate());
        dateEndField = new WebDateField(LocalDate.now().toDate());
        
        selectButton   = new WebButton("Выбрать", new ImageIcon("images/icons/default/16x16/button-ok.png"));
        cancelButton = new WebButton("Отмена",    new ImageIcon("images/icons/default/16x16/button-cancel.png"));
        selectButton.setMargin(5);
        cancelButton.setMargin(5);
        selectButton.setRound(StyleConstants.largeRound);
        cancelButton.setRound(StyleConstants.largeRound);
        buttonsGroupPanel = new GroupPanel(10, selectButton, cancelButton);
        
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10},
            {10, TableLayout.PREFERRED, 10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10, TableLayout.PREFERRED, 10}
        });
        setLayout(layout);
        
        add(dateStartLabel, "1, 1");
        add(dateStartField, "3, 1");
        add(dateEndLabel, "1, 3");
        add(dateEndField, "3, 3");
        add(buttonsGroupPanel, "1, 7, 3, 7, C, T");
        
        clientWindow = null;
    }
    
    public void selectFor(PeriodSelectable window)
    {
        this.clientWindow = window;
        if(!actionListenersInit) setupActionListeners();
        setVisible(true);
    }

    public void setupActionListeners()
    {
        selectButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                clientWindow.setPeriod(new LocalDate(dateStartField.getDate()), new LocalDate(dateEndField.getDate()));
                setVisible(false);
            }
            
        });
        
        cancelButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        });
        
        actionListenersInit = true;
    }
}
