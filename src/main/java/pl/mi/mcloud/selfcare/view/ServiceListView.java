/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mi.mcloud.selfcare.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;
import mcloud.integration.ldap.client.LdapUserClient;
import pl.mi.mcloud.selfcare.util.Const;
import pl.mi.mcloud.selfcare.view.util.ViewUtils;

/**
 *
 * @author bor
 */
class ServiceListView extends VerticalLayout implements View {

    
    String username;
    final Navigator navigator = ViewUtils.getNavigator();
    final HorizontalLayout menuBar = new MenuView();
    final AbsoluteLayout footer = new AbsoluteLayout();
    final AbsoluteLayout header = new AbsoluteLayout();
    final VerticalLayout layout = new VerticalLayout();
    final LdapUserClient userAPI = Const.USER_API;
//    final TextField usernameField = new TextField("Username");
//    final TextField phoneField = new TextField("Phone number");
//    final TextField emailField = new TextField("Email address");
//    final TextField firstnameField = new TextField("First name");
//    final TextField lastnameField = new TextField("Last name");
//    final TextField displaynameField = new TextField("Display name");
//
//    final TextField cityField = new TextField("City");
//    final TextField codeField = new TextField("Postal code");
//    final TextField addressField = new TextField("Address");
//    final ListSelect countrySelect = new ListSelect("Country");
//
//    final PasswordField password1Field = new PasswordField("Password");
//    final PasswordField repassword1Field = new PasswordField("Confirm Password");
//
//    final TextField phoneVerificationField = new TextField("Phone verification");
//    final TextField emailVerificationField = new TextField("Email verification");
//
//    Button verifyEmailButton = new Button("Verify email address");
//    Button changeAddressButton = new Button("Change address");
//    Button changePasswordButton = new Button("Change password");
//    Button verifyPhoneButton = new Button("Verify phone number");
//    
//    int failerCounter;
//
//    final Map<String, ValidComponent> validComponentMap1 = new HashMap<String, ValidComponent>();

    final GridLayout grid = new GridLayout(5, 6);

    public ServiceListView() {
        initComponents();
    }

    public void enter(ViewChangeListener.ViewChangeEvent event) {
        ViewUtils.checkAuthorization(getUI());
//        int failerCounter = 0;
//        username = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userLogin").toString();
//        User user = userAPI.find(username);
//        usernameField.setValue(user.getUid());
//        phoneField.setValue(user.getMobile());
//        emailField.setValue(user.getMail());
//        firstnameField.setValue(user.getGivenname());
//        lastnameField.setValue(user.getSn());
//        cityField.setValue(user.getCity());
//        codeField.setValue(user.getPostalCode());
//        addressField.setValue(user.getPostalAddress());
//        countrySelect.select(user.getCo());
//        if(user.isEmailConfirmed()) {
//            emailVerificationField.setEnabled(false);
//            emailVerificationField.setValue("Email address verified");
//            verifyEmailButton.setEnabled(false);
//        }
//        if(user.isSmsConfirmed()) {
//            phoneVerificationField.setEnabled(false);
//            phoneVerificationField.setValue("Phone number verified");
//            verifyEmailButton.setEnabled(false);
//        }
    }

