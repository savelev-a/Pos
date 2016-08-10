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
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "cdocCheque", joinColumns = @JoinColumn(name = "id_cheque"))
    @Column(name = "quantity", nullable = false)
    @MapKeyJoinColumn(name = "id_product", referencedColumnName = "id")
    private Map<Product, Integer> contents;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_workday", nullable = false)
    private Workday workday;
    
    public Cheque()
    {
        this.contents = new HashMap<>();
    }
    
    public Cheque(Workday wd)
    {
        this.contents = new HashMap<>();
        this.workday = wd;
    }

    public Map<Product, Integer> getContents()
    {
        return contents;
    }

    public void setContents(Map<Product, Integer> contents)
    {
        this.contents = contents;
    }

    public Workday getWorkday()
    {
        return workday;
    }

    public void setWorkday(Workday workday)
    {
        this.workday = workday;
    }
    
    public Double getSum()
    {
        Double result = 0.0;
        for(Map.Entry<Product, Integer> entry : contents.entrySet())
        {
            result += entry.getKey().getPrice() * entry.getValue();
        }
        
        return result;
    }
    
    
}
