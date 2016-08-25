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

import com.alee.extended.layout.TableLayout;
import com.alee.extended.layout.ToolbarLayout;
import com.alee.extended.statusbar.WebMemoryBar;
import com.alee.extended.statusbar.WebStatusBar;
import com.alee.extended.statusbar.WebStatusLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.tabbedpane.WebTabbedPane;
import java.awt.KeyboardFocusManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.WindowConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.service.WorkdayService;
import ru.codemine.pos.ui.docspanel.DocumentsPanel;
import ru.codemine.pos.ui.keydispatcher.MainKeyDispatcher;
import ru.codemine.pos.ui.salespanel.SalesPanel;
import ru.codemine.pos.ui.settingspanel.SettingsPanel;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class MainWindow extends WebFrame
{
    @Autowired private Application application;
    @Autowired private WorkdayService workdayService;

    @Autowired private SalesPanel salesPanel;
    @Autowired private DocumentsPanel documentsPanel;
    @Autowired private SettingsPanel settingsPanel;
    
    @Autowired private MainKeyDispatcher keyDispatcher;
    
    private final WebTabbedPane tabs;
    private final WebStatusBar statusBar;
    
    private boolean inputBlocked;
    
    public MainWindow()
    {
        setTitle("Торговая касса v0.1alpha ");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        inputBlocked = true;
        
        tabs = new WebTabbedPane(WebTabbedPane.BOTTOM);
        
        statusBar = new WebStatusBar();
        statusBar.add(new WebStatusLabel("Статус: готов"));
        statusBar.add(new WebMemoryBar(), ToolbarLayout.END);
        
        TableLayout layout = new TableLayout(new double[][]{
            {TableLayout.FILL}, {TableLayout.FILL, 15, TableLayout.PREFERRED}
        });
        setLayout(layout);
        
        add(tabs, "0, 0");
        add(statusBar, "0, 2");
    
    }
    
    public void showMainWindow()
    {
        refreshStatus();
        setVisible(true);
        inputBlocked = false;
        
        tabs.addTab("Продажи",   new ImageIcon("images/icons/default/32x32/tab-sales.png"), salesPanel);
        salesPanel.init();
        
        tabs.addTab("Документы", new ImageIcon("images/icons/default/32x32/tab-docs.png"), documentsPanel);
        documentsPanel.init();
        
        tabs.addTab("Отчеты",    new ImageIcon("images/icons/default/32x32/tab-reports.png"), new WebPanel());
        //init
        
        tabs.addTab("Настройки", new ImageIcon("images/icons/default/32x32/tab-settings.png"), settingsPanel);
        settingsPanel.init();
        
        setupActionListeners();
        setupKeyboard();
    }
    
    public void refreshStatus()
    {
        salesPanel.getUpperStatusPanel().setCurrentUserStatus(application.getActiveUser().getUsername());
        salesPanel.getUpperStatusPanel().setRightTopStatus(workdayService.isWorkdayOpened() ? "Смена открыта" : "Смена закрыта");
        salesPanel.getUpperStatusPanel().setLeftBottomStatus("Выручка текущей смены: " 
                + (workdayService.isWorkdayOpened() 
                        ? workdayService.getOpenWorkday().getTotal() + " руб."
                        : "смена закрыта"));

    }
    
    private void setupActionListeners()
    {
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if(WebOptionPane.showConfirmDialog(rootPane, 
                        "Вы хотите выйти?", "Подтвердите действие", 
                        WebOptionPane.YES_NO_OPTION, WebOptionPane.QUESTION_MESSAGE) == 0)
                {
                    application.close();
                }
                
            }
        });
  
    }

    private void setupKeyboard()
    {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(keyDispatcher);
    }
    
    public int getActiveTabIndex()
    {
        return tabs.getSelectedIndex();
    }
    
    public boolean isBarcodeInputBlocked()
    {
        return inputBlocked;
    }
    
    public void blockBarcodeInput()
    {
        inputBlocked = true;
    }
    
    public void unblockBarcodeInput()
    {
        inputBlocked = false;
    }
}
