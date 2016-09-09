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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.entity.Workday;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "cheques")
public class Cheque extends Document
{
    public enum PaymentType
    {
        CASH,
        CASHLESS
    }
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cheque", cascade = CascadeType.ALL)
    private Set<ChequeLine> content;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_workday", nullable = false)
    private Workday workday;
    
    @Column(name = "ptype", nullable = false)
    private PaymentType paymentType;
    
    @Column(name = "chequeTotal", nullable = false)
    private Double chequeTotal;
    
    public Cheque()
    {
        this.content = new LinkedHashSet<>();
        this.paymentType = PaymentType.CASH;
        this.chequeTotal = 0.0;
    }
    
    public Cheque(Workday wd)
    {
        this.content = new LinkedHashSet<>();
        this.paymentType = PaymentType.CASH;
        this.chequeTotal = 0.0;
        this.workday = wd;
    }

    public Set<ChequeLine> getContent()
    {
        return content;
    }

    public void setContent(Set<ChequeLine> content)
    {
        this.content = content;
        
        recalculateCheque();
    }

    public Workday getWorkday()
    {
        return workday;
    }

    public void setWorkday(Workday workday)
    {
        this.workday = workday;
    }
    
    public PaymentType getPaymentType()
    {
        return paymentType;
    }
    
    public void setPaymentType(PaymentType type)
    {
        this.paymentType = type;
    }

    public Double getChequeTotal()
    {
        return chequeTotal;
    }

    public void setChequeTotal(Double chequeTotal)
    {
        this.chequeTotal = chequeTotal;
    }

    @Override
    public String toString()
    {
        return "Cheque{" + "content=" + content + ", workday=" + workday + ", chequeTotal=" + chequeTotal + '}';
    }
    
    
    // Вычисляемые методы //
    
    public Integer getQuantityOf(Product product)
    {
        Integer quantity = 0;
        for(ChequeLine line : content)
        {
            if(product != null && product.equals(line.getProduct()))
            {
                quantity += line.getQuantity();
            }
        }
        
        return quantity;
    }
    
    
    public boolean hasProduct(Product product)
    {
        for(ChequeLine line : content)
        {
            if(line.getProduct() == product) return true;
        }
        
        return false;
    }
    
    public void addItem(Product product, Integer quantity)
    {
        if(product == null) return;
        
        boolean found = false;
        for(ChequeLine line : content)
        {
            if(product.equals(line.getProduct()) && !found)
            {
                line.setQuantity(line.getQuantity() + quantity);
                found = true;
            }
        }
        
        if(!found)
        {
            ChequeLine newLine = new ChequeLine(this, product, product.getPrice(), quantity);
            content.add(newLine);
        }

        recalculateCheque();
    }
    
    public void removeItem(Product product) //,Discount discount
    {
        Iterator<ChequeLine> iterator = content.iterator();
        while(iterator.hasNext())
        {
            ChequeLine line = iterator.next();
            if(line.getProduct() == product) // && discount == discount
            {
                iterator.remove();   
            }
        }
        
        recalculateCheque();
    }
    
    public void recalculateCheque()
    {
        if(content != null && !content.isEmpty())
        {
            chequeTotal = 0.0;
            for(ChequeLine line : content)
            {
                chequeTotal += line.getLineTotal();
            }
        }
    }
    
    public String getPaymentTypeString()
    {
        return getAvaiblePaymentTypes().get(paymentType);
    }
    
    public static Map<PaymentType, String> getAvaiblePaymentTypes()
    {
        Map<PaymentType, String> result = new LinkedHashMap<>();
        
        result.put(PaymentType.CASH, "Наличными");
        result.put(PaymentType.CASHLESS, "Безналично");
        
        return result;
    }

    @Override
    public EntityType getEntityType()
    {
        return EntityType.CHEQUE;
    }

}
