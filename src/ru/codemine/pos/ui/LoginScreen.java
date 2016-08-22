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
import com.alee.extended.panel.GroupPanel;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.text.WebPasswordField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.entity.User;
import ru.codemine.pos.service.UserService;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class LoginScreen 
{
    @Autowired
    private Application application;
    
    @Autowired
    private UserService userService;
    
    private final WebFrame frame;
    private final WebLabel usernameLabel;
    private final WebLabel passwordLabel;
    private final WebComboBox usernameComboBox;
    private final WebPasswordField passwordField;
    private final WebButton okButton;
    private final WebButton cancelButton;
    
    public LoginScreen()
    {
        frame = new WebFrame("Вход в систему");
        frame.setSize(400, 150);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.PREFERRED, 15, TableLayout.FILL, 10},
            {10, TableLayout.PREFERRED, 10, TableLayout.PREFERRED, TableLayout.FILL, TableLayout.PREFERRED, 10}
        });
        
        frame.setLayout(layout);
        
        usernameLabel = new WebLabel("Имя пользователя");
        passwordLabel = new WebLabel("Пароль");
        
        usernameComboBox = new WebComboBox();
        passwordField = new WebPasswordField();
        passwordField.setInputPrompt("Введите пароль...");
        
        okButton     = new WebButton("Ок",     new ImageIcon("images/icons/default/16x16/button-ok.png"));
        cancelButton = new WebButton("Отмена", new ImageIcon("images/icons/default/16x16/button-cancel.png"));
        okButton.setMargin(5);
        cancelButton.setMargin(5);
        okButton.setRound(StyleConstants.largeRound);
        cancelButton.setRound(StyleConstants.largeRound);
        GroupPanel buttonsGroupPanel = new GroupPanel(10, okButton, cancelButton);
        
        frame.add(usernameLabel, "1, 1");
        frame.add(usernameComboBox, "3, 1");
        frame.add(passwordLabel, "1, 3");
        frame.add(passwordField, "3, 3");
        frame.add(buttonsGroupPanel, "1, 5, 3, 5, C, T");
        
        frame.getRootPane().setDefaultButton(okButton);
        
        cancelButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                application.close();
            }
        });
        
        okButton.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                String username = (String)usernameComboBox.getSelectedItem();
                String password = new String(passwordField.getPassword());
                if(userService.performLogin(username, password))
                {
                    application.setActiveUser(userService.getByUsername(username));
                    
                    application.showMainWindow();
                    close();
                }
                else
                {
                    WebOptionPane.showMessageDialog(null,  
                        "Проверьте правильность пароля", 
                        "Ошибка входа", WebOptionPane.ERROR_MESSAGE);
                    
                    passwordField.clear();
                }
            }
        });
    }
    
    public void show()
    {
        for(User user : userService.getAllUsers())
        {
            usernameComboBox.addItem(user.getUsername());
        }
        
        frame.setVisible(true);
        passwordField.requestFocusInWindow();
    }
    
    public void close()
    {
        frame.setVisible(false);
    }

}
