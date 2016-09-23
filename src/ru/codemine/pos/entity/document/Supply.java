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

import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ru.codemine.pos.entity.Shop;
import ru.codemine.pos.entity.Store;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "docSupplies")
public class Supply extends Document
{
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_shop", nullable = false)
    private Shop targetShop;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_store", nullable = false)
    private Store targetStore;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supply", cascade = CascadeType.ALL)
    private Set<SupplyLine> content;
    
    @Column(name = "doc_total", nullable = false)
    private Double documentTotal;
    
    @Column(name = "real_total", nullable = false)
    private Double realTotal;
    
    public Supply()
    {
        this.content = new LinkedHashSet<>();
        this.documentTotal = 0.0;
    }
    
    public Supply(Shop targetShop, Store targetStore)
    {
        this.targetShop = targetShop;
        this.targetStore = targetStore;
        this.content = new LinkedHashSet<>();
        this.documentTotal = 0.0;
    }

    public Shop getTargetShop()
    {
        return targetShop;
    }

    public void setTargetShop(Shop targetShop)
    {
        this.targetShop = targetShop;
    }

    public Store getTargetStore()
    {
        return targetStore;
    }

    public void setTargetStore(Store targetStore)
    {
        this.targetStore = targetStore;
    }

    public Set<SupplyLine> getContent()
    {
        return content;
    }

    public void setContent(Set<SupplyLine> content)
    {
        this.content = content;
    }

    public Double getDocumentTotal()
    {
        return documentTotal;
    }

    public void setDocumentTotal(Double documentTotal)
    {
        this.documentTotal = documentTotal;
    }

    public Double getRealTotal()
    {
        return realTotal;
    }

    public void setRealTotal(Double realTotal)
    {
        this.realTotal = realTotal;
    }

    @Override
    public String toString()
    {
        return "Supply{" + "targetShop=" + targetShop + ", targetStore=" + targetStore + ", documentTotal=" + documentTotal + ", realTotal=" + realTotal + '}';
    }
    
    

    @Override
    public EntityType getEntityType()
    {
        return EntityType.SUPPLY;
    }

}
