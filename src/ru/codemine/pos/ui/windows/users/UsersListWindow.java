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

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.table.TableColumnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.User;
import ru.codemine.pos.service.UserService;
import ru.codemine.pos.tablemodel.UsersListTableModel;
import ru.codemine.pos.ui.windows.document.GenericDocumentListWindow;
import ru.codemine.pos.ui.windows.users.listener.DeleteUser;
import ru.codemine.pos.ui.windows.users.listener.EditUser;
import ru.codemine.pos.ui.windows.users.listener.NewUser;
import ru.codemine.pos.ui.windows.users.listener.RefreshUsersList;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class UsersListWindow extends GenericDocumentListWindow
{
    
    @Autowired private UserService userService;
    @Autowired private UsersListTableModel tableModel;
    
    @Autowired private NewUser newUser;
    @Autowired private EditUser editUser;
    @Autowired private DeleteUser deleteUser;
    @Autowired private RefreshUsersList refreshUsersList;
    
    public UsersListWindow()
    {
        super();
        setTitle("Управление пользователями");
        menuItemProcess.setEnabled(false);
        menuItemUnprocess.setEnabled(false);
        toolButtonProcess.setEnabled(false);
        toolButtonUnprocess.setEnabled(false);
    }

    @Override
    public void showWindow()
    {
        List<User> users = userService.getAllUsers();
        
        tableModel.setUsersList(users);
        table.setModel(tableModel);
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setMaxWidth(10);
        columnModel.getColumn(1).setMaxWidth(10);
        
         if(!actionListenersInit) setupActionListeners();
        
        statusLabel.setText("Загружено " + users.size() + " строк");
        
        setupSorter();
        setVisible(true);
    }

    @Override
    public void setupActionListeners()
    {
        setNewActionListener(newUser);
        setEditActionListener(editUser);
        setDeleteActionListener(deleteUser);
        setRefreshActionListener(refreshUsersList);
        
        table.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                Point p = e.getPoint();
                int row = table.rowAtPoint(p);
                if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1)
                {
                    menuItemEdit.doClick();
                }
            }
        });
        
        actionListenersInit = true;
    }
    
    public User getSelectedUser()
    {
        if(table.getSelectedRow() == -1) return null;
        
        return tableModel.getUserAt(table.getSelectedRow());
    }


}
