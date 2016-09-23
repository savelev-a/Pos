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

package ru.codemine.pos.utils;

import com.fazecast.jSerialComm.SerialPort;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander Savelev
 */
public class SerialPortUtils 
{
    public static List<String> getSerialPortNames()
    {
        List<String> portNames = new ArrayList<>();
        SerialPort[] commPorts = SerialPort.getCommPorts();
        for(SerialPort port : commPorts)
        {
            portNames.add(port.getSystemPortName());
        }
        
        return portNames;
    }
    
    public static List<Integer> getSerialPortSpeeds()
    {
        List<Integer> speeds = new ArrayList<>();
        
        speeds.add(9600);
        speeds.add(19200);
        speeds.add(38400);
        speeds.add(57600);
        speeds.add(115200);
        
        return speeds;
    }

}
