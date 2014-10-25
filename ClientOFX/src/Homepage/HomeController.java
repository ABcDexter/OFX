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

import Account.AccountController;
import Account.AccountView;
import Add.AddController;
import Add.AddView;
import Buyer.BuyerController;
import Buyer.BuyerView;
import Details.DetailsController;
import Details.DetailsView;
import Edit.EditController;
import Edit.EditView;
import Login.LoginController;
import Login.LoginView;
import MainPackage.Server;
import Miscellaneous.Item;
import Miscellaneous.Message;
import Miscellaneous.Notification;
import Miscellaneous.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class HomeController {

    public HomeView hview;
    public Home home;
    public User user;  // stores details of the authenticated user
//------------------------------------------------------------------------------

    public HomeController(HomeView hview, Home home, User user) {
        this.hview = hview;
        this.home = home;
        this.user = new User(user);   // authenticated user

        this.hview.addLogoutListener(new LogoutListener());
        this.hview.addAccountListener(new AccountListener());
        this.hview.addAddListener(new AddListener());
        this.hview.addRecentListener(new RecentListener());
        this.hview.addReviewListener(new ReviewListener());
        this.hview.addNotifListener(new NotifListener());
        this.hview.addSearchListener(new SearchListener());

        this.hview.addTreeSelectionListener(new SelectionListener());
        this.hview.addSortPriceListener(new SortPriceListener());
        this.hview.addSortNameListener(new SortNameListener());
        this.hview.addSortCategoryListener(new SortCategoryListener());
        this.hview.addSortBrandListener(new SortBrandListener());
        this.hview.addCheckListener(new CheckListener());
        this.hview.addBudgetListener(new BudgetListener());
        this.hview.addRefreshListsListener(new RefreshListener());
        this.hview.addRefreshNotifListener(new RefreshNotifListener());
        this.hview.addBasicRadioListener(new BasicRadioListener());
        this.hview.addAdvancedRadioListener(new AdvancedRadioListener());
        this.hview.addAdvancedListener(new AdvancedListener());
        this.hview.addBasicListener(new BasicListener());

        this.hview.setGreeting(user.firstname, user.username);
    }
//------------------------------------------------------------------------------

    public void addRecentItems(ArrayList<Item> recent) {
        Home.recent = home.getFilterRecent(user, recent);
        hview.addRecentItem(Home.recent);
    }
//------------------------------------------------------------------------------

    public void addReviewItems(ArrayList<Item> review) {
        Home.review = home.getFilterReview(user, review);
        hview.addReviewItem(Home.review);
    }
//------------------------------------------------------------------------------

    public void addNotifications(ArrayList<Notification> notif) {
        Home.notif = notif;
        hview.addNotification(notif);
    }
//------------------------------------------------------------------------------

    public void refreshLists() {
        /* Refresh list of Recent, Review  */
        Message m = new Message();
        Message reply = new Message();

        while (reply.list == null) {
            m.type = 10; // ITEMS
            Server.send(m);
            reply = Server.receive();
        }

        addRecentItems(reply.list);
        addReviewItems(reply.list);

        /* After refreshing, filter the items out of the budget, if necessary */
        if (hview.isBudgetChecked()) {
            int budget = hview.getSliderValue();
            hview.setBudget(budget);
            hview.addRecentItem(home.getFilterBudget(Home.recent, budget));
        }

        /* notif list refresh */
        m = new Message();
        reply = new Message();

        while (reply.notif == null) {
            m.type = 11; // NOTIFS
            m.user = new User();
            m.user.username = this.user.username;
            Server.send(m);
            reply = Server.receive();
        }

        addNotifications(reply.notif);
    }
//------------------------------------------------------------------------------

    class LogoutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* logout from account */
            boolean logout = hview.logoutConfirm();
            if (logout) {
                Message m = new Message();
                m.type = 9; // LOGOUT
                Server.send(m);

                hview.dispose();
                LoginView lview = new LoginView();
                User usr = new User();

                LoginController ctrl = new LoginController(lview, usr);
                lview.setVisible(true);
            }
        }
    }
//------------------------------------------------------------------------------

    class AccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* Account info dialog */
            AccountView aview = new AccountView(null, true);

            AccountController actrl = new AccountController(aview, user, hview);
            aview.setVisible(true);
        }
    }
//------------------------------------------------------------------------------

    class AddListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* user wants to add item for sale */
            AddView aview = new AddView(null, true);
            Item nitem = new Item();

            AddController actrl = new AddController(aview, nitem, user);
            aview.setVisible(true);

            refreshLists();
        }
    }

