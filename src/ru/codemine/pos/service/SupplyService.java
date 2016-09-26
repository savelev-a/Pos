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

package ru.codemine.pos.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codemine.pos.dao.document.SupplyDAO;
import ru.codemine.pos.entity.document.Supply;
import ru.codemine.pos.entity.document.SupplyLine;
import ru.codemine.pos.exception.ActiveDocumentEditException;

/**
 *
 * @author Alexander Savelev
 */

@Service
@Transactional
public class SupplyService 
{
    @Autowired private SupplyDAO supplyDAO;
    
    public void create(Supply supply)
    {
        supply.setProcessed(false);
        supply.recalculateTotals();
        
        supplyDAO.create(supply);
    }
    
    public void delete(Supply supply) 
    {
        supplyDAO.delete(supply);
    }
    
    public void update(Supply supply) throws ActiveDocumentEditException
    {
        if(supply.isProcessed()) throw new ActiveDocumentEditException();
        
        supply = supplyDAO.unproxyContents(supply);
        supply.recalculateTotals();
        
        supplyDAO.update(supply);
    }
    
    public Supply getById(Long id)
    {
        return supplyDAO.getById(id);
    }
    
    public List<Supply> getAll()
    {
        return supplyDAO.getAll();
    }
    
    public void process(Supply supply)
    {
        //TODO process
    }
    
    public void unprocess(Supply supply)
    {
        //TODO unprocess
    }
    
    public Supply unproxyContent(Supply supply)
    {
        return supplyDAO.unproxyContents(supply);
    }
}
