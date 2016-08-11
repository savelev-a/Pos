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

package ru.codemine.pos.service.kkm.template;

import java.util.Map;
import ru.codemine.pos.entity.Product;
import ru.codemine.pos.entity.document.Cheque;

/**
 *
 * @author Alexander Savelev
 */
public class SimpleChequeTemplate 
{
    private final Cheque cheque;
    
    public SimpleChequeTemplate(Cheque cheque)
    {
        this.cheque = cheque;
    }
    
    @Override
    public String toString()
    {
        String itemStr = "";
        String head = ""
                + "     ООО 'Рога И Копыта'    \n"
                + "ИНН 123456789 КПП 000000000 \n"
                + "----------------------------\n"
                + "                            \n";
        for(Map.Entry<Product, Integer> entry : cheque.getContents().entrySet())
        {
            String productName = entry.getKey().getName();
            String spaces = "";
            String priceStr = entry.getKey().getPrice() + " руб.";
            String quantityStr = "x" + entry.getValue();
            
            if(productName.length() > 14) productName = productName.substring(0, 13);
            int spaceNum = 29 - (productName.length() + quantityStr.length() + priceStr.length() + 2);
            for(int i = 0; i < spaceNum; i++) spaces = spaces + " ";
            
            itemStr = itemStr + productName + spaces + quantityStr + " " + priceStr + "\n";

        }
        
        String itogo = "ИТОГО:";
        String spaces = "";
        String sumStr = cheque.getSum() + " руб.";
        int spaceNum = 28 - (itogo.length() + sumStr.length());
        
        for(int i = 0; i < spaceNum; i++) spaces = spaces + " ";
        String tail = "" 
                + "                            \n"
                + itogo + spaces + sumStr +   "\n"
                + "----------------------------\n"
                + "     Спасибо за покупку!    \n";
        
        return head + itemStr + tail;
        
    }
}
