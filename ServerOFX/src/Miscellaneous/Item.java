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

public class Item implements Serializable {

    private static final long serialVersionUID = 102;

    public String itemname;
    public int price;
    public int category;
    public int subcategory;
    public String brand;
    public int quantity;
    public String username;
    public String location;
    public String description;
    public String contact;

//------------------------------------------------------------------------------
    public Item() {

    }
//------------------------------------------------------------------------------

    public Item(String itemname, int price, int category, int subcategory,
            String brand, int quantity, String username, String location, String description, String contact) {
        this.itemname = itemname;
        this.price = price;
        this.category = category;
        this.subcategory = subcategory;
        this.brand = brand;
        this.quantity = quantity;
        this.username = username;
        this.location = location;
        this.description = description;
        this.contact = contact;
    }
//------------------------------------------------------------------------------

    /* Copy constructor */
    public Item(Item ob) {
        this.itemname = ob.itemname;
        this.price = ob.price;
        this.category = ob.category;
        this.subcategory = ob.subcategory;
        this.brand = ob.brand;
        this.quantity = ob.quantity;
        this.username = ob.username;
        this.location = ob.location;
        this.description = ob.description;
        this.contact = ob.contact;
    }
//------------------------------------------------------------------------------

    public static int sanityCheckItem(Item item) {
        /* mandatory fields empty */
        if (item.itemname.isEmpty() || item.category == 0 || item.location.isEmpty() || item.price == 0) {
            return 1;
        }

        /* item details are sane */
        return 0;
    }
//------------------------------------------------------------------------------
}
