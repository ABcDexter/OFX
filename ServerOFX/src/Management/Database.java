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
/* 

 Fixing SQL Injection attacks
 http://software-security.sans.org/developer-how-to/fix-sql-injection-in-java-using-prepared-callable-statement
 https://www.owasp.org/index.php/Preventing_SQL_Injection_in_Java

 */
package Management;

import Miscellaneous.Item;
import Miscellaneous.Notification;
import Miscellaneous.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    /* DB variables */
    static String HOST;
    static String DBNAME;
    static String UNAME;
    static String PASS;
    static String URL;

    static Connection CONN;
    static ResultSet RESULT;
    static Statement STAT;

//------------------------------------------------------------------------------
    public Database() {
    }
//------------------------------------------------------------------------------

    public static void init() {
        try {
            HOST = "jdbc:mysql://localhost:3306/";
            DBNAME = "ofx";
            UNAME = "root";
            PASS = "";
            URL = HOST + DBNAME + "?user=" + UNAME + "&password=" + PASS;

            Class.forName("com.mysql.jdbc.Driver");
            CONN = DriverManager.getConnection(URL);
            STAT = CONN.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            View.log("SEVERE ALERT: Error initializing connection to database");
        }
    }
//------------------------------------------------------------------------------

    public static int loginUser(User user) {

        try {

            /* Retrieve password for given username from DB */
            RESULT = STAT.executeQuery("SELECT password FROM user WHERE username='" + user.username + "'");

            /* If username doesn't exist */
            if (!RESULT.next()) {
                return 2;
            }

            /* if password does NOT match */
            if (!RESULT.getString(1).equals(user.password)) {
                return 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            View.log("SEVERE ALERT: Error while executing Login query on database");
        }

        // Successful login
        return 0;
    }

//------------------------------------------------------------------------------
    public static User getUser(User user) {
        User u = new User();
        try {
            /* Get user profile */
            RESULT = STAT.executeQuery("SELECT * FROM user WHERE username='" + user.username + "'");
            RESULT.next();

            u.init(RESULT.getString(1), RESULT.getString(2), RESULT.getString(3), RESULT.getString(4),
                    RESULT.getString(5), RESULT.getInt(6), RESULT.getString(7), RESULT.getString(8), RESULT.getString(9));

        } catch (SQLException e) {
            e.printStackTrace();
            View.log("SEVERE ALERT: Error in fetching user information");
        }
        return u;
    }
//------------------------------------------------------------------------------

    public static int registerUser(User ob) {

        try {
            /* check if user already exists with given username */
            RESULT = STAT.executeQuery("SELECT * FROM user WHERE username='" + ob.username + "'");

            /* If username already exists */
            if (RESULT.next()) {
                return 1;
            }

            /* Add new record to user table */
            STAT.execute("INSERT INTO user VALUES ('" + ob.username + "','" + ob.password + "','"
                    + ob.firstname + "','" + ob.lastname + "','" + ob.contact + "','" + ob.hostel + "','"
                    + ob.room + "','" + ob.question + "','" + ob.answer + "')");

        } catch (SQLException e) {
            e.printStackTrace();
            View.log("SEVERE ALERT: Error registering new user");
        }

        // registration success
        return 0;
    }
//------------------------------------------------------------------------------

    public static void updateUser(User user) {
        try {
            /* Update record in user table */
            STAT.execute("UPDATE user SET password='" + user.password + "', contact='" + user.contact + "', hostel='" + user.hostel + "', "
                    + "room='" + user.room + "', question='" + user.question + "', answer='" + user.answer + "' WHERE username='" + user.username + "'");

        } catch (SQLException e) {
            e.printStackTrace();
            View.log("SEVERE ALERT: Error updating user information");
        }
    }
//------------------------------------------------------------------------------

    public static void deleteUser(User user) {
        System.out.println(user.username);
        try {
            /* Delete records from table-> user,item,notif */
            STAT.execute("DELETE FROM user WHERE username='" + user.username + "'");
            STAT.execute("DELETE FROM item WHERE username='" + user.username + "'");
            STAT.execute("DELETE FROM notif WHERE username='" + user.username + "'");
        } catch (SQLException e) {
            e.printStackTrace();
            View.log("SEVERE ALERT: Error deleting user from database");
        }
    }
//------------------------------------------------------------------------------

    public static String getQuestion(User u) {
        String Q = "";
        try {
            /* Retrieve security question for given username */
            RESULT = STAT.executeQuery("SELECT question FROM user WHERE username='" + u.username + "'");
            RESULT.next();
            Q = RESULT.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
            View.log("SEVERE ALERT: Error fetching security question");
        }
        return Q;
    }
//------------------------------------------------------------------------------

    public static void setPassword(User u) {
        try {
            STAT.execute("UPDATE user SET password='" + u.password + "' WHERE username='" + u.username + "'");
        } catch (SQLException e) {
            e.printStackTrace();
            View.log("SEVERE ALERT: Error in recovery process: cannot change password");
        }
    }
//------------------------------------------------------------------------------

    public static int verifyAnswer(User u) {
        try {
            /* Retrieve security answer for given username */
            RESULT = STAT.executeQuery("SELECT answer FROM user WHERE username='" + u.username + "'");
            RESULT.next();

            // if answer is correct
            if (u.answer.equalsIgnoreCase(RESULT.getString(1))) {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            View.log("SEVERE ALERT: Error in recovery process: cannot verify security answer");
        }

        // wrong answer
        return 1;
    }
//------------------------------------------------------------------------------

    public static ArrayList<Item> getAllItems() {
        ArrayList<Item> list = new ArrayList<>();
        try {
            /* retrieve all items */
            RESULT = STAT.executeQuery("SELECT * FROM item");
            while (RESULT.next()) {
                list.add(new Item(RESULT.getString(1), RESULT.getInt(2), RESULT.getInt(3), RESULT.getInt(4), RESULT.getString(5),
                        RESULT.getInt(6), RESULT.getString(7), RESULT.getString(8), RESULT.getString(9), RESULT.getString(10)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            View.log("SEVERE ALERT: Error fetching list of items recently added for sale");
        }
        return list;
    }

//------------------------------------------------------------------------------
    public static ArrayList<Notification> getNotification(User user) {
        ArrayList<Notification> notif = new ArrayList<>();
        try {
            /* retrieve notifs */
            RESULT = STAT.executeQuery("SELECT itemname,text FROM notif WHERE username='" + user.username + "'");
            while (RESULT.next()) {
                notif.add(new Notification(RESULT.getString(1), RESULT.getString(2)));
            }

//            /* delete notifs */
//            STAT.execute("DELETE FROM notif WHERE username='" + user.username + "'");
        } catch (SQLException e) {
            e.printStackTrace();
            View.log("SEVERE ALERT: Error fetching notifications for user: " + user.username);
        }
        return notif;
    }
//------------------------------------------------------------------------------

    public static int registerItem(Item nitem) {
        // TODO: ensure that this item contains the correct username
        try {
            /* if an item with the same name exists, send error code to user */
            RESULT = STAT.executeQuery("SELECT itemname FROM item WHERE itemname='" + nitem.username + "'");
            if (RESULT.next()) {
                return 1;
            }

            /* item name ensured to be unique */
            STAT.execute("INSERT INTO item VALUES ('" + nitem.itemname + "','" + nitem.price + "','"
                    + nitem.category + "','" + nitem.subcategory + "','" + nitem.brand + "','"
                    + nitem.quantity + "','" + nitem.username + "','" + nitem.location + "','" + nitem.description + "','"
                    + nitem.contact + "')");
        } catch (SQLException e) {
            View.log("SEVERE ALERT: Error registering item; itemname: " + nitem.itemname);
            return 1;
        }
        return 0;
    }
//------------------------------------------------------------------------------

    public static int buyItem(User buyer, Item item) {
        /* it might be possible that in the time interval of user clicking on View Item details
         and clicking on Buy, another user logged in to server, buys the item
         In this case, user must be shown a Just Sold message
         */
        try {

            item.quantity--; // deduct quantity

            /* Update quantity of item in table if quantity is still > 0 */
            if (item.quantity > 0) {
                STAT.execute("UPDATE item SET price='" + item.price + "',category='" + item.category + "',subcategory='" + item.subcategory
                        + "',brand='" + item.brand + "',quantity='" + item.quantity + "',description='" + item.description + "',location='" + item.location + "',contact='" + item.contact + "' "
                        + "WHERE itemname='" + item.itemname + "'");
            } else if (item.quantity == 0) /* Delete item from table if quantity has become zero */ {
                STAT.execute("DELETE FROM item WHERE itemname='" + item.itemname + "'");
            } else if (item.quantity < 0) {
                /* item has been already sold out - case of high traffic of clients */
                return 1;
            }

            /* Check if notification has already been added - user buys more than 1 quantity of same item */
            String notif = "Your item: " + item.itemname + " has been purchased by " + buyer.firstname + " (" + buyer.username + ")";
            RESULT = STAT.executeQuery("SELECT * FROM notif WHERE text='" + notif + "'");
            if (RESULT.next()) {
                return 0;
            }
            /* Add sold notification for seller */
            STAT.execute("INSERT INTO notif VALUES ('" + item.username + "','" + item.itemname + "','" + notif + "')");
        } catch (SQLException e) {
            e.printStackTrace();
            View.log("SEVERE ALERT: Error buying item: " + item.itemname);
            return 1;
        }
        return 0;
    }
//------------------------------------------------------------------------------

    public static void modifyItem(Item item) {
        try {
            STAT.execute("UPDATE item SET price='" + item.price + "',category='" + item.category + "',subcategory='" + item.subcategory
                    + "',brand='" + item.brand + "',quantity='" + item.quantity + "',description='" + item.description
                    + "',location='" + item.location + "',contact='" + item.contact + "' " + "WHERE itemname='" + item.itemname + "'");
        } catch (SQLException e) {
            e.printStackTrace();
            View.log("SEVERE ALERT: Error modifying item details: " + item.itemname);
        }
    }
//------------------------------------------------------------------------------

    public static void deleteItem(Item item) {
        try {
            /* Delete item from table */
            STAT.execute("DELETE FROM item WHERE itemname='" + item.itemname + "'");
        } catch (SQLException e) {
            e.printStackTrace();
            View.log("SEVERE ALERT: Error deleting item: " + item.itemname);
        }
    }
//------------------------------------------------------------------------------
}
