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

package ru.codemine.pos.entity.document;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import org.joda.time.DateTime;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.entity.Store;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "docStartBalance")
public class StartBalance extends Document
{
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "cdocStartBalance", joinColumns = @JoinColumn(name = "id_doc"))
    @Column(name = "quantity", nullable = false)
    @MapKeyJoinColumn(name = "id_product", referencedColumnName = "id")
    private Map<Product, Integer> content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_store", nullable = false)
    private Store store;
    
    @Column(name = "total", nullable = false)
    private Double total;
    
    public StartBalance()
    {
        content = new HashMap<>();
        total = 0.0;
    }
    
    public StartBalance(Store store)
    {
        this.store = store;
        content = new HashMap<>();
        total = 0.0;
    }
    
    public Map<Product, Integer> getContent()
    {
        return content;
    }

    public void setContent(Map<Product, Integer> content)
    {
        this.content = content;
    }

    public Store getStore()
    {
        return store;
    }

    public void setStore(Store store)
    {
        this.store = store;
    }

    public Double getTotal()
    {
        return total;
    }

    public void setTotal(Double total)
    {
        this.total = total;
    }
    
    public void calculateTotals()
    {
        total = 0.0;
        
        for (Map.Entry<Product, Integer> entrySet : content.entrySet())
        {
            Product product = entrySet.getKey();
            Integer quantity = entrySet.getValue();
            
            total += product.getPrice() * quantity;
        }
    }

    @Override
    public String toString()
    {
        return "StartBalance{" + ", store=" + store + ", total=" + total + '}';
    }

    @Override
    public EntityType getEntityType()
    {
        return EntityType.STARTBALANCE;
    }
    
    
    
}
