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

import Miscellaneous.Message;
import Miscellaneous.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {

    public Socket s;
    User u;

    boolean loggedIn;

    public ObjectInputStream ois;
    public ObjectOutputStream oos;
//------------------------------------------------------------------------------

    Client(Socket s) {
        this.s = s;
        this.loggedIn = false;
        Database.init();
    }

//------------------------------------------------------------------------------
    @Override
    public void run() {
        Message m, reply;
        int type = -1, returnCode;

        while (true) {

            try {
                /* read client message = m*/
                m = (Message) ois.readObject();
                type = m.getType();
                View.log("INFO: Message received; Type: " + type);

            } catch (IOException | ClassNotFoundException ex) {
                View.log("ALERT: Connection with client broken unexpectedly");
                return;
            }

            /* Server's reply to client */
            reply = new Message();

            if (!loggedIn) {     // LOGIN / REGISTER / FORGOT case

                switch (type) {
                    case 0:
                        /* user attempt to LOGIN */
                        View.log("INFO: User attempting to log in");
                        reply.response = Database.loginUser(m.user);

                        // successful login
                        if (reply.response == 0) {
                            reply.list = Database.getAllItems();
                            reply.notif = Database.getNotification(m.user);
                            reply.user = Database.getUser(m.user);
                            loggedIn = true;
                            View.log("INFO: User:" + reply.user.username + " successfully logged in");
                        }

                        break;

                    case 1:
                        /* user attempt to REGISTER */
                        returnCode = Database.registerUser(m.user);
                        reply.response = returnCode;
                        View.log("INFO: New user registered: " + m.user.username);
                        break;

                    case 2:
                        /* user FORGOT password */
                        View.log("INFO: User initiated forgot password process: " + m.user.username);
                        switch (m.message) {
                            case "GET":
                                reply.user = new User();
                                reply.user.question = Database.getQuestion(m.user);
                                reply.response = (reply.user.question.equals("")) ? 1 : 0;
                                break;
                            case "VERIFY":
                                reply.response = Database.verifyAnswer(m.user);
                                break;
                            case "SET":
                                Database.setPassword(m.user);
                                reply.response = 0;
                                break;
                        }
                        break;
                    default:
                        View.log("ERROR: Illegal message type sent by client - not loggedIn");
                }
            } else if (loggedIn) {
                switch (type) {
                    //----------------------------------------------------------
                    case 10:
                        /* user wants ITEMS list */
                        View.log("INFO: User demands list of all items");
                        reply.list = Database.getAllItems();
                        break;
                    case 11:
                        /* user wants NOTIF list */
                        View.log("INFO: User demands list of notifications");
                        reply.notif = Database.getNotification(m.user);
                        break;
                    case 12:
                        /* user wants USER details - exclude password, security answer */
                        View.log("INFO: User demands details of user");
                        reply.user = Database.getUser(m.user);
                        // Remove critical information
                        reply.user.answer = "";
                        reply.user.password = "";
                        break;
                    //----------------------------------------------------------
                    case 3:
                        /* user attempt to BUY item */
                        reply.response = Database.buyItem(m.user, m.item);
                        View.log("INFO: User buys item - response code: " + reply.response);
                        break;
                    case 4:
                        /* user attempt to ADD item */
                        reply.response = Database.registerItem(m.item);
                        View.log("INFO: User adds new item");
                        break;
                    //----------------------------------------------------------
                    case 5:
                        /* user attempt to MODIFY item details */
                        Database.modifyItem(m.item);
                        View.log("INFO: User modified item details");
                        break;
                    case 6:
                        /* user attempt to REMOVE his item */
                        Database.deleteItem(m.item);
                        View.log("INFO: User deletes his item");
                        break;
                    case 7:
                        /* user attempt to EDIT his account detail */
                        Database.updateUser(m.user);
                        View.log("INFO: User edits his account details");
                        break;
                    case 8:
                        /* user attempt to DELETE his account */
                        Database.deleteUser(m.user);
                        loggedIn = false;
                        View.log("INFO: User deletes his account");
                        break;
                    case 9:
                        /* user attempt to LOGOUT */
                        loggedIn = false;
                        View.log("INFO: User has logged out.");
                        break;
                    default:
                        View.log("ERROR: Illegal message type sent by client - loggedIn: type=" + type);
                }
                if (type != 10 && type != 11 && type != 12 && type != 3 && type != 4) {
                    continue;
                }
            }

            /* send reply */
            try {
                View.log("INFO: Server sending Message to client");
                oos.writeObject(reply);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                View.log("ALERT: IOException occured while sending message to client");
            }

        }
    }
//------------------------------------------------------------------------------

}
