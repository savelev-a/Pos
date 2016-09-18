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
import com.alee.extended.panel.GroupPanel;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.text.WebTextField;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.entity.Shop;
import ru.codemine.pos.exception.DuplicateDataException;
import ru.codemine.pos.service.ShopService;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class ShopSettingsWindow extends WebFrame
{
    @Autowired private Application application;
    @Autowired private ShopService shopService;
    
    private WebLabel nameLabel;
    private WebLabel addressLabel;
    private WebLabel orgLabel;
    private WebLabel orgNameLabel;
    private WebLabel orgInnLabel;
    private WebLabel orgKppLabel;
    
    private WebTextField nameField;
    private WebTextArea addressTextArea;
    private WebTextField orgNameField;
    private WebTextField orgInnField;
    private WebTextField orgKppField;
    
    protected boolean actionListenersInit;
    
    protected WebButton saveButton;
    protected WebButton cancelButton;
    protected GroupPanel buttonsGroupPanel;
    
    public ShopSettingsWindow()
    {
        setTitle("Настройки торговой точки");
        setMinimumSize(new Dimension(800, 400));
        setLocationRelativeTo(null);
        
        actionListenersInit = false;
     
        saveButton   = new WebButton("Сохранить",     new ImageIcon("images/icons/default/16x16/button-ok.png"));
        cancelButton = new WebButton("Отмена", new ImageIcon("images/icons/default/16x16/button-cancel.png"));
        saveButton.setMargin(5);
        cancelButton.setMargin(5);
        saveButton.setRound(StyleConstants.largeRound);
        cancelButton.setRound(StyleConstants.largeRound);
        buttonsGroupPanel = new GroupPanel(10, saveButton, cancelButton);
        
        nameLabel = new WebLabel("Название магазина");
        addressLabel = new WebLabel("Адрес");
        orgLabel = new WebLabel("Реквизиты организации (печатаются в чеках)");
        orgNameLabel = new WebLabel("Название организации");
        orgInnLabel = new WebLabel("ИНН");
        orgKppLabel = new WebLabel("КПП");
        
        nameField = new WebTextField();
        addressTextArea = new WebTextArea();
        orgNameField = new WebTextField();
        orgInnField = new WebTextField();
        orgKppField = new WebTextField();
        
        
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10},
            {10, TableLayout.PREFERRED,                 // name
             10, TableLayout.FILL,                      // address
             10, TableLayout.PREFERRED,                 // orglabel
             10, TableLayout.PREFERRED,                 // orgname
             10, TableLayout.PREFERRED,                 // inn, kpp
             10, TableLayout.PREFERRED, 10}             // Кнопки
        });
        setLayout(layout);
        
        add(nameLabel, "1, 1");
        add(nameField, "3, 1, 7, 1, F, F");
        add(addressLabel, "1, 3");
        add(new WebScrollPane(addressTextArea), "3, 3, 7, 3, F, F");
        add(orgLabel, "1, 5, 7, 5, C, T");
        add(orgNameLabel, "1, 7");
        add(orgNameField, "3, 7, 7, 7, F, F");
        add(orgInnLabel, "1, 9");
        add(orgInnField, "3, 9");
        add(orgKppField, "5, 9");
        add(orgKppField, "7, 9");
        add(buttonsGroupPanel, "1, 11, 7, 11, C, T");
    }
    
    public void showWindow()
    {
        if(!actionListenersInit) setupActionListeners();
        
        Shop currentShop = application.getCurrentShop();
        
        nameField.setText(currentShop.getName());
        addressTextArea.setText(currentShop.getAddress());
        orgNameField.setText(currentShop.getOrgName());
        orgInnField.setText(currentShop.getOrgInn());
        orgKppField.setText(currentShop.getOrgKpp());
        
        setVisible(true);
    }
    
    public void setupActionListeners()
    {
        cancelButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        });
        
        saveButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                Shop shop = application.getCurrentShop();
                
                shop.setName(nameField.getText());
                shop.setAddress(addressTextArea.getText());
                shop.setOrgName(orgNameField.getText());
                shop.setOrgInn(orgInnField.getText());
                shop.setOrgKpp(orgKppField.getText());
                
                try
                {
                    shopService.update(shop);
                }
                catch (DuplicateDataException ex)
                {
                    WebOptionPane.showMessageDialog(rootPane, ex.getLocalizedMessage(), "Ошибка", WebOptionPane.WARNING_MESSAGE);
                }
                
                setVisible(false);
            }
        });
        
        actionListenersInit = true;
    }

}