    private void initComponents() {

        Panel light = new Panel("Personal data", grid);
        light.addStyleName(Runo.PANEL_LIGHT);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponent(light);
        layout.setSpacing(true);
        grid.setSpacing(true);

//        verifyEmailButton.setStyleName("alignButtonDown");
//        changeAddressButton.setStyleName("alignButtonDown");
//        changePasswordButton.setStyleName("alignButtonDown");
//        verifyPhoneButton.setStyleName("alignButtonDown");
////        verifyEmailButton.setEnabled(false);
////        verifyPhoneButton.setEnabled(false);
////        changePasswordButton.setEnabled(false);
//
//        armListeners();
//
//        grid.addComponent(usernameField);
//        grid.addComponent(phoneField);
//        grid.addComponent(emailField);
//        grid.addComponent(new Label(""));
//        grid.addComponent(new Label(""));
//
//        grid.addComponent(firstnameField);
//        grid.addComponent(lastnameField);
//        grid.addComponent(displaynameField);
//        grid.addComponent(new Label(""));
//        grid.addComponent(new Label(""));
//
//        grid.addComponent(cityField);
//        grid.addComponent(codeField);
//        grid.addComponent(addressField);
//        grid.addComponent(countrySelect);
//        CountryCodes countryCodes = new CountryCodes();
//        Map<String, String> map = countryCodes.getMap();    
//        for (Map.Entry entry : map.entrySet()) {
//            countrySelect.addItem(entry.getKey());
//        }
//        countrySelect.setNullSelectionAllowed(false);
//        
//        countrySelect.setRows(1);
//        grid.addComponent(changeAddressButton);
//        Utils.armPwdField(password1Field, validComponentMap1, changePasswordButton, "8 characters, at least one lowercase letter, one uppercase letter, one digit, one special symbol", grid, new RegexpValidator("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$", "!"));
//        Utils.armRePwdField(password1Field, repassword1Field, validComponentMap1, changePasswordButton, "required match with first password input", grid, new RegexpValidator("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$", "!"));
//        grid.addComponent(changePasswordButton);
//        grid.addComponent(new Label(""));
//        grid.addComponent(new Label(""));
//
//        grid.addComponent(phoneVerificationField);
//        grid.addComponent(verifyPhoneButton);
//        grid.addComponent(new Label(""));
//        grid.addComponent(new Label(""));
//        grid.addComponent(new Label(""));
//
//        grid.addComponent(emailVerificationField);
//        grid.addComponent(verifyEmailButton);
//        grid.addComponent(new Label(""));
//        grid.addComponent(new Label(""));
//        grid.addComponent(new Label(""));
//
//        usernameField.setEnabled(false);
//        phoneField.setEnabled(false);
//        emailField.setEnabled(false);
//        firstnameField.setEnabled(false);
//        lastnameField.setEnabled(false);
//        displaynameField.setEnabled(false);

        light.setWidth(grid.getWidth(), Sizeable.Unit.PIXELS);

        ViewUtils.generateHeaderFooter(header, footer);

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
    }
    
//    private void armListeners() {
//        changePasswordButton.addClickListener(new Button.ClickListener() {
//
//            public void buttonClick(Button.ClickEvent event) {
//                if (!password1Field.getValue().equals(repassword1Field.getValue())) {
//                    Utils.dualMessage(Level.FINEST, footer, "Passowrd and Confirm Password fields must have the same values");
//                    return;
//                }
//                try {
////                    userAPI.setUsernamePassword(username, password1Field.getValue());
////                    User u = userAPI.find(username);
////                    u.setUserpassword(password1Field.getValue());
////                    userAPI.edit(u);
//                    userAPI.changePassword(new Password(password1Field.getValue()), username);
//                    password1Field.setValue("");
//                    repassword1Field.setValue("");
//                    changePasswordButton.setEnabled(false);
//                    Utils.dualMessage(Level.INFO, footer, "Password has been changed");
//                } catch (UniformInterfaceException e) {
//                    Utils.dualMessage(Level.WARNING, footer, "Password change failed");
//                    System.err.println("userAPI.setUsernamePassword " + e.getResponse().getEntity(String.class));
//                    return;
//                }
//            }
//        });
//        
////        emailVerificationField.addValueChangeListener(new Property.ValueChangeListener() {
////
////            public void valueChange(Property.ValueChangeEvent event) {
////                if(!emailVerificationField.getValue().equals("")) {
////                    verifyEmailButton.setEnabled(true);
////                } else {
////                    verifyEmailButton.setEnabled(false);
////                }
////            }
////        });
//        
////        phoneVerificationField.addTextChangeListener(new FieldEvents.TextChangeListener() {
////
////            public void textChange(FieldEvents.TextChangeEvent event) {
////                if(!phoneVerificationField.getValue().equals("")) {
////                    verifyPhoneButton.setEnabled(true);
////                } else {
////                    verifyPhoneButton.setEnabled(false);
////                }
////            }
////        });
//        
//        verifyEmailButton.addClickListener(new Button.ClickListener() {
//
//            public void buttonClick(Button.ClickEvent event) {
//                UserVerificationCode uvc = userAPI.getVerificationCodes(username);
//                Utils.messageLog(Level.FINE, username);
//                Utils.messageLog(Level.FINE, uvc.toString());
//                Utils.messageLog(Level.FINE, uvc.getEmailVerificationCode());
//                String evc = uvc.getEmailVerificationCode();
//                if(evc.equals(emailVerificationField.getValue().trim())) {
//                    User u = userAPI.find(username);
//                    u.setEmailConfirmed(true);
//                    userAPI.edit(u);
//                    Utils.dualMessage(Level.INFO, footer, "Email has been verified");
//                } else {
//                    Utils.dualMessage(Level.WARNING, footer, "Verification code is invalid");
//                    failerCounter++;
//                    if(failerCounter==5) {
//                        Utils.tripleMessage(Level.WARNING, footer, "Too many code verification attempts");
//                        navigator.navigateTo(Const.LOGOUT_VIEW); 
//                    }
//                }
//            }
//        });
//        
//        verifyPhoneButton.addClickListener(new Button.ClickListener() {
//
//            public void buttonClick(Button.ClickEvent event) {
//                UserVerificationCode uvc = userAPI.getVerificationCodes(username);
//                String svc = uvc.getSmsVerificationCode();
//                if(svc.equals(phoneVerificationField.getValue().trim())) {
//                    User u = userAPI.find(username);
//                    u.setSmsConfirmed(true);
//                    userAPI.edit(u);
//                    Utils.dualMessage(Level.INFO, footer, "Phone number has been verified");
//                } else {
//                    Utils.dualMessage(Level.WARNING, footer, "Verification code is invalid");
//                    failerCounter++;
//                    if(failerCounter==5) {
//                        Utils.tripleMessage(Level.WARNING, footer, "Too many code verification attempts");
//                        navigator.navigateTo(Const.LOGOUT_VIEW); 
//                    }
//                }
//            }
//        });
//        
//    }
}
