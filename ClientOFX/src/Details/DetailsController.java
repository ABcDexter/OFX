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
package Details;

import MainPackage.Server;
import Miscellaneous.Item;
import Miscellaneous.Message;
import Miscellaneous.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetailsController {

    DetailsView dview;
    Item item;
    User user;  // buyer 
    User seller;    // seller
//------------------------------------------------------------------------------

    public DetailsController(DetailsView dview, Item item, User user) {
        this.dview = dview;
        this.item = item;
        this.user = user;   // buyer user

        this.dview.addBackListener(new BackListener());
        this.dview.addBuyListener(new BuyListener());
    }
//------------------------------------------------------------------------------

    public void init() {
        // this function is called after DetailsController object is created
        getSellerInfo();
        dview.updateDetails(this.item);
        dview.updateSellerDetails(this.seller);
    }
//------------------------------------------------------------------------------

    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* back to homepage */
            dview.dispose();
        }
    }
//------------------------------------------------------------------------------

    class BuyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* comsumer clicks on Buy item, confirm action */
            boolean confirm = dview.showConfirmation();

            if (confirm) {
                Message m = new Message();
                m.user = new User(user);
                m.item = new Item(item);
                m.type = 3; // BUY
                Server.send(m);

                Message reply = Server.receive();

                switch (reply.response) {
                    case 0:
                        /* success - item bought - update view */
                        dview.dealDone();
                        break;
                    case 1:
                        /* failure - someone bought item after user opened its details */
                        dview.dealFailure();
                        break;
                }
                dview.dispose();
            }
        }
    }
//------------------------------------------------------------------------------

    public void getSellerInfo() {
        User s = new User();
        s.username = item.username; // item.username contains seller's username

        Message m = new Message();
        Message reply = new Message();

        m.type = 12;
        m.user = s;
        while (reply.user == null) {
            Server.send(m); // sending seller username to server for more details
            reply = Server.receive();
        }
        this.seller = new User(reply.user); // store seller user in local data member
    }

//------------------------------------------------------------------------------
}
