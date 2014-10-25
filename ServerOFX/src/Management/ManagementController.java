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
package Management;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ManagementController {

    ServerSocket server;
    ArrayList<Client> AL;

    int PORT = 10000;

//------------------------------------------------------------------------------
    public ManagementController() throws IOException {
        server = new ServerSocket(PORT);
        AL = new ArrayList<>();
    }

//------------------------------------------------------------------------------
    public void listen() {
        Thread t = new Thread(new NewClient());
        t.start();
        View.log("INFO: Server listening for client connections on Port: " + PORT);
    }
//------------------------------------------------------------------------------

    class NewClient implements Runnable {

        /* Server listens for new clients indefinitely */
        @Override
        public void run() {
            while (true) {
                try {
                    Socket s = server.accept();
                    View.log("INFO: Client connected to server");

                    Client object = new Client(s);
                    object.oos = new ObjectOutputStream(s.getOutputStream());
                    object.ois = new ObjectInputStream(s.getInputStream());

                    new Thread(object).start();
                    AL.add(object);
                    View.log("INFO: Server connected to " + s.getRemoteSocketAddress());
                } catch (IOException e) {
                    View.log("ALERT: IOException while creating new Connection for client");
                }
            }
        }
    }
//------------------------------------------------------------------------------
}
