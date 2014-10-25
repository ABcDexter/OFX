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
import java.util.ArrayList;

public class Message implements Serializable {

    private static final long serialVersionUID = 100;

    public int type;
    public String message;
    public int response;
    public User user;
    public Item item;
    public ArrayList<Item> list;
    public ArrayList<Notification> notif;
//------------------------------------------------------------------------------

    public Message() {
    }
//------------------------------------------------------------------------------

    public int getType() {
        return type;
    }
//------------------------------------------------------------------------------

}
