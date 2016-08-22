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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "stores")
public class Store extends GenericEntity
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "name", nullable = false, unique = true, length = 255)
    private String name;
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "stocks", joinColumns = @JoinColumn(name = "id_store"))
    @Column(name = "quantity", nullable = false)
    @MapKeyJoinColumn(name = "id_product", referencedColumnName = "id")
    private Map<Product, Integer> stocks;
    
    public Store()
    {
        this.stocks = new HashMap<>();
    }
    
    public Store(String name)
    {
        this.name = name;
        this.stocks = new HashMap<>();
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Map<Product, Integer> getStocks()
    {
        return stocks;
    }

    public void setStocks(Map<Product, Integer> stocks)
    {
        this.stocks = stocks;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.name);
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
        final Store other = (Store) obj;
        
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString()
    {
        return "Store{" + "id=" + id + ", name=" + name + '}';
    }

    @Override
    public EntityType getEntityType()
    {
        return EntityType.STORE;
    }
    
    
}
