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
package Forgot;

import Login.LoginController;
import Login.LoginView;
import MainPackage.Server;
import Miscellaneous.Message;
import Miscellaneous.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForgotController {

    ForgotView fview;
    User user;  // user who has forgotten password

//------------------------------------------------------------------------------
    public ForgotController(ForgotView fview, User user) {
        this.fview = fview;
        this.user = user;   // object contains username of current user and security question

        this.fview.addBackListener(new BackListener());
        this.fview.addChangeListener(new ChangeListener());
        this.fview.addRecoverListener(new RecoverListener());
    }
//------------------------------------------------------------------------------

    public void setQuestion(String Q) {
        fview.setQuestion(Q);
    }

//------------------------------------------------------------------------------
    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* back to login page */
            fview.dispose();
            LoginView lview = new LoginView();  // View
            User user = new User(); // Model

            LoginController lctrl = new LoginController(lview, user);   // Controller
            lview.setVisible(true);
        }
    }
//------------------------------------------------------------------------------

    class ChangeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* set new password */
            int returnCode;
            Message reply = new Message();

            user.password = fview.getPassword();
            returnCode = User.sanityCheckPassword(user);

            if (returnCode == 0) {
                /* SET new password */
                Message m = new Message();
                m.user = new User();
                m.user.username = user.username;
                m.user.password = user.password;

                m.type = 2;  // FORGOT type
                m.message = "SET";
                Server.send(m);
                reply = Server.receive();
                returnCode = reply.response;    // will always be 0
            }

            switch (returnCode) {
                case 0:
                    /* password change success */
                    fview.passChanged();
                    fview.dispose();

                    LoginView lview = new LoginView();
                    User usr = new User();
                    LoginController ctrl = new LoginController(lview, usr);
                    lview.setVisible(true);

                    break;
                case 1:
                    /* short password */
                    fview.shortPass();
                    break;
            }
        }
    }
//------------------------------------------------------------------------------

    class RecoverListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* get answer from MVC:view, send to MVC:model for verification */
            int returnCode;
            Message reply = new Message();

            user.answer = fview.getAnswer();
            returnCode = User.sanityCheckAnswer(user);

            if (returnCode == 0) {
                /* VERIFY security answer */
                Message m = new Message();
                m.user = new User();
                m.user.username = user.username;
                m.user.answer = user.answer;

                m.type = 2;  // FORGOT type
                m.message = "VERIFY";
                Server.send(m);
                reply = Server.receive();
                returnCode = reply.response;
            }

            switch (returnCode) {
                case 0:
                    /* answer correct */
                    fview.rightAnswer();
                    break;
                case 1:
                    /* answer incorrect */
                    fview.wrongAnswer();
                    break;
                case 2:
                    /* empty answer */
                    fview.emptyAnswer();
                    break;
            }
        }
    }

//------------------------------------------------------------------------------
}
