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

package ru.codemine.pos.dao.document;

import java.util.List;
import ru.codemine.pos.dao.GenericDAO;
import ru.codemine.pos.entity.Store;
import ru.codemine.pos.entity.document.StartBalance;

/**
 *
 * @author Alexander Savelev
 */
public interface StartBalanceDAO extends GenericDAO<StartBalance, Long>
{

    /**
     * Загружает из БД проведенный документ начальных остатки по заданному складу
     * @param store склад, остатки которого загружать
     * @return документ начальных остатков
     */
    public StartBalance getByStore(Store store);
    
    /**
     * Загружает из БД все начальные остатки по заданному складу (проведенные и нет)
     * @param store склад, остатки которого загружать
     * @return список документов начальных остатков
     */
    public List<StartBalance> getAllByStore(Store store);
    
    /**
     * Загружает из БД список всех начальных остатков
     * @return список документов начальных остатков
     */
    public List<StartBalance> getAll();
    
    /**
     * Депроксирует и загружает данные по содержимому документа
     * @param sb Документ начальных остатков, содержимое которого нужно депроксировать
     * @return документ с депроксированным содержимым
     */
    public StartBalance unproxyContents(StartBalance sb);
}
