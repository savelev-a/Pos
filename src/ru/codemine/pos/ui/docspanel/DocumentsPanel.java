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

package ru.codemine.pos.ui.docspanel;

import com.alee.extended.layout.TableLayout;
import com.alee.laf.panel.WebPanel;
import ru.codemine.pos.ui.docspanel.modules.ChequesPanel;
import ru.codemine.pos.ui.docspanel.modules.ProductsPanel;
import ru.codemine.pos.ui.docspanel.modules.StoresPanel;

/**
 *
 * @author Alexander Savelev
 */
public class DocumentsPanel extends WebPanel
{
    private final StoresPanel storesPanel;
    private final ChequesPanel chequesPanel;
    private final ProductsPanel productsPanel;
    
    public DocumentsPanel()
    {
        TableLayout layout = new TableLayout(new double[][]{
            {10, TableLayout.PREFERRED, 10, TableLayout.PREFERRED, 10},
            {10, TableLayout.PREFERRED, 
             10, TableLayout.PREFERRED, 
             10, TableLayout.PREFERRED, 
             10}
        });
        setLayout(layout);
        
        storesPanel = new StoresPanel();
        chequesPanel = new ChequesPanel();
        productsPanel = new ProductsPanel();
        
        add(storesPanel, "1, 1");
        add(chequesPanel, "1, 3");
        add(productsPanel, "1, 5");
    }
    
    public StoresPanel getStoresPanel()
    {
        return storesPanel;
    }
    
    public ChequesPanel getChequesPanel()
    {
        return chequesPanel;
    }
    
    public ProductsPanel getProductsPanel()
    {
        return productsPanel;
    }
}
