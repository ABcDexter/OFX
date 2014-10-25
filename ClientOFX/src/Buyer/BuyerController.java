/**
 * *********************************************************************
 *
 * Copyright (C) 2014 Avaneesh Rastogi (rastogi.avaneesh (at) gmail (dot) com)
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see (http://www.gnu.org/licenses/).
 *
 **********************************************************************
 */
package Buyer;

import Miscellaneous.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuyerController {

    User user;  // buyer of selected item 
    BuyerView bview;

//------------------------------------------------------------------------------
    public BuyerController(BuyerView bview, User user) {
        this.bview = bview;
        this.user = user;

        this.bview.addBackListener(new BackListener());

        /* set the details of User who bought this item */
        this.bview.setDetails(this.user);
    }
//------------------------------------------------------------------------------

    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* back to homepage */
            bview.dispose();
        }
    }

//------------------------------------------------------------------------------
}
