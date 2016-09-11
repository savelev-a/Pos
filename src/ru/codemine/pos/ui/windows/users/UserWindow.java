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

package ru.codemine.pos.ui.windows.users;

import com.alee.extended.layout.TableLayout;
import com.alee.extended.list.CheckBoxCellData;
import com.alee.extended.list.CheckBoxListModel;
import com.alee.extended.list.WebCheckBoxList;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.User;
import ru.codemine.pos.ui.windows.GenericEntityWindow;
import ru.codemine.pos.ui.windows.users.listener.DontSaveUser;
import ru.codemine.pos.ui.windows.users.listener.SaveUser;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class UserWindow extends GenericEntityWindow<User>
{
    @Autowired private SaveUser saveUser;
    @Autowired private DontSaveUser dontSaveUser;
    
    private final WebLabel idLabel;
    private final WebLabel usernameLabel;
    private final WebLabel passwordLabel;
    private final WebLabel retypeLabel;
    private final WebLabel printnameLabel;
    private final WebLabel rolesLabel;
    
    private final WebLabel userIdField;
    private final WebTextField usernameField;
    private final WebPasswordField userPasswordField;
    private final WebPasswordField userRetypePassField;
    private final WebTextField userPrintnameField;
    private final WebCheckBox userActiveField;
    private final WebCheckBoxList userRolesField;
    
    private User user;

    public UserWindow()
    {
        super();
        setTitle("Пользователь");
        
        idLabel = new WebLabel("ID");
        usernameLabel = new WebLabel("Имя пользователя");
        passwordLabel = new WebLabel("Пароль");
        retypeLabel = new WebLabel("Повторите пароль");
        printnameLabel = new WebLabel("Имя в чеке");
        rolesLabel = new WebLabel("Роли");

        userIdField = new WebLabel();
        usernameField = new WebTextField();
        userPasswordField = new WebPasswordField();
        userRetypePassField = new WebPasswordField();
        userPrintnameField = new WebTextField();
        userActiveField = new WebCheckBox("Вход разрешен");
        userRolesField = new WebCheckBoxList();
        
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10},
            {10, TableLayout.PREFERRED,                 // Id
             10, TableLayout.PREFERRED,                 // username, password
             10, TableLayout.PREFERRED,                 // printname, repeatPass
             10, TableLayout.PREFERRED,                 // roles, active
             10, TableLayout.FILL,                      // ...
             10, TableLayout.PREFERRED, 10}             // buttons
        });
        setLayout(layout);
        
        add(idLabel, "1, 1");
        add(userIdField, "3, 1");
        add(usernameLabel, "1, 3");
        add(usernameField, "3, 3");
        add(passwordLabel, "5, 3");
        add(userPasswordField, "7, 3");
        add(printnameLabel, "1, 5");
        add(userPrintnameField, "3, 5");
        add(retypeLabel, "5, 5");
        add(userRetypePassField, "7, 5");
        add(rolesLabel, "1, 7, L, T");
        add(new WebScrollPane(userRolesField), "3, 7");
        add(userActiveField, "7, 7, L, T");
        add(buttonsGroupPanel, "1, 11, 7, 11, C, T");
    }
    
    @Override
    public void showWindow(User user)
    {
        if(!actionListenersInit) setupActionListeners();
        
        this.user = user;
        setTitle("Пользователь - " + user.getUsername());
        
        userIdField.setText(user.getId() == null ? "" : user.getId().toString());
        usernameField.setText(user.getUsername());
        userPrintnameField.setText(user.getPrintName());
        userActiveField.setSelected(user.isActive());
        
        userPasswordField.clear();
        userRetypePassField.clear();
        
        userRolesField.getCheckBoxListModel().removeAllElements();
        
        for(Map.Entry<User.Role, String> entry : User.getAvaibleRoles().entrySet())
        {
            userRolesField.getCheckBoxListModel().addCheckBoxElementAt(entry.getKey().ordinal(), entry.getValue());
        }
        
        for(Map.Entry<User.Role, String> entry : User.getAvaibleRoles().entrySet())
        {
            if(user.hasRole(entry.getKey())) 
                userRolesField.getCheckBoxListModel().setCheckBoxSelected(entry.getKey().ordinal(), true);  
        }
        
        setVisible(true);
    }

    @Override
    public void setupActionListeners()
    {
        saveButton.addActionListener(saveUser);
        cancelButton.addActionListener(dontSaveUser);
        actionListenersInit = true;
    }

    public User getUser()
    {
        user.setUsername(usernameField.getText());
        user.setPrintName(userPrintnameField.getText());
        user.setActive(userActiveField.isSelected());
        
        String pass = new String(userPasswordField.getPassword());
        String retype = new String(userRetypePassField.getPassword());
        
        if(!pass.isEmpty() && !retype.isEmpty() && pass.equals(retype))
        {
            byte[] hash = DigestUtils.md5(pass);
            String hashString = Base64.encodeBase64String(hash);
            user.setPassword(hashString);
        }
        
        user.getRoles().clear();
        
        for(Object roleObj : userRolesField.getCheckedValues())
        { 
            for(Map.Entry<User.Role, String> entry : User.getAvaibleRoles().entrySet())
            {
                if(entry.getValue().equals(roleObj)) 
                    user.addRole(entry.getKey());
            }
        }
        
        return user;
    }
    
    public boolean isPasswordsSame()
    {
        String pass = new String(userPasswordField.getPassword());
        String retype = new String(userRetypePassField.getPassword());
        
        return pass.equals(retype);
    }

}
