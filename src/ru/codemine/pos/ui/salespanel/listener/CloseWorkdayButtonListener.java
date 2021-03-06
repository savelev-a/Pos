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
import ru.codemine.pos.exception.KkmException;
import ru.codemine.pos.service.WorkdayService;
import ru.codemine.pos.service.kkm.KkmService;
import ru.codemine.pos.ui.MainWindow;
import ru.codemine.pos.ui.salespanel.SalesPanel;
import ru.codemine.pos.ui.salespanel.modules.ButtonsPanel;

/**
 *
 * @author Alexander Savelev
 */
@Component
public class CloseWorkdayButtonListener implements ActionListener
{

    @Autowired private WorkdayService workdayService;
    @Autowired private KkmService kkmService;
    @Autowired private MainWindow mainWindow;
    @Autowired private SalesPanel salesPanel;
    @Autowired private ButtonsPanel buttonsPanel;

    @Override
    public void actionPerformed(ActionEvent e)
    {
        try
        {
            buttonsPanel.getCloseWorkdayButton().setEnabled(false);
            if (WebOptionPane.showConfirmDialog(mainWindow.getRootPane(),
                    "Напечатать Z-отчет?", "Подтвердите действие",
                    WebOptionPane.YES_NO_OPTION, WebOptionPane.QUESTION_MESSAGE) == 0)
            {
                kkmService.printZReport();
            }
            mainWindow.refreshStatus();
        } 
        catch (GeneralException | KkmException ex)
        {
            WebOptionPane.showMessageDialog(mainWindow.getRootPane(), ex.getLocalizedMessage(), "Ошибка", WebOptionPane.WARNING_MESSAGE);
        } 
        finally
        {
            buttonsPanel.getCloseWorkdayButton().setEnabled(true);
            salesPanel.requestFocus();
        }
    }

}
