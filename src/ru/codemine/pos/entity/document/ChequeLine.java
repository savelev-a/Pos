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
@Table(name = "cdocCheque")
public class ChequeLine implements Serializable
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cheque", nullable = false)
    private Cheque cheque;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;
    
    @Column(name = "price", nullable = false)
    private Double price;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "lineTotal", nullable = false)
    private Double lineTotal;

    
    public ChequeLine()
    {
        this.price = 0.0;
        this.quantity = 0;
        this.lineTotal = 0.0;
    }
    
    public ChequeLine(Cheque cheque, Product product, Double price, Integer quantity)
    {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.cheque = cheque;
        
        recalculateLine();
    }
    
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
        recalculateLine();
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
        recalculateLine();
    }

    public Double getLineTotal()
    {
        recalculateLine();
        return lineTotal;
    }

    public void setLineTotal(Double lineTotal)
    {
        this.lineTotal = lineTotal;
    }

    public Cheque getCheque()
    {
        return cheque;
    }

    public void setCheque(Cheque cheque)
    {
        this.cheque = cheque;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.cheque);
        hash = 23 * hash + Objects.hashCode(this.product);
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
        final ChequeLine other = (ChequeLine) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        if (!Objects.equals(this.cheque, other.cheque))
        {
            return false;
        }
        return Objects.equals(this.product, other.product);
    }

    @Override
    public String toString()
    {
        if(cheque == null) 
            return "ChequeLine{" + "id=" + id + ", cheque=!null!, product=" + product + ", price=" + price + ", quantity=" + quantity + ", lineTotal=" + lineTotal + '}';
        return "ChequeLine{" + "id=" + id + ", chequeId=" + cheque.getId() + ", product=" + product + ", price=" + price + ", quantity=" + quantity + ", lineTotal=" + lineTotal + '}';
    }
    
    
    
    private void recalculateLine()
    {
        if(price != null && quantity != null)
            lineTotal = price * quantity;
    }
}
