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
package Miscellaneous;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 101;

    public String username;
    public String password;
    public String firstname;
    public String lastname;
    public String contact;
    public int hostel;
    public String room;
    public String question;
    public String answer;

//------------------------------------------------------------------------------
    public User() {
    }

//------------------------------------------------------------------------------
    public User(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }
//------------------------------------------------------------------------------

    public User(String username, String password, String firstname,
            String lastname, String contact, int hostel, String room, String question, String answer) {
        this();
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.contact = contact;
        this.hostel = hostel;
        this.room = room;
        this.question = question;
        this.answer = answer;
    }
//------------------------------------------------------------------------------

    public User(User ob) {
        this();
        this.username = ob.username;
        this.password = ob.password;
        this.firstname = ob.firstname;
        this.lastname = ob.lastname;
        this.contact = ob.contact;
        this.hostel = ob.hostel;
        this.room = ob.room;
        this.question = ob.question;
        this.answer = ob.answer;
    }
//------------------------------------------------------------------------------

    void initialize(String username, String password, String firstname,
            String lastname, String contact, int hostel, String room, String question, String answer) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.contact = contact;
        this.hostel = hostel;
        this.room = room;
        this.question = question;
        this.answer = answer;
    }

//------------------------------------------------------------------------------
    public static int sanityCheckLogin(User user) {

        /* empty username */
        if (user.username.trim().isEmpty()) {
            return 4;
        }

        /*empty password */
        if (user.password.isEmpty()) {
            return 3;
        }

        /* sanity check success */
        return 0;
    }

//------------------------------------------------------------------------------
    public static int sanityCheckRegister(User ob) {

        /* Mandatory fields left empty */
        if (ob.username.isEmpty() || ob.password.isEmpty() || ob.answer.isEmpty()
                || ob.firstname.isEmpty() || ob.question.isEmpty() || ob.hostel == 0 || ob.contact.isEmpty()) {
            return 4;
        }

        /* short password */
        if (ob.password.length() < 6) {
            return 3;
        }

        /* incorrect mobile no */
        String regex = "[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";
        if (!ob.contact.matches(regex)) {
            return 2;
        }

        /* sanity check success */
        return 0;
    }
//------------------------------------------------------------------------------

    public static int sanityCheckForgot(User ob) {
        /* empty username */
        if (ob.username.isEmpty()) {
            return 2;
        }

        /* sanity check success */
        return 0;
    }
//------------------------------------------------------------------------------

    public static int sanityCheckAnswer(User ob) {

        /* empty answer to security ques */
        if (ob.answer.isEmpty()) {
            return 2;
        }

        /* sanity check success */
        return 0;
    }
//------------------------------------------------------------------------------

    public static int sanityCheckPassword(User ob) {

        /* short password */
        if (ob.password.length() < 6) {
            return 1;
        }

        /* sanity check success */
        return 0;
    }

//------------------------------------------------------------------------------
}
