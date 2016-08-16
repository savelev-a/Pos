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

import java.util.List;
import org.joda.time.DateTime;
import ru.codemine.pos.entity.Workday;
import ru.codemine.pos.entity.document.Cheque;

/**
 *
 * @author Alexander Savelev
 */
public class SimpleXReportTemplate 
{

    private final Workday currentWorkday;
    private final List<Cheque> cheques;
    
    public SimpleXReportTemplate(Workday currentWorkday, List<Cheque> cheques)
    {
        this.currentWorkday = currentWorkday;
        this.cheques = cheques;
    }
    
    @Override
    public String toString()
    {
        String head = ""
                + "     ООО 'Рога И Копыта'    \n"
                + "ИНН 123456789 КПП 000000000 \n"
                + "----------------------------\n"
                + "                            \n"
                + " Отчет суточный без гашения \n"
                + "                            \n";
        
        String timeUser = formTab(DateTime.now().toString("dd.MM.YY HH:mm"), currentWorkday.getOpenUser().getUsername(), 28);
        String cheqNum = formTab("Продаж", String.valueOf(cheques.size()), 28);
        String cashbNum = formTab("Возвратов", "0", 28);
        
        Double salesVal = 0.0;
        for(Cheque cheque : cheques)
            salesVal += cheque.getSum();
        String sales = formTab("Выручка", String.valueOf(salesVal), 28);
        
        String total = formTab("Сменный итог", String.valueOf(salesVal), 28);
        
        String tail = ""
                + "----------------------------\n"
                + "                            \n";
        
        return head + timeUser + cheqNum + cashbNum + sales + total + tail;
    }
    
    private String formTab(String left, String right, int width)
    {
        String spaces = "";
        int spaceNum = width - left.length() - right.length();
        for(int i = 0; i < spaceNum; i++) spaces = spaces + " ";
        
        return left + spaces + right + "\n";
    }

}
