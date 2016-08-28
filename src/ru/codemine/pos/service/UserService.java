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

package ru.codemine.pos.service;

import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.pos.dao.UserDAO;
import ru.codemine.pos.entity.User;
import ru.codemine.pos.exception.DuplicateUserDataException;

/**
 *
 * @author Alexander Savelev
 */

@Service
public class UserService 
{
    private static final Logger log = Logger.getLogger("UserService");
    
    @Autowired
    private UserDAO userDAO;
    
    /**
     * Создает нового пользователя и записывает его в БД
     * @param user
     * @throws ru.codemine.pos.exception.DuplicateUserDataException
     */
    @Transactional
    public void create(User user) throws DuplicateUserDataException
    {
        if(user == null) return;
        
        byte[] hash = DigestUtils.md5(user.getPassword());
        user.setPassword(Base64.encodeBase64String(hash));
        
        if(userDAO.getByUsername(user.getUsername()) != null) throw new DuplicateUserDataException();
        
        userDAO.create(user);
    }
    
    /**
     * Удаляет из БД указанного пользователя
     * @param user
     */
    @Transactional
    public void delete(User user)
    {
        userDAO.delete(user);
    }
    
    /**
     * Обновляет пользователя в БД
     * @param user
     * @throws ru.codemine.pos.exception.DuplicateUserDataException
     */
    @Transactional
    public void update(User user) throws DuplicateUserDataException
    {
        if(user == null) return;
        
        User testName = userDAO.getByUsername(user.getUsername());
        if(testName != null && !testName.getId().equals(user.getId())) throw new DuplicateUserDataException();
        
        userDAO.evict(testName);
        
        userDAO.update(user);
    }
    
    /**
     * Возвращает пользователя по его имени
     * @param username имя пользователя
     * @return
     */
    @Transactional
    public User getByUsername(String username)
    {
        return userDAO.getByUsername(username);
    }
    
    /**
     * Возвращает список незаблокированных пользователей
     * @return
     */
    @Transactional
    public List<User> getActive()
    {
        return userDAO.getActive();
    }
    
    /**
     * Возвращает список всех пользователей
     * @return список пользователей
     */
    @Transactional
    public List<User> getAllUsers()
    {
        return userDAO.getAll();
    }
    
    /**
     * Выполняет валидность заданной пары логин/пароль
     * @param username логин
     * @param password пароль
     * @return
     */
    @Transactional
    public boolean performLogin(String username, String password)
    {
        byte[] hash = DigestUtils.md5(password);
        String hashString = Base64.encodeBase64String(hash);
        
        User user = userDAO.getByUsername(username);
        
        return user != null && user.getPassword().equals(hashString) && user.isActive() == true;
    }
}
