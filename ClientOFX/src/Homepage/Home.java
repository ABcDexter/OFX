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
package Homepage;

import Miscellaneous.Item;
import Miscellaneous.Notification;
import Miscellaneous.User;
import Utilities.Hardcoding;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Home {

    public static ArrayList<Item> recent;
    public static ArrayList<Item> review;
    public static ArrayList<Notification> notif;
    public static ArrayList<Item> search;

//------------------------------------------------------------------------------
    public Home() {
        recent = new ArrayList<>();
        review = new ArrayList<>();
        notif = new ArrayList<>();
        search = new ArrayList<>();
    }
//------------------------------------------------------------------------------

    public Item getRecentItem(int i) {
        return Home.recent.get(i);
    }
//------------------------------------------------------------------------------

    public Item getReviewItem(int i) {
        return Home.review.get(i);
    }
//------------------------------------------------------------------------------

    public Notification getNotifItem(int i) {
        return Home.notif.get(i);
    }
//------------------------------------------------------------------------------

    public Item getSearchItem(int i) {
        return Home.search.get(i);
    }
//------------------------------------------------------------------------------

    public ArrayList<Item> getFilterSelection(String selection) {
        ArrayList<Item> filter = new ArrayList<>();

        int i;
        for (i = 0; i < Home.recent.size(); i++) {
            if (Hardcoding.getCategory(Home.recent.get(i).category).equals(selection)
                    || Hardcoding.getSubcategory(Home.recent.get(i).category, Home.recent.get(i).subcategory).equals(selection)) {
                filter.add(Home.recent.get(i));
            }
        }

        return filter;
    }
//------------------------------------------------------------------------------

    public ArrayList<Item> getFilterReview(User user, ArrayList<Item> list) {
        ArrayList<Item> filter = new ArrayList<>();

        int i;
        for (i = 0; i < list.size(); i++) {
            if (list.get(i).username.equals(user.username)) {
                filter.add(list.get(i));
            }
        }

        return filter;
    }
//------------------------------------------------------------------------------

    public ArrayList<Item> getFilterRecent(User user, ArrayList<Item> list) {
        if (list == null) {
            return null;
        }
        ArrayList<Item> filter = new ArrayList<>();

        int i;
        for (i = 0; i < list.size(); i++) {
            if (!list.get(i).username.equals(user.username)) {
                filter.add(list.get(i));
            }
        }

        return filter;
    }
//------------------------------------------------------------------------------

    public ArrayList<Item> getFilterBudget(ArrayList<Item> list, int budget) {
        ArrayList<Item> filter = new ArrayList<>();

        int i;
        for (i = 0; i < list.size(); i++) {
            /* budget == 0 signifies no budget set = no restrictions */
            if (list.get(i).price <= budget || budget == 0) {
                filter.add(list.get(i));
            }
        }

        return filter;
    }
//------------------------------------------------------------------------------

    public ArrayList<Item> getBasicResults(String Q) {
        ArrayList<Item> res = new ArrayList<>();

        StringTokenizer t = new StringTokenizer(Q);
        while (t.hasMoreTokens()) {
            String query = t.nextToken();
            for (Item i : Home.recent) {
                query = query.toLowerCase();
                if (i.itemname.toLowerCase().contains(query) || i.brand.toLowerCase().contains(query) || i.description.toLowerCase().contains(query)
                        || Hardcoding.getCategory(i.category).toLowerCase().contains(query)
                        || Hardcoding.getSubcategory(i.category, i.subcategory).toLowerCase().contains(query)
                        || i.description.toLowerCase().contains(query)) {
                    res.add(i);
                }
            }
        }
        return res;
    }
//------------------------------------------------------------------------------

    public ArrayList<Item> getAdvancedResults(String name, String brand, String description) {
        ArrayList<Item> res = new ArrayList<>();
        name = name.toLowerCase();
        brand = brand.toLowerCase();
        description = description.toLowerCase();

        for (Item i : Home.recent) {
            if (i.itemname.toLowerCase().contains(name) || i.brand.toLowerCase().contains(brand)
                    || i.description.toLowerCase().contains(description)) {
                res.add(i);
            }
        }

        return res;
    }
//------------------------------------------------------------------------------

    public void sortRecent(String param) {
        switch (param) {
            case "Price":
                Collections.sort(Home.recent, new PriceComparator());
                break;
            case "Name":
                Collections.sort(Home.recent, new NameComparator());
                break;
            case "Category":
                Collections.sort(Home.recent, new CategoryComparator());
                break;
            case "Brand":
                Collections.sort(Home.recent, new BrandComparator());
                break;
        }
    }
//------------------------------------------------------------------------------

    class PriceComparator implements Comparator<Item> {

        @Override
        public int compare(Item o1, Item o2) {
            return o1.price - o2.price;
        }
    }
//------------------------------------------------------------------------------

    class NameComparator implements Comparator<Item> {

        @Override
        public int compare(Item o1, Item o2) {
            return o1.itemname.compareToIgnoreCase(o2.itemname);
        }
    }
//------------------------------------------------------------------------------

    class CategoryComparator implements Comparator<Item> {

        @Override
        public int compare(Item o1, Item o2) {
            return Hardcoding.getCategory(o1.category).compareToIgnoreCase(Hardcoding.getCategory(o2.category));
        }
    }
//------------------------------------------------------------------------------

    class BrandComparator implements Comparator<Item> {

        @Override
        public int compare(Item o1, Item o2) {
            return o1.brand.compareToIgnoreCase(o2.brand);
        }
    }
//------------------------------------------------------------------------------
}
