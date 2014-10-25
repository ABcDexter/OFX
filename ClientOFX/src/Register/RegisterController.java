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
package Register;

import Login.LoginController;
import Login.LoginView;
import MainPackage.Server;
import Miscellaneous.Message;
import Miscellaneous.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController {

    RegisterView rview;
//------------------------------------------------------------------------------

    public RegisterController(RegisterView rview) {
        this.rview = rview;
        this.rview.addCreateListener(new CreateListener());
        this.rview.addBackListener(new BackListener());
    }

//------------------------------------------------------------------------------
    class CreateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            User newuser = rview.getDetails();
            int returnCode = User.sanityCheckRegister(newuser);

            // if info is sane
            if (returnCode == 0) {
                Message m = new Message();
                m.user = newuser;
                m.type = 1; // REGISTER type
                Server.send(m);
                Message reply = Server.receive();

                returnCode = reply.response;
            }

            /* Analyze result codes of registration process */
            switch (returnCode) {
                case 0:
                    /* registration success */
                    rview.userCreated();
                    rview.dispose();

                    LoginView lview = new LoginView();
                    User usr = new User();
                    LoginController ctrl = new LoginController(lview, usr);
                    lview.setVisible(true);
                    break;
                case 1:
                    /* user already exists */
                    rview.userExists();
                    break;
                case 2:
                    /* mobile not in format */
                    rview.wrongMobile();
                    break;
                case 3:
                    /* short password */
                    rview.shortPass();
                    break;
                case 4:
                    /* empty fields */
                    rview.fieldsEmpty();
                    break;
            }
        }

    }

//------------------------------------------------------------------------------
    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* Back to Login screen */
            rview.dispose();
            LoginView lview = new LoginView();  // View
            User user = new User(); // Model

            LoginController lctrl = new LoginController(lview, user);   // Controller
            lview.setVisible(true);
        }

    }
//------------------------------------------------------------------------------
}
