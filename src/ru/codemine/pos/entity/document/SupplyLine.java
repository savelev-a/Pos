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

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import ru.codemine.pos.entity.Product;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "cdocSupply")
public class SupplyLine implements Serializable
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_supply", nullable = false)
    private Supply supply;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;
    
    @Column(name = "price", nullable = false)
    private Double price;
    
    @Column(name = "doc_quantity", nullable = false)
    private Integer documentQuantity;
    
    @Column(name = "real_quantity", nullable = false)
    private Integer realQuantity;
    
    @Column(name = "doc_lineTotal", nullable = false)
    private Double documentLineTotal;
    
    @Column(name = "real_lineTotal", nullable = false)
    private Double realLineTotal;

    public SupplyLine()
    {
        this.price = 0.0;
        this.documentQuantity = 0;
        this.realQuantity = 0;
        this.documentLineTotal = 0.0;
        this.realLineTotal = 0.0;
    }

    public SupplyLine(Supply supply, Product product, Double price, Integer quantity)
    {
        this.supply = supply;
        this.product = product;
        this.price = price;
        this.documentQuantity = quantity;
        this.realQuantity = 0;
        this.documentLineTotal = price * quantity;
        this.realLineTotal = 0.0;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Supply getSupply()
    {
        return supply;
    }

    public void setSupply(Supply supply)
    {
        this.supply = supply;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Integer getDocumentQuantity()
    {
        return documentQuantity;
    }

    public void setDocumentQuantity(Integer documentQuantity)
    {
        this.documentQuantity = documentQuantity;
    }

    public Integer getRealQuantity()
    {
        return realQuantity;
    }

    public void setRealQuantity(Integer realQuantity)
    {
        this.realQuantity = realQuantity;
    }

    public Double getDocumentLineTotal()
    {
        return documentLineTotal;
    }

    public void setDocumentLineTotal(Double documentLineTotal)
    {
        this.documentLineTotal = documentLineTotal;
    }

    public Double getRealLineTotal()
    {
        return realLineTotal;
    }

    public void setRealLineTotal(Double realLineTotal)
    {
        this.realLineTotal = realLineTotal;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.supply);
        hash = 53 * hash + Objects.hashCode(this.product);
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
        final SupplyLine other = (SupplyLine) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        if (!Objects.equals(this.supply, other.supply))
        {
            return false;
        }
        if (!Objects.equals(this.product, other.product))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        if(supply == null)
            return "SupplyLine{" + "id=" + id + ", supply=!null!" + ", product=" + product + ", price=" + price + ", documentQuantity=" + documentQuantity + ", realQuantity=" + realQuantity + ", documentLineTotal=" + documentLineTotal + ", realLineTotal=" + realLineTotal + '}';
        return "SupplyLine{" + "id=" + id + ", supply=" + supply + ", product=" + product + ", price=" + price + ", documentQuantity=" + documentQuantity + ", realQuantity=" + realQuantity + ", documentLineTotal=" + documentLineTotal + ", realLineTotal=" + realLineTotal + '}';
    }
    
    

}
