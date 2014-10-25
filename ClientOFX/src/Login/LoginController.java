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
package Login;

import Forgot.ForgotController;
import Forgot.ForgotView;
import Homepage.Home;
import Homepage.HomeController;
import Homepage.HomeView;
import MainPackage.Server;
import Miscellaneous.Message;
import Miscellaneous.User;
import Register.RegisterController;
import Register.RegisterView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

    User user;  // Model
    LoginView lview;    // View

//------------------------------------------------------------------------------
    public LoginController(LoginView lview, User user) {
        this.user = user;
        this.lview = lview;

        this.lview.addLoginListener(new LoginListener());
        this.lview.addForgotListener(new ForgotListener());
        this.lview.addRegisterListener(new RegisterListener());
    }

//------------------------------------------------------------------------------
    class LoginListener implements ActionListener {
        /* user clicks on Login */

        @Override
        public void actionPerformed(ActionEvent e) {
            Message reply = new Message();
            Message m = new Message();

            User login = new User();
            login.username = lview.getUsername();
            login.password = lview.getPassword();
            int returnCode = User.sanityCheckLogin(login);

            // if info is sane
            while (returnCode == 0 && reply.user == null) {

                m.user = new User(login);
                m.type = 0; // LOGIN type
                Server.send(m);
                reply = Server.receive();

                returnCode = reply.response;
            }

            /* Analyze result codes of authentication process */
            switch (returnCode) {
                case 0:
                    /* login success: show homepage */
                    HomeView hview = new HomeView();
                    Home home = new Home();

                    HomeController hctrl = new HomeController(hview, home, reply.user);
                    hctrl.addRecentItems(reply.list);
                    hctrl.addReviewItems(reply.list);
                    hctrl.addNotifications(reply.notif);

                    hview.setVisible(true);

                    lview.dispose();
                    break;
                case 1:
                    /* password incorrect */
                    lview.wrongPasswordGiven();
                    break;
                case 2:
                    /* username doesn't exist in DB */
                    lview.wrongUsernameGiven();
                    break;
                case 3:
                    /* empty password */
                    lview.emptyPasswordGiven();
                    break;
                case 4:
                    /* empty username */
                    lview.emptyUsernameGiven();
                    break;
            }
        }
    }

//------------------------------------------------------------------------------
    class ForgotListener implements ActionListener {
        /* user clicks on forgot password */

        @Override
        public void actionPerformed(ActionEvent e) {
            int returnCode;
            Message reply = null;

            User fuser = new User();
            fuser.username = lview.getUsername();

            returnCode = User.sanityCheckForgot(fuser);

            if (returnCode == 0) {
                /* GET security question */
                Message m = new Message();
                m.user = fuser;
                m.type = 2;  // FORGOT type
                m.message = "GET";
                Server.send(m);
                reply = Server.receive();
                returnCode = reply.response;
            }

            switch (returnCode) {
                case 0:
                    /* username exists, proceed */
                    lview.dispose();
                    fuser.question = reply.user.question;
                    ForgotView fview = new ForgotView(null, true);
                    ForgotController fctrl = new ForgotController(fview, fuser);    // fuser contains username and security ques only
                    fctrl.setQuestion(reply.user.question);
                    fview.setVisible(true);

                    break;
                case 1:
                    /* username doesn't exist in DB */
                    lview.wrongUsernameGiven();
                    break;
                case 2:
                    /* empty username */
                    lview.emptyUsernameGiven();
                    break;
            }

        }

    }

//------------------------------------------------------------------------------
    class RegisterListener implements ActionListener {
        /* user clicks on Register button */

        @Override
        public void actionPerformed(ActionEvent e) {
            lview.dispose();
            RegisterView rview = new RegisterView(null, true);
            RegisterController rctrl = new RegisterController(rview);
            rview.setVisible(true);
        }

    }
//------------------------------------------------------------------------------
}
