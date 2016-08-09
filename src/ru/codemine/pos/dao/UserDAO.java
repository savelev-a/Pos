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

package ru.codemine.pos.dao;

import java.util.List;
import ru.codemine.pos.entity.User;

/**
 *
 * @author Alexander Savelev
 */
public interface UserDAO extends GenericDAO<User, Integer>
{

    /**
     * Делает выборку из БД пользователя по его имени
     * @param username имя пользователя
     * @return
     */
    public User getByUsername(String username);

    /**
     * Делает выборку из БД списка незаблокированных пользователей
     * @return список пользователей
     */
    public List<User> getActive();
    /**
     * Делает выборку из БД списка всех пользователей
     * @return список пользователей
     */
    public List<User> getAll();

}
