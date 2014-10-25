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
package Utilities;

public class Hardcoding {

    public static String[] categories = new String[]{"(Select category)", "Electronics", "Stationery", "Books", "Sports", "Appliances", "Miscellaneous"};
    public static String[][] subcategories = new String[][]{
        {"(Select category)"},
        {"(Select subcategory)", "Mobiles", "Mobile Accessories", "Tablets", "Laptops", "Computer Accessories", "Software/Antivirus",
            "Headphones", "Speakers", "MP3 Players", "Gaming Accessories"},
        {"(Select subcategory)", "Drafters", "Geometry Box", "Calculators", "College Supplies", "Bags", "Document Folders"},
        {"(Select subcategory)", "Novels", "Entrance Exam", "Computers", "Civil", "Mechanical", "Electronics & Communication", "Electrical", "Biotechnology",
            "Mathematics", "Physics", "Chemistry/Chemical"},
        {"(Select subcategory)", "Cricket", "Basketball", "Football", "Volleyball", "Hockey", "Table Tennis", "Badminton"},
        {"(Select subcategory)", "Irons", "Kettle", "Heater", "Cooler", "Lamp"},
        {"(Select subcategory)", "Study Table", "Mattress", "Pillow", "Formal Shoes", "Table Clock", "Others"}};

//------------------------------------------------------------------------------
    public static String getCategory(int category) {
        /* convert category # to readable string */
        return categories[category];  // category is 1-indexed
    }
//------------------------------------------------------------------------------

    public static String getSubcategory(int category, int subcategory) {
        /* convert subcategory # to readable String */
        return subcategories[category][subcategory];
    }
//------------------------------------------------------------------------------

    public static String getHostelName(int hostel) {
        String[] array = {"(Select Hostel)", "KNGH", "Malviya", "Mega", "Patel", "PG Hostel", "Raman", "SNGH", "Tagore", "Tandon", "Tilak"};
        return array[hostel];
    }
//------------------------------------------------------------------------------
}
