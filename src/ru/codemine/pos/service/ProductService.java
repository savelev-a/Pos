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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.pos.dao.ProductDAO;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.exception.DuplicateProductDataException;

/**
 *
 * @author Alexander Savelev
 */

@Service
public class ProductService 
{
    @Autowired
    private ProductDAO productDAO;
    
    @Transactional
    public void create(Product product) throws DuplicateProductDataException
    {
        if(productDAO.getByArtikul(product.getArtikul()) != null) throw new DuplicateProductDataException("Артикул", product.getArtikul());
        if(productDAO.getByBarcode(product.getBarcode()) != null) throw new DuplicateProductDataException("Штрихкод", product.getBarcode());
        
        productDAO.create(product);
    }
    
    @Transactional
    public void delete(Product product)
    {
        productDAO.delete(product);
    }
    
    @Transactional
    public void update(Product product) throws DuplicateProductDataException
    {
        if(productDAO.getByArtikul(product.getArtikul()) != null) throw new DuplicateProductDataException("Артикул", product.getArtikul());
        if(productDAO.getByBarcode(product.getBarcode()) != null) throw new DuplicateProductDataException("Штрихкод", product.getBarcode());
        
        productDAO.update(product);
    }
    
    @Transactional
    public Product getById(Long id)
    {
        return productDAO.getById(id);
    }
    
    @Transactional
    public Product getByArtikul(String artikul)
    {
        return productDAO.getByArtikul(artikul);
    }
    
    @Transactional
    public Product getByBarcode(String barcode)
    {
        return productDAO.getByBarcode(barcode);
    }
}