//------------------------------------------------------------------------------
    class RecentListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* user clicked on 'View Item Details' */
            int itemRow = hview.getSelectedRowRecent();
            hview.deselectRecentTable();

            DetailsView dview = new DetailsView(null, true);
            DetailsController dctrl = new DetailsController(dview, home.getRecentItem(itemRow), user);
            dctrl.init(); // initialize the details
            dview.setVisible(true);

            refreshLists(); // refresh needed if user buys item
        }
    }
//------------------------------------------------------------------------------

    class ReviewListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* user clicked on 'Edit Item Details' */
            int itemRow = hview.getSelectedRowReview();
            hview.deselectReviewTable();

            EditView eview = new EditView(null, true);
            EditController ectrl = new EditController(eview, home.getReviewItem(itemRow), user);
            ectrl.init();
            eview.setVisible(true);

            refreshLists();
        }
    }
//------------------------------------------------------------------------------

    class NotifListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* user clicked on 'See Buyer's details' */
            int notifRow = hview.getSelectedRowNotif();
            hview.deselectNotifTable();

            Notification notif = home.getNotifItem(notifRow);
            String buyerUsername = notif.text.substring(notif.text.lastIndexOf('(') + 1, notif.text.lastIndexOf(')'));

            Message m = new Message();
            Message reply = new Message();

            m.user = new User();
            m.user.username = buyerUsername;
            m.type = 12; // USER

            while (reply.user == null) {
                Server.send(m);
                reply = Server.receive();
            }

            BuyerView bview = new BuyerView(null, true);
            BuyerController bctrl = new BuyerController(bview, reply.user);
            bview.setVisible(true);
        }
    }
//------------------------------------------------------------------------------

    class SearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* user clicked on 'View Item Details' */
            int itemRow = hview.getSelectedRowSearch();
            hview.deselectSearchTable();

            DetailsView dview = new DetailsView(null, true);
            DetailsController dctrl = new DetailsController(dview, home.getRecentItem(itemRow), user);
            dctrl.init();
            dview.setVisible(true);

            hview.emptySearchTable();
            refreshLists();
        }
    }
//------------------------------------------------------------------------------

    class SelectionListener implements TreeSelectionListener {

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            /* user has chosen a (sub)category from Catalog */
            DefaultMutableTreeNode node = hview.getComponent();

            if (node == null) {
                return;
            }

            /* retrieve the node that was selected */
            String label = node.toString();

            /* display all the items across categories */
            if (label.equals("Product Catalog")) {
                hview.addRecentItem(Home.recent);
                return;
            }

            /* set filtered results */
            hview.addRecentItem(home.getFilterSelection(label));
        }
    }
//------------------------------------------------------------------------------

    class SortPriceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            home.sortRecent(hview.getSelectedButtonText());
            hview.addRecentItem(Home.recent);
        }
    }

//------------------------------------------------------------------------------
    class SortNameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            home.sortRecent(hview.getSelectedButtonText());
            hview.addRecentItem(Home.recent);
        }
    }
//------------------------------------------------------------------------------

    class SortCategoryListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            home.sortRecent(hview.getSelectedButtonText());
            hview.addRecentItem(Home.recent);
        }
    }
//------------------------------------------------------------------------------

    class SortBrandListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            home.sortRecent(hview.getSelectedButtonText());
            hview.addRecentItem(Home.recent);
        }
    }
//------------------------------------------------------------------------------

    class CheckListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            hview.toggleEnabledSort();
        }
    }
//------------------------------------------------------------------------------

    class BudgetListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            /* if uesr moves slider */
            int budget = hview.getSliderValue();
            hview.setBudget(budget);
            hview.addRecentItem(home.getFilterBudget(Home.recent, budget));
        }
    }
//------------------------------------------------------------------------------

    class RefreshListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* user clicks on refresh button in Browse tab */
            refreshLists();
        }
    }
//------------------------------------------------------------------------------

    class RefreshNotifListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* User clicks on refresh notifications */
            refreshLists();
        }
    }
//------------------------------------------------------------------------------

    class BasicRadioListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* user clicks on Basic Radio Button */
            hview.enableBasic();
        }
    }
//------------------------------------------------------------------------------

    class AdvancedRadioListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* user clicks on Advanced Radio Button */
            hview.enableAdvanced();
        }
    }
//------------------------------------------------------------------------------

    class BasicListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* user makes Basic Search Query */
            Home.search = home.getBasicResults(hview.getBasicQuery());
            hview.addSearchResults(home.getFilterRecent(user, Home.search));
        }
    }
//------------------------------------------------------------------------------

    class AdvancedListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            /* user makes Advanced Search Query */
            Item input = hview.getAdvancedQuery();
            Home.search = home.getAdvancedResults(input.itemname, input.brand, input.description);
            hview.addSearchResults(home.getFilterRecent(user, Home.search));
        }
    }
//------------------------------------------------------------------------------
}
