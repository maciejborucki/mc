/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sc.main.view;

import com.vaadin.navigator.View; 
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import java.util.logging.Level;
import sc.main.util.Const;
import sc.main.util.Utils;

/**
 *
 * @author bor
 */
public class MenuView extends HorizontalLayout implements View {

    
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String username = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userLogin").toString();
        Utils.messageLog(Level.FINE, "User entered the protected application: ", username);
    } 

    public MenuView() {
        
        
//        setHeight(25, Sizeable.Unit.PIXELS);
        setWidth(100, Sizeable.Unit.PERCENTAGE);

        MenuBar barmenu = new MenuBar();
        addComponent(barmenu);
        barmenu.setWidth(100, Sizeable.Unit.PERCENTAGE);

        MenuBar.Command mycommand = new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                Notification.show(selectedItem.getText());
            }
        };
        
        MenuBar.Command logoutCommand = new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                Utils.getNavigator().navigateTo(Const.LOGOUT_VIEW); 
            }
        };
        
        MenuBar.Command personalDataCommand = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                Utils.getNavigator().navigateTo(Const.PERSONAL_DATA_VIEW); 
            }
        };
        
        MenuBar.Command newRequestCommand = new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                Utils.getNavigator().navigateTo(Const.NEW_REQUEST_VIEW); 
            }
        };
        
        MenuBar.Command listRequestCommand = new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                Utils.getNavigator().navigateTo(Const.LIST_REQUEST_VIEW); 
            }
        };
        
        MenuBar.Command newComplaintCommand = new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                Utils.getNavigator().navigateTo(Const.NEW_COMPLAINT_VIEW); 
            }
        };
        
        MenuBar.Command listComplaintCommand = new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                Utils.getNavigator().navigateTo(Const.LIST_COMPLAINT_VIEW); 
            }
        };

        MenuBar.MenuItem user = barmenu.addItem("User", null, null);
        user.addItem("Personal data", null, personalDataCommand);
        user.addItem("Logout", null, logoutCommand);
        
         MenuBar.MenuItem mCloud = barmenu.addItem("mCloud", null, null);
        mCloud.addItem("About mCloud", null, mycommand);
        mCloud.addItem("Credits", null, mycommand);
        
        MenuBar.MenuItem workspace = barmenu.addItem("Workspace", null, null);
        workspace.addItem("Components", null, mycommand);
        workspace.addItem("Services", null, mycommand);
        
        MenuBar.MenuItem finances = barmenu.addItem("Finances", null, null);
        finances.addItem("Components", null, mycommand);
        finances.addItem("Services", null, mycommand);

        MenuBar.MenuItem paperwork = barmenu.addItem("Paperwork", null, null);
        paperwork.addItem("Agreements", null, mycommand);
        paperwork.addItem("Licences", null, mycommand);
        
        MenuBar.MenuItem issues = barmenu.addItem("Issues", null, null);
        MenuBar.MenuItem requests = issues.addItem("Requests", null, null);
        requests.addItem("New request", null, newRequestCommand);
        requests.addItem("Request list", null, listRequestCommand);
        MenuBar.MenuItem complaints = issues.addItem("Complaints", null, null);
        complaints.addItem("New complaint", null, newComplaintCommand);
        complaints.addItem("Complaints list", null, listComplaintCommand);
        
        MenuBar.MenuItem help = barmenu.addItem("Help", null, null);
        help.addItem("Help index", null, mycommand);
    }
    
    
    
}
