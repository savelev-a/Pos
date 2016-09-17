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
@Table(name = "shops")
public class Shop extends GenericEntity
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "name", nullable = false, unique = true, length = 255)
    private String name;
    
    @Column(name = "address", nullable = false, columnDefinition = "TEXT")
    private String address;
    
    @Column(name = "orgName", nullable = false, length = 255)
    private String orgName;
    
    @Column(name = "orgInn", nullable = false, length = 12)
    private String orgInn;
    
    @Column(name = "orgKpp", nullable = false, length = 9)
    private String orgKpp;

    public Shop()
    {
        this.name = "Новый магазин";
        this.address = "";
        this.orgName = "";
        this.orgInn = "";
        this.orgKpp = "";
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

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getOrgName()
    {
        return orgName;
    }

    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

    public String getOrgInn()
    {
        return orgInn;
    }

    public void setOrgInn(String orgInn)
    {
        this.orgInn = orgInn;
    }

    public String getOrgKpp()
    {
        return orgKpp;
    }

    public void setOrgKpp(String orgKpp)
    {
        this.orgKpp = orgKpp;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.name);
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
        final Shop other = (Shop) obj;
        if (!Objects.equals(this.name, other.name))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Shop{" + "id=" + id + ", name=" + name + ", address=" + address + ", orgName=" + orgName + ", orgInn=" + orgInn + ", orgKpp=" + orgKpp + '}';
    }
    

    @Override
    public EntityType getEntityType()
    {
        return EntityType.SHOP;
    }
    
}
