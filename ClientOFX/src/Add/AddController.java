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
package Add;

import MainPackage.Server;
import Miscellaneous.Item;
import Miscellaneous.Message;
import Miscellaneous.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddController {

    AddView aview;
    Item item;

//------------------------------------------------------------------------------
    public AddController(AddView aview, Item newitem, User user) {
        this.aview = aview;
        this.item = newitem;
        this.item.username = user.username;     /* seller's username stored in item to be sold */

        this.aview.addConfirmListener(new ConfirmListener());
        this.aview.addBackListener(new BackListener());
        this.aview.addCategoryListener(new CategoryListener());
    }
//------------------------------------------------------------------------------

    class ConfirmListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Message reply = new Message();
            /* user confirms details of new item */
            Item i = aview.getItemDetails();
            i.username = item.username; /* set correct seller username */

            int returnCode = Item.sanityCheckItem(i);

            if (returnCode == 0) {
                Message m = new Message();
                m.item = new Item(i);
                m.type = 4; // ADD
                Server.send(m);
                reply = Server.receive();
                returnCode = reply.response;
            }

            switch (returnCode) {
                case 0:
                    /* success - item added */
                    aview.success();
                    aview.dispose();
                    break;
                case 1:
                    /* failure - duplicate itemname */
                    aview.duplicateName();
                    break;
                case 2:
                    /* mandatory fields empty */
                    aview.fieldsEmpty();
                    break;
            }
        }
    }
//------------------------------------------------------------------------------

    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* Back to homepage */
            aview.dispose();
        }
    }
//------------------------------------------------------------------------------

    class CategoryListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* set a subcategory combo box according to category selected */
            aview.setSubcategory(aview.getSelectedCategory());
        }
    }

//------------------------------------------------------------------------------
}
