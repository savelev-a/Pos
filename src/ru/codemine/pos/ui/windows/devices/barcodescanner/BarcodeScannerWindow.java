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

package ru.codemine.pos.ui.windows.devices.barcodescanner;

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
import ru.codemine.pos.entity.device.BarcodeScannerDevice;
import ru.codemine.pos.ui.windows.GenericEntityWindow;
import ru.codemine.pos.ui.windows.devices.barcodescanner.listener.ChangeScannerType;
import ru.codemine.pos.ui.windows.devices.barcodescanner.listener.DontSaveBarcodeScanner;
import ru.codemine.pos.ui.windows.devices.barcodescanner.listener.SaveBarcodeScanner;

/**
 *
 * @author Alexander Savelev
 */

@Component
public class BarcodeScannerWindow extends GenericEntityWindow<BarcodeScannerDevice>
{
    @Autowired private SaveBarcodeScanner saveBarcodeScanner;
    @Autowired private DontSaveBarcodeScanner dontSaveBarcodeScanner;
    @Autowired private ChangeScannerType changeScannerType;
    
    private final WebLabel deviceTypeLabel;
    private final WebLabel portLabel;
    private final WebLabel speedLabel;
    private final WebLabel descrLabel;
    
    private final WebPanel deviceTypeDescrPanel;
    private final WebTextArea deviceTypeDescrArea;
    
    private final WebComboBox deviceTypeBox;
    private final WebTextField portField;
    private final WebTextField speedField;
    private final WebTextArea descriptionArea;
    
    private BarcodeScannerDevice device;
    
    public BarcodeScannerWindow()
    {
        super();
        setTitle("Кассовый аппарат");
        
        deviceTypeLabel      = new WebLabel("Тип сканера");
        portLabel         = new WebLabel("Порт");
        speedLabel        = new WebLabel("Скорость");
        descrLabel        = new WebLabel("Описание");
        
        deviceTypeDescrPanel = new WebPanel();
        deviceTypeDescrArea  = new WebTextArea();
    
        deviceTypeBox      = new WebComboBox();
        portField       = new WebTextField();
        speedField      = new WebTextField();
        descriptionArea = new WebTextArea();
        
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        
        deviceTypeDescrArea.setMargin(5);
        deviceTypeDescrArea.setWrapStyleWord(true);
        deviceTypeDescrArea.setLineWrap(true);
        deviceTypeDescrArea.setOpaque(false);
        deviceTypeDescrArea.setEditable(false);
        deviceTypeDescrArea.setFocusable(false);
        deviceTypeDescrPanel.setMargin(5);
        
        deviceTypeDescrPanel.setUndecorated(false);
        deviceTypeDescrPanel.setRound(StyleConstants.largeRound);
        deviceTypeDescrPanel.add(deviceTypeDescrArea);
        
         TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10, TableLayout.PREFERRED, 10, TableLayout.FILL, 10},
            {10, TableLayout.PREFERRED,                 // Type
             10, TableLayout.PREFERRED,                 // Type description
             10, TableLayout.PREFERRED,                 // port, speed
             10, TableLayout.FILL,                      // description
             10, TableLayout.PREFERRED, 10}             // buttons
        });
        setLayout(layout);
        
        add(deviceTypeLabel, "1, 1, 3, 1, F, F");
        add(deviceTypeBox, "3, 1, 7, 1, F, F");
        add(deviceTypeDescrPanel, "3, 3, 7, 3, F, F");
        add(portLabel, "1, 7");
        add(portField, "3, 7");
        add(speedLabel, "5, 7");
        add(speedField, "7, 7");
        add(descrLabel, "1, 11");
        add(new WebScrollPane(descriptionArea), "3, 11, 7, 11, F, F");
        add(buttonsGroupPanel, "1, 13, 7, 13, C, T");
        
        for(Map.Entry<BarcodeScannerDevice.BarcodeScannerType, String> type : BarcodeScannerDevice.getAvaibleTypes().entrySet())
        {
            deviceTypeBox.addItem(type.getValue());
        }

    }

    @Override
    public void showWindow(BarcodeScannerDevice device)
    {
        if(!actionListenersInit) setupActionListeners();
        
        this.device = device;
        setTitle("Сканер ШК - " + device.getName());
        
        String t = BarcodeScannerDevice.getAvaibleTypes().get(device.getType());
        deviceTypeBox.setSelectedItem(t);
        
        portField.setText(device.getPort());
        speedField.setText(device.getSpeed().toString());
        descriptionArea.setText(device.getDescription());
        
        setVisible(true);
    }

    @Override
    public void setupActionListeners()
    {
        saveButton.addActionListener(saveBarcodeScanner);
        cancelButton.addActionListener(dontSaveBarcodeScanner);
        deviceTypeBox.addActionListener(changeScannerType);
        actionListenersInit = true;
    }
    
    public BarcodeScannerDevice getDevice()
    {
        BarcodeScannerDevice.BarcodeScannerType type = BarcodeScannerDevice.BarcodeScannerType.KEYBOARD_SCANNER;
        for(Map.Entry<BarcodeScannerDevice.BarcodeScannerType, String> entry : BarcodeScannerDevice.getAvaibleTypes().entrySet())
        {
            if(entry.getValue().equals((String)deviceTypeBox.getSelectedItem())) type = entry.getKey();
        }
        device.setType(type);
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
        device.setDescription(descriptionArea.getText());
        
        return device;
    }
    
    public String getScannerTypeStr()
    {
        return (String)deviceTypeBox.getSelectedItem();
    }
    
    public void setScannerTypeDescription(String string)
    {
        deviceTypeDescrArea.setText(string);
    }
    
    public void setPortVisible(boolean b)
    {
        portLabel.setVisible(b);
        speedLabel.setVisible(b);
        portField.setVisible(b);
        speedField.setVisible(b);
    }

}
