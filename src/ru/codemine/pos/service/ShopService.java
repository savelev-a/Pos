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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.pos.application.Application;
import ru.codemine.pos.dao.ShopDAO;
import ru.codemine.pos.entity.Shop;
import ru.codemine.pos.exception.DuplicateDataException;

/**
 *
 * @author Alexander Savelev
 */

@Service
@Transactional
public class ShopService 
{
    @Autowired private Application application;
    @Autowired private ShopDAO shopDAO;
    
    public Shop getCurrentShop()
    {
        return application.getCurrentShop();
    }
    
    public void setCurrentShop(Shop shop)
    {
        application.setCurrentShop(shop);
    }
    
    public void create(Shop shop) throws DuplicateDataException
    {
        if(shop == null) return;
        
        if(shopDAO.getByName(shop.getName()) != null) throw new DuplicateDataException("Такой магазин уже существует", "Наименование", shop.getName());
        shopDAO.create(shop);
    }
    
    public void delete(Shop shop)
    {
        shopDAO.delete(shop);
    }
    
    public void update(Shop shop) throws DuplicateDataException
    {
        if(shop == null) return;
        Shop testName = shopDAO.getByName(shop.getName());
        if(testName != null && !testName.getId().equals(shop.getId())) throw new DuplicateDataException("Такой магазин уже существует", "Наименование", shop.getName());
        shopDAO.update(shop);
    }
    
    public Shop getByName(String name)
    {
        return shopDAO.getByName(name);
    }
    
    public List<Shop> getAll()
    {
        return shopDAO.getAll();
    }
    
    
}
