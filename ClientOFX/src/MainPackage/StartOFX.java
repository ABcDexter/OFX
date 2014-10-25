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
package MainPackage;

import Login.LoginController;
import Login.LoginView;
import Miscellaneous.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class StartOFX {

    static int PORT = 10000;

    public static void main(String args[]) throws IOException {

        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Server.s = new Socket("localhost", PORT);
        Server.oos = new ObjectOutputStream(Server.s.getOutputStream());
        Server.ois = new ObjectInputStream(Server.s.getInputStream());

        LoginView lview = new LoginView();  // View
        User user = new User(); // Model

        LoginController lctrl = new LoginController(lview, user);   // Controller
        lview.setVisible(true);
        // main method ends soon after making login screen visible
    }
}
