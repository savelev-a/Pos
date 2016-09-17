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

package ru.codemine.pos.exception;

import ru.codemine.pos.entity.GenericEntity;
import ru.codemine.pos.entity.device.GenericDevice;

/**
 *
 * @author Alexander Savelev
 */
public class DuplicateDataException extends Exception
{
    
    public DuplicateDataException(String message, String fieldName, String fieldData)
    {
        super(message + "Совпадение по: " + fieldName + ", значение: " + fieldData);
    }
    
    private String formErrorStr(GenericEntity entity, String fieldName, String fieldData)
    {
        String errorStr;
        switch(entity.getEntityType())
        {
            case CHEQUE : 
                errorStr = "Такой чек уже существует.\n"; 
                break;
            case DEVICE :
                GenericDevice dev = (GenericDevice)entity;
                errorStr = "Такое устройство уже существует.\nТип устройства: " + dev.getDeviceType() + "\n";
                break;
            case PRODUCT : 
                errorStr = "В справочнике товара уже присутствует данный товар.\n";
                break;
            case SHOP :
                errorStr = "Такой магазин уже существует.\n";
                break;
            case STARTBALANCE :
                errorStr = "Такой документ уже существует.\n";
                break;
            case STORE : 
                errorStr = "Склад с такими параметрами уже существует!\n";
                break;
            case USER :
                errorStr = "Пользователь с такими данными уже существует. \n";
                break;
            case WORKDAY :
                errorStr = "Рабочая смена с такими данными уже существует. \n";
                break;
            default:
                errorStr = "Ошибка уникальности данных.\n";
        }
        errorStr += ("Совпадение по: " + fieldName + ", значение: " + fieldData);
        
        return errorStr;
    }

}
