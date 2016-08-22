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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "workdays")
public class Workday extends GenericEntity
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "openTime", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime openTime;
    
    @Column(name = "closeTime", nullable = true)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime closeTime;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_opener", nullable = false)
    private User openUser;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_closer", nullable = true)
    private User closeUser;
    
    @Column(name = "isOpen", nullable = false)
    private boolean open;
    
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public DateTime getOpenTime()
    {
        return openTime;
    }

    public void setOpenTime(DateTime openTime)
    {
        this.openTime = openTime;
    }

    public DateTime getCloseTime()
    {
        return closeTime;
    }

    public void setCloseTime(DateTime closeTime)
    {
        this.closeTime = closeTime;
    }

    public User getOpenUser()
    {
        return openUser;
    }

    public void setOpenUser(User openUser)
    {
        this.openUser = openUser;
    }

    public User getCloseUser()
    {
        return closeUser;
    }

    public void setCloseUser(User closeUser)
    {
        this.closeUser = closeUser;
    }

    public boolean isOpen()
    {
        return open;
    }

    public void setOpen(boolean open)
    {
        this.open = open;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final Workday other = (Workday) obj;

        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Workday{" + "id=" + id + ", openTime=" + openTime + ", closeTime=" + closeTime + ", open=" + open + '}';
    }

    @Override
    public EntityType getEntityType()
    {
        return EntityType.WORKDAY;
    }
    
    

}
