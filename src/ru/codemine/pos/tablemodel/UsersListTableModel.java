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

package ru.codemine.pos.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.entity.User;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class UsersListTableModel extends DefaultTableModel
{
    @Autowired private Application application;
    
    private List<User> users;
    private static final ImageIcon ICON_IMAGE = new ImageIcon("images/icons/default/16x16/user.png");

    public UsersListTableModel()
    {
        this.users = new ArrayList<>();
    }

    public UsersListTableModel(List<User> users)
    {
        this.users = users;
    }

    @Override
    public int getRowCount()
    {
        return users == null ? 0 : users.size();
    }

    @Override
    public int getColumnCount()
    {
        return 6; //icon, current, id, username, active, printname
    }

    @Override
    public String getColumnName(int column)
    {
        switch(column)
        {
            case 0 : return "";
            case 1 : return "";
            case 2 : return "ID";
            case 3 : return "Имя пользователя";
            case 4 : return "Вход разрешен";
            case 5 : return "Имя в чеке";
        }
        
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0 : return ImageIcon.class;
            case 1 : return String.class;
            case 2 : return String.class;
            case 3 : return String.class;
            case 4 : return String.class;
            case 5 : return String.class;
        }
        
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }

    @Override
    public Object getValueAt(int row, int column)
    {
        switch(column)
        {
            case 0 : return ICON_IMAGE;
            case 1 : return application.getActiveUser().equals(users.get(row)) ? "A" : " ";
            case 2 : return users.get(row).getId();
            case 3 : return users.get(row).getUsername();
            case 4 : return users.get(row).isActive() ? "Да" : "Нет";
            case 5 : return users.get(row).getPrintName();
        }
        
        return "";
    }
    
    public void setUsersList(List<User> users)
    {
        this.users = users;
    }
    
    public User getUserAt(int SelectedRow)
    {
        return users.get(SelectedRow);
    }
}
