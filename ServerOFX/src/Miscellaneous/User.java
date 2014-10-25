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

    public User(String username, String password, String firstname,
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

    public void init(String username, String password, String firstname,
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
}
