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

package ru.codemine.pos.ui.windows.document.startbalances.listener;

import com.alee.laf.optionpane.WebOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.document.StartBalance;
import ru.codemine.pos.exception.ActiveDocumentEditException;
import ru.codemine.pos.exception.GeneralException;
import ru.codemine.pos.service.StartBalanceService;
import ru.codemine.pos.ui.windows.document.startbalances.StartBalanceWindow;
import ru.codemine.pos.ui.windows.document.startbalances.StartBalancesListWindow;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class SaveSb implements ActionListener
{
    @Autowired private StartBalanceWindow sbWindow;
    @Autowired private StartBalancesListWindow sblistWindow;
    @Autowired private StartBalanceService sbService;
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        StartBalance startBalance = sbWindow.getDocument();
        
        if(startBalance == null) return;
        
        if(startBalance.getId() == null)
        {
            try
            {
                sbService.create(startBalance);
            } 
            catch (GeneralException ex)
            {
                WebOptionPane.showMessageDialog(sbWindow, ex.getLocalizedMessage(), "Ошибка сохранения документа", WebOptionPane.ERROR_MESSAGE);
                return;            
            }
        }
        else
        {
            try
            {
                sbService.update(startBalance);
            } 
            catch (ActiveDocumentEditException ex)
            {
                WebOptionPane.showMessageDialog(sbWindow, ex.getLocalizedMessage(), "Ошибка сохранения документа", WebOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        sbWindow.setVisible(false);
        sblistWindow.refresh();
    }

}
