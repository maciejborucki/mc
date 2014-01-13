/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mi.mcloud.selfcare.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;
import mcloud.integration.ldap.client.LdapUserClient;
import pl.mi.mcloud.selfcare.util.Const;
import pl.mi.mcloud.selfcare.view.util.ViewUtils;
import pl.mlife.mcloud.integration.ldap.entity.User;

/**
 *
 * @author bor
 */
class PersonalDataView extends VerticalLayout implements View {

    final Navigator navigator = ViewUtils.getNavigator();
    final HorizontalLayout menuBar = new MenuView();
    final AbsoluteLayout footer = new AbsoluteLayout();
    final AbsoluteLayout header = new AbsoluteLayout();
    final VerticalLayout layout = new VerticalLayout();
    final LdapUserClient userAPI = Const.USER_API;
    final TextField usernameField = new TextField("Username");
    final TextField phoneField = new TextField("Phone number");
    final TextField emailField = new TextField("Email address");
    final TextField firstnameField = new TextField("First name");
    final TextField lastnameField = new TextField("Last name");
    final TextField displaynameField = new TextField("Display name");

    final TextField cityField = new TextField("City");
    final TextField codeField = new TextField("Postal code");
    final TextField addressField = new TextField("Address");
    final TextField countryField = new TextField("Country");

    final PasswordField password1Field = new PasswordField("Password");
    final PasswordField repassword1Field = new PasswordField("Confirm Password");

    final TextField phoneVerificationField = new TextField("Phone verification");
    final TextField emailVerificationField = new TextField("Email verification");

    final Label emptyCell = new Label();
    final GridLayout grid = new GridLayout(5, 6);

    public PersonalDataView() {
        initComponents();

//        String user = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userLogin").toString();
//        User bean = userAPI.find(user);
//
//        // Wrap it in a BeanItem
//        BeanItem<User> item = new BeanItem<User>(bean);
//
//        // Bind it to a component
//        Form form = new Form();
//        form.setItemDataSource(item);
//        addComponent(form);
//        UserVerificationCode uvc = userAPI.getVerificationCodes(user);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
//        Utils.checkAuthorization(getUI());
        String username = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userLogin").toString();
        User user = userAPI.find(username);
        usernameField.setValue(user.getUid());
        phoneField.setValue(user.getMobile());
        emailField.setValue(user.getMail());
        firstnameField.setValue(user.getGivenname());
        lastnameField.setValue(user.getSn());
        cityField.setValue(user.getCity());
        codeField.setValue(user.getPostalCode());
        addressField.setValue(user.getPostalAddress());
    }

    private void initComponents() {

        Panel light = new Panel("Personal data", grid);
        light.addStyleName(Runo.PANEL_LIGHT);
//        layout.addComponent(menuBar);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponent(light);
        

//        Label title = new Label("PERSONAL DATA");
//        layout.addComponent(title);
//        layout.addComponent(grid);
        layout.setSpacing(true);

        grid.setSpacing(true);
//        grid.setWidth(70, Sizeable.Unit.PERCENTAGE);

        Button verifyEmailButton = new Button("Verify email");
        verifyEmailButton.setStyleName("alignButtonDown");
        Button changeAddressButton = new Button("Change address");
        changeAddressButton.setStyleName("alignButtonDown");
        Button changePasswordButton = new Button("Change password");
        changePasswordButton.setStyleName("alignButtonDown");
        Button verifyPhoneButton = new Button("Verify Phone");
        verifyPhoneButton.setStyleName("alignButtonDown");
//        Button verifyEmailButton = new Button("Verify Email");
//        verifyEmailButton.setStyleName("alignButtonDown");
        
        grid.addComponent(usernameField);
        grid.addComponent(phoneField);
        grid.addComponent(emailField);
        grid.addComponent(new Label(""));
        grid.addComponent(new Label(""));

        grid.addComponent(firstnameField);
        grid.addComponent(lastnameField);
        grid.addComponent(displaynameField);
        grid.addComponent(new Label(""));
        grid.addComponent(new Label(""));

        grid.addComponent(cityField);
        grid.addComponent(codeField);
        grid.addComponent(addressField);
        grid.addComponent(countryField);
        grid.addComponent(changeAddressButton);

        grid.addComponent(password1Field);
        grid.addComponent(repassword1Field);
        grid.addComponent(changePasswordButton);
        grid.addComponent(new Label(""));
        grid.addComponent(new Label(""));

        grid.addComponent(phoneVerificationField);
        grid.addComponent(verifyPhoneButton);
        grid.addComponent(new Label(""));
        grid.addComponent(new Label(""));
        grid.addComponent(new Label(""));
        
        grid.addComponent(emailVerificationField);
        grid.addComponent(verifyEmailButton);
        grid.addComponent(new Label(""));
        grid.addComponent(new Label(""));
        grid.addComponent(new Label(""));

        usernameField.setEnabled(false);
        phoneField.setEnabled(false);
        emailField.setEnabled(false);
        firstnameField.setEnabled(false);
        lastnameField.setEnabled(false);
        displaynameField.setEnabled(false);

//        password1Field.setInputPrompt("Enter new password");
//        repassword1Field.setInputPrompt("Reenter new password");
        light.setWidth(grid.getWidth(), Sizeable.Unit.PIXELS);
        
        ViewUtils.generateHeaderFooter(header, footer);

//        layout.addComponent(new VerticalLayout(new Label("dsakjdskj")));
        this.setSizeFull();
        layout.setWidth(100, Sizeable.Unit.PERCENTAGE);
        ViewUtils.attachHeader(this, header);
        this.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        this.addComponent(menuBar);
        this.setExpandRatio(menuBar, 5);
        
        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.addComponent(layout);
        this.setExpandRatio(layout, 75);
        ViewUtils.attachFooter(this, footer);
//        grid.addComponent(u);
        // Create a 4 by 4 grid layout
// Fill out the first row using the cursor
//        grid.addComponent(new Button("R/C 1"));
//        for (int i = 0; i < 3; i++) {
//            grid.addComponent(new Button("Col " + (grid.getCursorX() + 1)));
//        }
//
//// Fill out the first column using coordinates
//        for (int i = 1; i < 4; i++) {
//            grid.addComponent(new Button("Row " + i), 0, i);
//        }
//
//// Add some components of various shapes.
//        grid.addComponent(new Button("3x1 button"), 1, 1, 3, 1);
//        grid.addComponent(new Label("1x2 cell"), 1, 2, 1, 3);
//        final InlineDateField date = new InlineDateField("A 2x2 date field");
//        date.setResolution(DateField.RESOLUTION_DAY);
//        grid.addComponent(date, 2, 2, 3, 3);
//
//// Needed for the grid borders
//        grid.addStyleName("basic-gridlayout");
//        grid.setMargin(true);
//        grid.setSizeUndefined(); // This isn't enough
//        setSizeUndefined();      // This is needed
    }
}
