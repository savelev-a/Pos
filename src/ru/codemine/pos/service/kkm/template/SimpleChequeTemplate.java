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

import org.joda.time.DateTime;
import ru.codemine.pos.entity.document.Cheque;
import ru.codemine.pos.entity.document.ChequeLine;

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
                + formTab(cheque.getShop().getOrgName(), "", 28)
                + formTab("ИНН" + cheque.getShop().getOrgInn(), "КПП" + cheque.getShop().getOrgKpp(), 28)
                + "----------------------------\n"
                + "                            \n"
                + formTab(DateTime.now().toString("dd.MM.YY HH:mm"), "", 28)
                + formTab("", cheque.getCreator().getPrintName(), 28)
                + "                            \n"
                + "                            \n";

        for(ChequeLine line : cheque.getContent())
        {
            String productName = line.getProduct().getName();

            String priceStr = line.getPrice() + " руб.";
            String quantityStr = "x" + line.getQuantity();
            
            if(productName.length() > 14) productName = productName.substring(0, 13);
            
            itemStr = itemStr + formTab(productName, quantityStr + " " + priceStr, 28);

        }
        
        String sumStr = cheque.getChequeTotal()+ " руб.";

        String tail = "" 
                + "                            \n"
                + formTab("ИТОГО:", sumStr, 28)
                + "----------------------------\n"
                + "     Спасибо за покупку!    \n";
        
        return head + itemStr + tail;
        
    }
    
    private String formTab(String left, String right, int width)
    {
        String spaces = "";
        int spaceNum = width - left.length() - right.length();
        for(int i = 0; i < spaceNum; i++) spaces = spaces + " ";
        
        return left + spaces + right + "\n";
    }
}
