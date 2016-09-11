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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Alexander Savelev
 */

@Entity
@Table(name = "users")
public class User extends GenericEntity
{
    
    public enum Role
    {
        ROLE_SALESMAN,
        ROLE_SHOPADMIN,
        ROLE_SYSADMIN
    }
    
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "active", nullable = false)
    private boolean active;
    
    @Column(name = "printName", nullable = false)
    private String printName;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "id_user"))
    private Set<Role> roles;

    public User()
    {
        this.password = "";
        this.active = true;
        this.roles = new HashSet<>();
    }
    
    public User(String username, String printname)
    {
        this.username = username;
        this.printName = printname;
        this.password = "";
        this.active = true;
        this.roles = new HashSet<>();
    }
    
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public String getPrintName()
    {
        return printName;
    }

    public void setPrintName(String printName)
    {
        this.printName = printName;
    }

    public Set<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }
    
    public void addRole(Role role)
    {
        this.roles.add(role);
    }
    
    public void removeRole(Role role)
    {
        this.roles.remove(role);
    }
    
    public boolean hasRole(Role role)
    {
        return roles.contains(role);
    }
    
    public static Map<Role, String> getAvaibleRoles()
    {
        Map<Role, String> result = new LinkedHashMap<>();
        
        result.put(Role.ROLE_SALESMAN, "Кассир");
        result.put(Role.ROLE_SHOPADMIN, "Администратор магазина");
        result.put(Role.ROLE_SYSADMIN, "Системный администратор");
        
        return result;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.username);
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
        final User other = (User) obj;

        return Objects.equals(this.username, other.username);
    }

    @Override
    public String toString()
    {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", active=" + active + ", printName=" + printName + '}';
    }

    @Override
    public EntityType getEntityType()
    {
        return EntityType.USER;
    }
    
    
    
}
