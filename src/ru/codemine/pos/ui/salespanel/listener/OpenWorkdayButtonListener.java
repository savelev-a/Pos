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
package ru.codemine.pos.ui.salespanel.listener;

import com.alee.laf.optionpane.WebOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.exception.GeneralException;
import ru.codemine.pos.exception.WorkdayAlreadyOpenedException;
import ru.codemine.pos.service.WorkdayService;
import ru.codemine.pos.ui.MainWindow;
import ru.codemine.pos.ui.salespanel.SalesPanel;
import ru.codemine.pos.ui.salespanel.modules.ButtonsPanel;

/**
 *
 * @author Alexander Savelev
 */
@Component
public class OpenWorkdayButtonListener implements ActionListener
{

    @Autowired private WorkdayService workdayService;
    @Autowired private SalesPanel salesPanel;
    @Autowired private ButtonsPanel buttonsPanel;
    @Autowired private MainWindow mainWindow;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            buttonsPanel.getOpenWorkdayButton().setEnabled(false);
            if (WebOptionPane.showConfirmDialog(mainWindow.getRootPane(),
                    "Открыть новую смену?", "Подтвердите действие",
                    WebOptionPane.YES_NO_OPTION, WebOptionPane.QUESTION_MESSAGE) == 0)
            {
                workdayService.openNewWorkday();
            }
            mainWindow.refreshStatus();
        } 
        catch (WorkdayAlreadyOpenedException | GeneralException ex)
        {
            WebOptionPane.showMessageDialog(mainWindow.getRootPane(), ex.getLocalizedMessage(), "Ошибка", WebOptionPane.WARNING_MESSAGE);
        } 
        finally
        {
            buttonsPanel.getOpenWorkdayButton().setEnabled(true);
            salesPanel.requestFocus();
        }
    }

}
