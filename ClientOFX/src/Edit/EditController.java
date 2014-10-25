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
package Edit;

import MainPackage.Server;
import Miscellaneous.Item;
import Miscellaneous.Message;
import Miscellaneous.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditController {

    EditView eview;
    Item item;
//------------------------------------------------------------------------------

    public EditController(EditView eview, Item item, User user) {
        this.eview = eview;
        this.item = item;

        this.eview.addUpdateListener(new UpdateListener());
        this.eview.addDeleteListener(new DeleteListener());
        this.eview.addBackListener(new BackListener());
        this.eview.addCategoryListener(new CategoryListener());
    }
//------------------------------------------------------------------------------
    /* method init() is called after EditController instantiation */

    public void init() {
        eview.setDetails(this.item);
    }
//------------------------------------------------------------------------------

    class UpdateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* user confirms details of new item */
            Item ni = eview.getItemDetails();
            int returnCode = Item.sanityCheckItem(ni);

            if (returnCode == 0) {
                Message m = new Message();
                m.item = new Item(ni);
                m.type = 5; // MODIFY
                Server.send(m);
            }

            switch (returnCode) {
                case 0:
                    /* success - item modified */
                    eview.updateSuccess();
                    eview.dispose();
                    break;
                case 1:
                    /* mandatory fields empty */
                    eview.fieldsEmpty();
                    break;
            }
        }
    }
//------------------------------------------------------------------------------

    class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* user confirms deletion of item */
            boolean delete = eview.deleteConfirm();
            if (delete) {
                /* delete item */
                Message m = new Message();
                m.item = new Item(item);
                m.type = 6; // REMOVE
                Server.send(m);

                /* get back to homepage */
                eview.dispose();
            }
        }
    }
//------------------------------------------------------------------------------

    class BackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* Back to homepage */
            eview.dispose();
        }
    }
//------------------------------------------------------------------------------

    class CategoryListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* update subcategory combo box */
            eview.setSubcategory(eview.getSelectedCategory());
        }
    }

//------------------------------------------------------------------------------
}
