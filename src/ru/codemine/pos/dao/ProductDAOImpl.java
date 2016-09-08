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
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import ru.codemine.pos.entity.Product;

/**
 *
 * @author Alexander Savelev
 */

@Repository
public class ProductDAOImpl extends GenericDAOImpl<Product, Long> implements ProductDAO
{

    @Override
    public Product getByArtikul(String artikul)
    {
        Query query = getSession().createQuery("FROM Product p WHERE p.artikul = :artikul");
        query.setString("artikul", artikul);
        
        return (Product)query.uniqueResult();
    }
    
    @Override
    public Product getByBarcode(String barcode)
    {
        Query query = getSession().createQuery("FROM Product p WHERE p.barcode = :barcode");
        query.setString("barcode", barcode);
        
        return (Product)query.uniqueResult();
    }

    @Override
    public List<Product> getAll()
    {
        Query query = getSession().createQuery("FROM Product");
        
        return query.list();
    }


}
