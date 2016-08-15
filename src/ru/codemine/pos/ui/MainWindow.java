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
import com.alee.laf.text.WebTextField;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.WindowConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.entity.document.Cheque;
import ru.codemine.pos.service.ProductService;
import ru.codemine.pos.service.StoreService;
import ru.codemine.pos.service.WorkdayService;
import ru.codemine.pos.tablemodel.ChequeSetupTableModel;
import ru.codemine.pos.ui.docspanel.DocumentsPanel;
import ru.codemine.pos.ui.docspanel.listener.ShowStoresButtonListener;
import ru.codemine.pos.ui.salespanel.SalesPanel;
import ru.codemine.pos.ui.salespanel.listener.ChequeProcessButtonListener;
import ru.codemine.pos.ui.salespanel.listener.ChequeTableKeyListener;
import ru.codemine.pos.ui.salespanel.listener.CloseWorkdayButtonListener;
import ru.codemine.pos.ui.salespanel.listener.OpenWorkdayButtonListener;
import ru.codemine.pos.ui.salespanel.modules.ButtonsPanel;
import ru.codemine.pos.ui.salespanel.modules.CalcsPanel;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class MainWindow extends WebFrame
{
    @Autowired private Application application;
    @Autowired private WorkdayService workdayService;
    @Autowired private ProductService productService;
    @Autowired private StoreService storeService;
    
    @Autowired private OpenWorkdayButtonListener openWorkdayButtonListener;
    @Autowired private CloseWorkdayButtonListener closeWorkdayButtonListener;
    @Autowired private ChequeProcessButtonListener chequeProcessButtonListener;
    
    @Autowired private ShowStoresButtonListener showStoresButtonListener;
    
    private final WebTabbedPane tabs;
    private final SalesPanel salesPanel;
    private final DocumentsPanel documentsPanel;
    private final WebStatusBar statusBar;
    
    private boolean inputBlocked;
    
    public MainWindow()
    {
        setTitle("Торговая касса v0.1alpha ");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        inputBlocked = true;
        
        salesPanel = new SalesPanel();
        documentsPanel = new DocumentsPanel();
        
        tabs = new WebTabbedPane(WebTabbedPane.BOTTOM);
        tabs.addTab("Продажи", new ImageIcon("images/icons/32x32/Coffee-01.png"), salesPanel);
        tabs.addTab("Документы", new ImageIcon("images/icons/32x32/Library-01.png"), documentsPanel);
        tabs.addTab("Отчеты", new ImageIcon("images/icons/32x32/Document-01.png"), new WebPanel());
        tabs.addTab("Настройки", new ImageIcon("images/icons/32x32/Gear-01.png"), new WebPanel());
        
        //tabs.setRound(StyleConstants.largeRound);
        
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
        
        setupActionListeners();
        setupKeyboard();
    }
    
    public void refreshStatus()
    {

        salesPanel.getUpperStatusPanel().setCurrentUserStatus(application.getActiveUser().getUsername());
        salesPanel.getUpperStatusPanel().setRightTopStatus(workdayService.isWorkdayOpened() ? "Смена открыта" : "Смена закрыта");
        salesPanel.getUpperStatusPanel().setLeftBottomStatus("Статус чека итд.");

    }
    
    private void setupActionListeners()
    {
        ButtonsPanel buttonsPanel = salesPanel.getButtonsPanel();

        buttonsPanel.getOpenWorkdayButton().addActionListener(openWorkdayButtonListener);
        buttonsPanel.getCloseWorkdayButton().addActionListener(closeWorkdayButtonListener);
        buttonsPanel.getChequeProcessButton().addActionListener(chequeProcessButtonListener);
        
        documentsPanel.getStoresPanel().getShowStoresBtn().addActionListener(showStoresButtonListener);
        
        salesPanel.getChequeSetupPanel().getTable().addKeyListener(new ChequeTableKeyListener(salesPanel));
        
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
        manager.addKeyEventDispatcher(new KeyDispatcher());
    }
    
    private class KeyDispatcher implements KeyEventDispatcher
    {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e)
        {
            WebTextField inputField = salesPanel.getChequeSetupPanel().getInputField();
            ChequeSetupTableModel tableModel = salesPanel.getChequeSetupPanel().getTableModel();
            Cheque setupCheque = salesPanel.getChequeSetupPanel().getTableModel().getCheque();
            ButtonsPanel buttonsPanel = salesPanel.getButtonsPanel();
            CalcsPanel calcsPanel = salesPanel.getCalcsPanel();
            
            //Если на вводе число, при этом активно и является видимым окно набора чеков - 
            //вводим данное число в строку поиска
            if(tabs.getSelectedIndex() == 0 && !inputBlocked
                    && ("1234567890".indexOf(e.getKeyChar()) >= 0))
            {
                e.setSource(inputField);
            }
            //Если на вводе Enter и в строке поиска что-то есть
            //Ищем по ШК позицию и вставляем в чек
            else if(tabs.getSelectedIndex() == 0 && !inputBlocked && !"".equals(inputField.getText())
                    && (e.getKeyCode() == KeyEvent.VK_ENTER))
            {
                //TODO В отдельный класс/сервис/процедуру
                String barcode = inputField.getText();
                inputField.clear();
                Product product = productService.getByBarcode(barcode);
                if(product != null)
                {
                    // Товар найден, проверка остатков
                    Integer quantity = setupCheque.getContents().get(product) == null 
                                ? 1 
                                : setupCheque.getContents().get(product) + 1;
                    
                    if(storeService.checkStocks("Розница", product, quantity))
                    {
                        tableModel.addItem(product, quantity);
                        calcsPanel.showByCheque(setupCheque);
                        tableModel.fireTableDataChanged();
                    }
                    else
                    {
                        WebOptionPane.showMessageDialog(rootPane, "Недостаточно товара на складе");
                    }
                }
                else
                {
                    WebOptionPane.showMessageDialog(rootPane, "Товар по штрихкоду " + barcode + " не найден!");
                }
            }
            else if(tabs.getSelectedIndex() == 0 && !inputBlocked && (e.getKeyCode() == KeyEvent.VK_F2))
            {
                buttonsPanel.getOpenWorkdayButton().doClick();
            }
            else if(tabs.getSelectedIndex() == 0 && !inputBlocked && (e.getKeyCode() == KeyEvent.VK_F3))
            {
                buttonsPanel.getCloseWorkdayButton().doClick();
            }
            else if(tabs.getSelectedIndex() == 0 && !inputBlocked && (e.getKeyCode() == KeyEvent.VK_F5))
            {
                buttonsPanel.getChequeProcessButton().doClick();
            }
            return false;
        }
        
    }

    public SalesPanel getSalesPanel()
    {
        return salesPanel;
    }
}
