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
package Account;

import Homepage.HomeView;
import Login.LoginController;
import Login.LoginView;
import MainPackage.Server;
import Miscellaneous.Message;
import Miscellaneous.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountController {

    AccountView aview;
    User user;
    HomeView home;
//------------------------------------------------------------------------------

    public AccountController(AccountView aview, User user, HomeView home) {
        this.aview = aview;
        this.user = user;
        this.home = home;

        this.aview.addBackListener(new BackListener());
        this.aview.addUpdateListener(new UpdateListener());
        this.aview.addDeleteListener(new DeleteListener());

        aview.setDetails(this.user);
    }
//------------------------------------------------------------------------------

    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* back to homepage */
            aview.dispose();
        }
    }
//------------------------------------------------------------------------------

    class UpdateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* update user information */
            User test = new User();
            aview.updateDetails(test);

            int returnCode = User.sanityCheckRegister(test);    // Same sanity check as used in registration

            switch (returnCode) {
                case 0:
                    /* new user info is sane */
                    aview.updateDetails(user);
                    Message m = new Message();
                    m.user = new User(user);
                    m.type = 7; // EDIT
                    Server.send(m);
                    aview.updateSuccess();
                    break;
                case 2:
                    /* incorrect mobile */
                    aview.wrongMobile();
                    break;
                case 3:
                    aview.shortPass();
                    break;
                case 4:
                    /* fields empty */
                    aview.fieldsEmpty();
                    break;
            }

        }
    }
//------------------------------------------------------------------------------

    class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            /* confirm, then delete account */
            boolean delete = aview.deleteConfirm();
            if (delete) {
                /* delete account */
                Message m = new Message();
                m.user = new User();
                m.user.username = user.username;
                m.type = 8;
                Server.send(m);

                /* get back to login screen */
                home.dispose();
                aview.dispose();
                LoginView lview = new LoginView();  // View
                User user = new User(); // Model

                LoginController lctrl = new LoginController(lview, user);   // Controller
                lview.setVisible(true);
            }
        }
    }
//------------------------------------------------------------------------------
}
