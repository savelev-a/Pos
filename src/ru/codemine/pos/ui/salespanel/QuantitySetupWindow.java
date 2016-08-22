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
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.spinner.WebSpinner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.JTextComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.service.StoreService;
import ru.codemine.pos.ui.MainWindow;
import ru.codemine.pos.ui.salespanel.listener.SetQuantityListener;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class QuantitySetupWindow extends WebFrame
{
    @Autowired private MainWindow mainWindow;
    @Autowired private SalesPanel salesPanel;
    @Autowired private StoreService storeService;
    @Autowired private SetQuantityListener setQuantityListener;
    
    private final WebLabel quantityLabel = new WebLabel("Количество");
    private final WebLabel onStoresLabel = new WebLabel();
    private final WebSpinner spinner = new WebSpinner();
    private final WebButton okButton = new WebButton("ОК", new ImageIcon("images/icons/default/16x16/button-ok.png"));
    private final WebButton cancelButton = new WebButton("Отмена", new ImageIcon("images/icons/default/16x16/button-cancel.png"));
    private boolean actionListenersInit;
    
    public QuantitySetupWindow()
    {
        setTitle("Задайте количество");
        setSize(300, 150);
        setLocationRelativeTo(null);
        
        okButton.setMargin(5);
        cancelButton.setMargin(5);
        okButton.setRound(StyleConstants.largeRound);
        cancelButton.setRound(StyleConstants.largeRound);
        GroupPanel buttonsGroupPanel = new GroupPanel(10, okButton, cancelButton);
        
        
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10},
            {10, TableLayout.PREFERRED,         // установка кол-ва
             10, TableLayout.PREFERRED,         // доступное кол-во
             10, TableLayout.PREFERRED,10}      // кнопки
        });
        setLayout(layout);
        
        add(quantityLabel, "1, 1");
        add(spinner, "3, 1");
        add(onStoresLabel, "3, 3");
        add(buttonsGroupPanel, "1, 5, 3, 5, C, T");

        setResizable(false);
        getRootPane().setDefaultButton(okButton);
        
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                mainWindow.unblockBarcodeInput();
                setVisible(false);
            }
        });
        
        actionListenersInit = false;
    }

    public void showWindow()
    {
        if(!actionListenersInit) setupActionListeners();
        mainWindow.blockBarcodeInput();
        
        Product product = salesPanel.getChequeSetupPanel().getSelectedProduct();
        if(product == null) 
        {
            mainWindow.unblockBarcodeInput();
            return;
        }

        int maxQuantity = storeService.getAvaibleStocksOnRetail(product);
        onStoresLabel.setText("Доступно: " + String.valueOf(maxQuantity) + " шт.");
        setTitle("Количество - " + product.getName());
        spinner.setModel(new SpinnerNumberModel(1, 1, maxQuantity, 1));

        
        
        JTextField ft = ((WebSpinner.DefaultEditor)spinner.getEditor()).getTextField();
        ft.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                final JTextComponent txtcomp = (JTextComponent)e.getSource();
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        txtcomp.selectAll();
                    }
                });
            }
        });
        ft.requestFocus();

        setVisible(true);
    }

    public void setupActionListeners()
    {
        okButton.addActionListener(setQuantityListener);
        cancelButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainWindow.unblockBarcodeInput();
                setVisible(false);
            }
        });
        
        spinner.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    okButton.doClick();
                }
            }
        });
        actionListenersInit = true;
    }

    public int getQuantity()
    {
        return (Integer)spinner.getValue();
    }
}
