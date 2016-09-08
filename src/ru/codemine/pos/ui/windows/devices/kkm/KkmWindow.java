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

package ru.codemine.pos.ui.windows.devices.kkm;

import com.alee.extended.layout.TableLayout;
import com.alee.global.StyleConstants;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextArea;
import com.alee.laf.text.WebTextField;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codemine.pos.entity.device.KkmDevice;
import ru.codemine.pos.service.printer.PrinterService;
import ru.codemine.pos.ui.windows.GenericEntityWindow;
import ru.codemine.pos.ui.windows.devices.kkm.listener.ChangeKkmType;
import ru.codemine.pos.ui.windows.devices.kkm.listener.DontSaveKkmDevice;
import ru.codemine.pos.ui.windows.devices.kkm.listener.SaveKkmDevice;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class KkmWindow extends GenericEntityWindow<KkmDevice>
{
    @Autowired private SaveKkmDevice saveKkmDevice;
    @Autowired private DontSaveKkmDevice dontSaveKkmDevice;
    @Autowired private ChangeKkmType changeKkmType;
    
    private final WebLabel kkmTypeLabel;
    private final WebLabel snLabel;
    private final WebLabel knLabel;
    private final WebLabel portLabel;
    private final WebLabel speedLabel;
    private final WebLabel sysprinterLabel;
    private final WebLabel descrLabel;
    
    private final WebPanel kkmTypeDescrPanel;
    private final WebTextArea kkmTypeDescrArea;
    
    private final WebComboBox kkmTypeBox;
    private final WebTextField snField;
    private final WebTextField knField;
    private final WebTextField portField;
    private final WebTextField speedField;
    private final WebComboBox sysprinterBox;
    private final WebTextArea descriptionArea;
    
    private KkmDevice device;
    
    public KkmWindow()
    {
        super();
        setTitle("Кассовый аппарат");
        
        kkmTypeLabel      = new WebLabel("Тип ККМ");
        snLabel           = new WebLabel("Серийный номер");
        knLabel           = new WebLabel("Номер ККМ");
        portLabel         = new WebLabel("Порт");
        speedLabel        = new WebLabel("Скорость");
        sysprinterLabel   = new WebLabel("Принтер");
        descrLabel        = new WebLabel("Описание");
        
        kkmTypeDescrPanel = new WebPanel();
        kkmTypeDescrArea  = new WebTextArea();
    
        kkmTypeBox      = new WebComboBox();
        snField         = new WebTextField();
        knField         = new WebTextField();
        portField       = new WebTextField();
        speedField      = new WebTextField();
        sysprinterBox   = new WebComboBox();
        descriptionArea = new WebTextArea();
        
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        
        kkmTypeDescrArea.setMargin(5);
        kkmTypeDescrArea.setWrapStyleWord(true);
        kkmTypeDescrArea.setLineWrap(true);
        kkmTypeDescrArea.setOpaque(false);
        kkmTypeDescrArea.setEditable(false);
        kkmTypeDescrArea.setFocusable(false);
        kkmTypeDescrPanel.setMargin(5);
        
        kkmTypeDescrPanel.setUndecorated(false);
        kkmTypeDescrPanel.setRound(StyleConstants.largeRound);
        kkmTypeDescrPanel.add(kkmTypeDescrArea);
        
         TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10},
            {10, TableLayout.PREFERRED,                 // Type
             10, TableLayout.PREFERRED,                 // Type description
             10, TableLayout.PREFERRED,                 // sn, kn
             10, TableLayout.PREFERRED,                 // port, speed
             10, TableLayout.PREFERRED,                 // sysname, ...
             10, TableLayout.FILL,                      // description
             10, TableLayout.PREFERRED, 10}             // buttons
        });
        setLayout(layout);
        
        add(kkmTypeLabel, "1, 1, 3, 1, F, F");
        add(kkmTypeBox, "3, 1, 7, 1, F, F");
        add(kkmTypeDescrPanel, "3, 3, 7, 3, F, F");
        add(snLabel, "1, 5");
        add(snField, "3, 5");
        add(knLabel, "5, 5");
        add(knField, "7, 5");
        add(portLabel, "1, 7");
        add(portField, "3, 7");
        add(speedLabel, "5, 7");
        add(speedField, "7, 7");
        add(sysprinterLabel, "1, 9");
        add(sysprinterBox, "3, 9");
        add(descrLabel, "1, 11");
        add(new WebScrollPane(descriptionArea), "3, 11, 7, 11, F, F");
        add(buttonsGroupPanel, "1, 13, 7, 13, C, T");
        
        for(Map.Entry<KkmDevice.KkmType, String> type : KkmDevice.getAvaibleTypes().entrySet())
        {
            kkmTypeBox.addItem(type.getValue());
        }
        
        for(String printerName : PrinterService.getAvaiblePrinterNames())
        {
            sysprinterBox.addItem(printerName);
        }
    }

    @Override
    public void showWindow(KkmDevice device)
    {
        if(!actionListenersInit) setupActionListeners();
        
        this.device = device;
        setTitle("Кассовый аппарат - " + device.getName() + " (№ " + device.getSerialNumber() + ")");
        
        String t = KkmDevice.getAvaibleTypes().get(device.getType());
        kkmTypeBox.setSelectedItem(t);
        
        snField.setText(device.getSerialNumber());
        knField.setText(device.getKkmNumber());
        portField.setText(device.getPort());
        speedField.setText(device.getSpeed().toString());
        sysprinterBox.setSelectedItem(device.getSysprinter());
        descriptionArea.setText(device.getDescription());
        
        snField.setEditable(device.getId() == null);
        
        
        setVisible(true);
    }

    @Override
    public void setupActionListeners()
    {
        saveButton.addActionListener(saveKkmDevice);
        cancelButton.addActionListener(dontSaveKkmDevice);
        kkmTypeBox.addActionListener(changeKkmType);
        actionListenersInit = true;
    }
    
    public KkmDevice getKkmDevice()
    {
        KkmDevice.KkmType type = KkmDevice.KkmType.SYSLOG_PRINTER;
        for(Map.Entry<KkmDevice.KkmType, String> entry : KkmDevice.getAvaibleTypes().entrySet())
        {
            if(entry.getValue().equals((String)kkmTypeBox.getSelectedItem())) type = entry.getKey();
        }
        device.setType(type);
        device.setSerialNumber(snField.getText());
        device.setKkmNumber(knField.getText());
        device.setPort(portField.getText());
        try
        {
            device.setSpeed(Integer.parseInt(speedField.getText()));
        } 
        catch (NumberFormatException ex)
        {
            WebOptionPane.showMessageDialog(rootPane, "Скорость должна быть целым числом", "Неверный формат числа", WebOptionPane.ERROR_MESSAGE);
            return null;
        }
        device.setSysprinter((String)sysprinterBox.getSelectedItem());
        device.setDescription(descriptionArea.getText());
        
        return device;
    }

    public String getKkmTypeStr()
    {
        return (String)kkmTypeBox.getSelectedItem();
    }

    public void setKkmDescription(String string)
    {
        kkmTypeDescrArea.setText(string);
    }

    public void setPortVisible(boolean b)
    {
        portLabel.setVisible(b);
        speedLabel.setVisible(b);
        portField.setVisible(b);
        speedField.setVisible(b);
    }

    public void setSysnameVisible(boolean b)
    {
        sysprinterLabel.setVisible(b);
        sysprinterBox.setVisible(b);
    }

}
