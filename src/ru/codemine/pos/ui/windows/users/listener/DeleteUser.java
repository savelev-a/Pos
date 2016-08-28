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
import com.alee.laf.rootpane.WebRootPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.entity.User;
import ru.codemine.pos.service.UserService;
import ru.codemine.pos.ui.windows.users.UsersListWindow;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class DeleteUser implements ActionListener
{
    @Autowired private Application application;
    @Autowired private UserService userService;
    @Autowired private UsersListWindow window;
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        User user = window.getSelectedUser();
        
        if(application.getActiveUser().equals(window.getSelectedUser()))
        {
            WebOptionPane.showMessageDialog(window, "Невозможно удалить самого себя!", "Ошибка", WebOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if(user != null)
        {
            try
            {
                userService.delete(user);
            } 
            catch (DataIntegrityViolationException ex)
            {
                WebOptionPane.showMessageDialog(window, "Невозможно удалить пользователя который пробивал чеки, \n"
                            + "либо создавал/принимал документы!", "Ошибка", WebOptionPane.WARNING_MESSAGE);
            }
            
        }
        else
        {
            WebOptionPane.showMessageDialog(window, "Не выбран пользователь!", "Ошибка", WebOptionPane.WARNING_MESSAGE);
        }
        
        window.refresh();
        
    }

}
