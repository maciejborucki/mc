/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mi.mcloud.selfcare.view;

import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Runo;
import java.io.InputStream;
import java.util.logging.Level;
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

//    final Label emptyCell = new Label();
    final GridLayout grid = new GridLayout(5, 6);
    
    private User user = null;

    public PersonalDataView() {
        initComponents();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
//        Utils.checkAuthorization(getUI());
        String username = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userLogin").toString();
        user = userAPI.find(username);
        ViewUtils.tripleMessage(Level.INFO, footer, "PersonalDataView entered by: ", user.getDisplayname() + " "+ username);
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
        Button verifyPhoneButton = new Button("Verify phone");
        verifyPhoneButton.setStyleName("alignButtonDown");
//        Button verifyEmailButton = new Button("Verify Email");
//        verifyEmailButton.setStyleName("alignButtonDown");

        changeAddressButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                user.setCity(cityField.getValue());
                user.setPostalCode(codeField.getValue());
                user.setPostalAddress(addressField.getValue());
                user.setCo(countryField.getValue());
                userAPI.edit(user);
                ViewUtils.tripleMessage(Level.INFO, footer, "User address has been changed: ", user.getDisplayname());
                PersonalDataView.this.markAsDirtyRecursive();
            }
        });
        
//        armTextField(countryField, "2-32 letters required", registerFormLayout, new RegexpValidator("^[a-zA-Z-'` ]{2,32}$", "!"));
//        armTextField(cityField, "2-32 letters required", registerFormLayout, new RegexpValidator("^[0-9a-zA-Z-'` ]{2,32}$", "!"));
//        armTextField(codeField, "2-9 characters required", registerFormLayout, new RegexpValidator("^[0-9a-zA-Z-'` ]{2,9}$", "!"));
//        armTextField(addressField, "4-64 characters required", registerFormLayout, new RegexpValidator("^[0-9a-zA-Z-'`./ ]{4,64}$", "!"));
        
        
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
        
//        Window window = new Window();
//        ((VerticalLayout) window.getContent()).setSizeFull();
//        window.setResizable(true);
//        window.setWidth("800");
//        window.setHeight("600");
//        window.center();
        Embedded e = new Embedded();
        e.setSizeFull();
        e.setType(Embedded.TYPE_BROWSER);

        // Here we create a new StreamResource which downloads our StreamSource,
        // which is our pdf.
        StreamResource resource = new StreamResource(new Pdf(), "/home/bor/test.pdf");
//        resource
        // Set the right mime type
        resource.setMIMEType("application/pdf");

        e.setSource(resource);
        this.addComponent(e);
//        thisgetMainWindow().addWindow(window);
    }

    
}
