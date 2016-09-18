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

package ru.codemine.pos.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "products")
public class Product extends GenericEntity
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "artikul", nullable = false, unique = true, length = 128)
    private String artikul;
    
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    
    @Column(name = "barcode", nullable = false, unique = true, length = 32)
    private String barcode;
    
    @Column(name = "price", nullable = false, precision = 2)
    private Double price;

    
    public Product()
    {
        this.price = 0.0;
    }
    
    public Product(String artikul, String name, String barcode, Double price)
    {
        this.artikul = artikul;
        this.name = name;
        this.price = price;
        this.barcode = barcode;
    }
    
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getArtikul()
    {
        return artikul;
    }

    public void setArtikul(String artikul)
    {
        this.artikul = artikul;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public String getBarcode()
    {
        return barcode;
    }

    public void setBarcode(String barcode)
    {
        this.barcode = barcode;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.artikul);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Product other = (Product) obj;

        return Objects.equals(this.artikul, other.artikul);
    }

    @Override
    public String toString()
    {
        return "Product{" + "id=" + id + ", artikul=" + artikul + ", name=" + name + ", barcode=" + barcode + ", price=" + price + '}';
    }

    @Override
    public EntityType getEntityType()
    {
        return EntityType.PRODUCT;
    }
    
    
    

}
