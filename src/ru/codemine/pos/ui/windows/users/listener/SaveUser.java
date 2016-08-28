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

package ru.codemine.pos.ui.windows.users.listener;

import com.alee.laf.optionpane.WebOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.User;
import ru.codemine.pos.exception.DuplicateUserDataException;
import ru.codemine.pos.service.UserService;
import ru.codemine.pos.ui.windows.users.UserWindow;
import ru.codemine.pos.ui.windows.users.UsersListWindow;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class SaveUser implements ActionListener
{
    @Autowired private UsersListWindow usersListWindow;
    @Autowired private UserWindow userWindow;
    @Autowired private UserService userService;
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(!userWindow.isPasswordsSame())
        {
            WebOptionPane.showMessageDialog(userWindow, "Пароли не совпадают!\n"
                    + "Если не хотите менять пароль - просто оставьте поле пустым", "Ошибка", WebOptionPane.WARNING_MESSAGE);
            return;
        }
        
        User user = userWindow.getUser();
        if(user.getId() == null)
        {
            try
            {
                userService.create(user);
            } 
            catch (DuplicateUserDataException ex)
            {
                WebOptionPane.showMessageDialog(userWindow, ex.getLocalizedMessage(), "Ошибка сохранения пользователя", WebOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else
        {
            try
            {
                userService.update(user);
            } 
            catch (DuplicateUserDataException ex)
            {
                WebOptionPane.showMessageDialog(userWindow, ex.getLocalizedMessage(), "Ошибка сохранения пользователя", WebOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        userWindow.setVisible(false);
        usersListWindow.refresh();
    }

}
