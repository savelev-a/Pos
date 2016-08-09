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

package ru.codemine.pos.exception;

import ru.codemine.pos.entity.Product;

/**
 *
 * @author Alexander Savelev
 */
public class NegativeQuantityOnDeactivateException extends Exception
{
    public NegativeQuantityOnDeactivateException()
    {
        super("Невозможно отменить проведение документа, так как не хватает товара на складе.");
    }
    
    public NegativeQuantityOnDeactivateException(String info)
    {
        super("Невозможно отменить проведение документа, так как не хватает товара на складе.\n" + info);
    }
    
    public NegativeQuantityOnDeactivateException(Product product, Integer inDoc, Integer inStore)
    {
        super("Невозможно отменить проведение документа, так как не хватает товара на складе.\n" 
                + "Артикул: " + product.getArtikul()
                + "\n Наименование: " + product.getName() 
                + "\n К удалению: " + inDoc 
                + "\n На складе: " + inStore);
    }
}
