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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.document.StartBalance;
import ru.codemine.pos.exception.DuplicateProcessedDocumentException;
import ru.codemine.pos.exception.GeneralException;
import ru.codemine.pos.exception.NegativeQuantityOnDeactivateException;
import ru.codemine.pos.service.StartBalanceService;
import ru.codemine.pos.ui.windows.document.startbalances.StartBalancesListWindow;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class UnprocessSb implements ActionListener
{
    @Autowired private StartBalanceService sbservice;
    @Autowired private StartBalancesListWindow window;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        StartBalance sb = window.getSelectedDocument();
        if(sb != null)
        {
            if(!sb.isProcessed())
            {
                WebOptionPane.showMessageDialog(window, "Этот документ не проведен", "Ошибка", WebOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try
            {
                sbservice.unprocess(sb);
            } 
            catch (GeneralException | NegativeQuantityOnDeactivateException ex)
            {
                WebOptionPane.showMessageDialog(window, ex.getLocalizedMessage(), "Ошибка", WebOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            WebOptionPane.showMessageDialog(window, "Не выбран документ для проведения!", "Ошибка", WebOptionPane.WARNING_MESSAGE);
        }
        
        window.refresh();
    }

}
