package pl.mi.mcloud.selfcare.view;

//import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.validator.CompositeValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.navigator.Navigator; 
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.mail.MessagingException;
import mcloud.integration.ldap.client.LdapAssignClient;
import mcloud.integration.ldap.client.LdapGroupClient;
import mcloud.integration.ldap.client.LdapUserClient;
import pl.mi.mcloud.selfcare.util.Const;
import pl.mi.mcloud.selfcare.util.Sender;
import pl.mi.mcloud.selfcare.view.util.ViewUtils;
import pl.mlife.mcloud.integration.ldap.entity.Group;
import pl.mlife.mcloud.integration.ldap.entity.GroupAssign;
import pl.mlife.mcloud.integration.ldap.entity.Password;
import pl.mlife.mcloud.integration.ldap.entity.User;
import pl.mlife.mcloud.integration.ldap.entity.UserVerificationCode;
//import javax.mail.MessagingException;
//import mcloud.integration.ldap.client.LdapAssignClient;
//import mcloud.integration.ldap.client.LdapGroupClient;
//import mcloud.integration.ldap.client.LdapUserClient;
//import pl.mlife.mcloud.integration.ldap.entity.Group;
//import pl.mlife.mcloud.integration.ldap.entity.GroupAssign;
//import pl.mlife.mcloud.integration.ldap.entity.Password;
//import pl.mlife.mcloud.integration.ldap.entity.User;
//import pl.mlife.mcloud.integration.ldap.entity.UserVerificationCode;

/**
 *
 * @author bor
 */
public class RegisterView extends VerticalLayout implements View {

    final LdapUserClient userAPI = Const.USER_API;
    final LdapGroupClient groupAPI = Const.GROUP_API;
    final LdapAssignClient assignAPI = Const.ASSIGN_API;
    final AbsoluteLayout footer = new AbsoluteLayout();
    final AbsoluteLayout header = new AbsoluteLayout();
    final Navigator navigator = ViewUtils.getNavigator();
    final VerticalLayout layout = new VerticalLayout();
//    final Form form = new Form();
    final FormLayout registerFormLayout = new FormLayout();
    final TextField usernameField = new TextField("Username");
    final TextField phoneField = new TextField("Phone number");
    final TextField emailField = new TextField("Email address");
    final TextField firstnameField = new TextField("First name");
    final TextField lastnameField = new TextField("Last name");
    final TextField countryField = new TextField("Country");
    final TextField cityField = new TextField("City");
    final TextField codeField = new TextField("Postal code");
    final TextField addressField = new TextField("Address");
    final PasswordField password1Field = new PasswordField("Password");
    final PasswordField repassword1Field = new PasswordField("Confirm Password");
    final ListSelect roleSelect = new ListSelect("Platform Role");
    final Button navigateToLoginButton = new Button("Proceed to Login Page");
    final Button registerButton = new Button("Register");
    final Sender sender = new Sender();
    final Button fillButton = new Button("Auto fill form");
    final Button fakeLoginButton = new Button("-Fake  Login-"); 
    final Map<String, ValidComponent> validComponentMap = new HashMap<String, ValidComponent>();

    @Override
    public void enter(ViewChangeEvent event) {
//        Notification.show("Welcome to mCloud");
    }

    public RegisterView() {

        navigateToLoginButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                navigator.navigateTo(Const.LOGIN_VIEW);
            }
        });

        registerButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {

                ViewUtils.messageLog(Level.FINEST, "-----------------------------new registration attempt ->", usernameField.getValue(), " ", password1Field.getValue());
                User find = null;
                try {
                    find = userAPI.find(usernameField.getValue());
                } catch (com.sun.jersey.api.client.UniformInterfaceException e) {
                    ViewUtils.messageLog(Level.FINEST, "userAPI.find thrown UniformInterfaceException", usernameField.getValue());
                } catch (com.sun.jersey.api.client.ClientHandlerException e) {
                    ViewUtils.dualMessage(Level.WARNING, footer, "userAPI.find thrown ClientHandlerException - connection problems");
                    return;
                }
                if (usernameField.getValue().length() < 4) {
                    ViewUtils.dualMessage(Level.FINEST, footer, "Username must be at least 4 characters long");
                    return;
                }
                if (find != null) {
                    ViewUtils.dualMessage(Level.FINEST, footer, "Username already registered on platform", find.getUid());
                    return;
                }

                try {
                    groupAPI.create(new Group(Const.DEVELOPER_GROUP));
                } catch (UniformInterfaceException e) {
                    ViewUtils.messageLog(Level.FINEST, "clientG.create -> Tried to create group in case it is not in ldap already", Const.DEVELOPER_GROUP);
                }
                try {
                    groupAPI.create(new Group(Const.CUSTOMER_GROUP));
                } catch (UniformInterfaceException e) {
                    ViewUtils.messageLog(Level.FINEST, "clientG.create -> Tried to create group in case it is not in ldap already", Const.CUSTOMER_GROUP);
                }

                if (!password1Field.getValue().equals(repassword1Field.getValue())) {
                    ViewUtils.dualMessage(Level.FINEST, footer, "Passowrd and Confirm Password fields must have the same values");
                    return;
                }

                User u = new User();
                u.setUid(usernameField.getValue());
                u.setCity(cityField.getValue());
                u.setCn(firstnameField.getValue());
                u.setDisplayname(usernameField.getValue());
                u.setGivenname(firstnameField.getValue());
                u.setSn(lastnameField.getValue());
                u.setMail(emailField.getValue());
                u.setMobile(phoneField.getValue());
                u.setPostalAddress(addressField.getValue());
                u.setEmailConfirmed(Boolean.FALSE);
                u.setSmsConfirmed(Boolean.FALSE);
                u.setPostalCode(codeField.getValue());
                try {
                    userAPI.setUsernamePassword(usernameField.getValue(), password1Field.getValue());
                } catch (UniformInterfaceException e) {
                    ViewUtils.dualMessage(Level.WARNING, footer, "User registration failed");
                    System.err.println("userAPI.setUsernamePassword "+e.getResponse().getEntity(String.class));
                    return;
                }
                try {
                    List<User> sameMail = userAPI.findAll(User.class, u.getMail(), null);
                    List<User> sameMobile = userAPI.findAll(User.class, null, u.getMobile());
                    if (!sameMail.isEmpty()) {
                        ViewUtils.dualMessage(Level.WARNING, footer, "This email is already registered on platform: " + u.getMail());
                        return;
                    }
                    if (!sameMobile.isEmpty()) {
                        ViewUtils.dualMessage(Level.WARNING, footer, "This phone number is already registered on platform: " + u.getMobile());
                        return;
                    }
                } catch (UniformInterfaceException e) {
                    System.err.println("userAPI.findAll "+e.getResponse().getEntity(String.class));
                }
//                    if (Boolean.FALSE == Const.TEST_ENABLED) {

//                    }
                try {
                    userAPI.create(u);
//                    userAPI.changePassword(new Password(password1Field.getValue()), usernameField.getValue());
                    ViewUtils.tripleMessage(Level.INFO, footer, "User registered on platform: ", u.getDisplayname());
                } catch (UniformInterfaceException e) {
                    ViewUtils.dualMessage(Level.WARNING, footer, "User registration failed", u.getDisplayname());
                    System.err.println("userAPI.create "+e.getResponse().getEntity(String.class));
                    return;
                }
                
                try {
//                    userAPI.create(u);
                    userAPI.changePassword(new Password(password1Field.getValue()), usernameField.getValue());
                    ViewUtils.tripleMessage(Level.INFO, footer, "User registered on platform: ", u.getDisplayname());
                } catch (UniformInterfaceException e) {
                    ViewUtils.dualMessage(Level.WARNING, footer, "User registration failed", u.getDisplayname());
                    System.err.println("userAPI.changePassword "+e.getResponse().getEntity(String.class));
                    return;
                }

                String recipient = u.getMail();
                try {
                    String mailCode = ViewUtils.generateRandomCode(32);
                    String smsCode = ViewUtils.generateRandomCode(32);
                    UserVerificationCode uvc = new UserVerificationCode(mailCode, smsCode);
                    userAPI.setVerificationCodes(u.getUid(), uvc);

                    StringBuilder mailText = new StringBuilder(128);
                    mailText.append(Const.REGISTRATION_MAIL_TEXT);
                    mailText.append(mailCode);
                    sender.sendMail(recipient, Const.REGISTRATION_MAIL_SUBJECT, mailText.toString());
                    ViewUtils.dualMessage(Level.INFO, footer, "Email has been send");

                    StringBuilder smsText = new StringBuilder(128);
                    smsText.append(Const.REGISTRATION_SMS_TEXT);
                    smsText.append(smsCode);
                    sender.sendSms(u.getMobile(), smsText.toString());
                    ViewUtils.dualMessage(Level.INFO, footer, "SMS has been send");
                } catch (MessagingException ex) {
                    ViewUtils.messageLog(Level.WARNING, "MessagingException");
                } catch (Exception e) {
                    ViewUtils.dualMessage(Level.WARNING, footer, "Sending verification problem occured");
                }
                try {
                    assignAPI.create(new GroupAssign(usernameField.getValue(), roleSelect.getValue().toString()));
                    ViewUtils.dualMessage(Level.INFO, footer, "User", usernameField.getValue(), "has been assigned to group", roleSelect.getValue().toString());
                    navigateToLoginButton.setEnabled(true);
                } catch (Exception e) {
                    ViewUtils.dualMessage(Level.WARNING, footer, "User", usernameField.getValue(), "has NOT been assigned to group", roleSelect.getValue().toString());
                }
            }
        }
        );

        fillButton.addClickListener(
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event
                    ) {
                        String username = usernameField.getValue();
                        phoneField.setValue("48123456789");
                        emailField.setValue(username + "@mcloud.com");
                        firstnameField.setValue(username);
                        lastnameField.setValue(username);
                        cityField.setValue("City of " + username);
                        countryField.setValue("Country of " + username);
                        codeField.setValue("123456");
                        addressField.setValue("Address of " + username);
                        password1Field.setValue(username + "123A$");
                        repassword1Field.setValue(username + "123A$");
                        roleSelect.select(Const.DEVELOPER_GROUP);
                    }
                }
        );

        fakeLoginButton.addClickListener(new Button.ClickListener() {

            public void buttonClick(Button.ClickEvent event) {
                VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogged", Boolean.TRUE);
                VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogin", Const.AUTOLOGIN_USERNAME);
                ViewUtils.messageLog(Level.FINEST, "User logged, session attrs set - userLogin:", VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userLogin").toString(), "userLogged:", VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userLogged").toString());
                navigator.addView(Const.WELCOME_VIEW, new WelcomeView());
                navigator.navigateTo(Const.WELCOME_VIEW);
            }
        });

        initComponents();
    }

    private void armRePwdField(final PasswordField field, final PasswordField refield, String desc, Layout target, Validator... validators) {
        validComponentMap.put(refield.getCaption(), new ValidComponent(refield, false));
        refield.setDescription(desc);
        CompositeValidator compositeValidator = new CompositeValidator(CompositeValidator.CombinationMode.OR, null);
        for (Validator validator : validators) {
            compositeValidator.addValidator(validator);
        }
        refield.addValidator(compositeValidator);
        target.addComponent(refield);
        refield.addBlurListener(new FieldEvents.BlurListener() {
            public void blur(FieldEvents.BlurEvent event) {
                try {
                    if (refield.getValue().trim().equals("")) {
                        throw new InvalidValueException(null);
                    } else if (!field.getValue().equals(refield.getValue())) {
                        throw new InvalidValueException(null);
                    } else {
                        refield.validate();
                    }
                    refield.setStyleName("v-textfield-ok");
                    validComponentMap.get(refield.getCaption()).setValid(Boolean.TRUE);
                    enableRegisterButtonAttempt();
                } catch (InvalidValueException e) {
                    refield.setStyleName("v-textfield-error");
                    validComponentMap.get(refield.getCaption()).setValid(Boolean.FALSE);
                    registerButton.setEnabled(false);
                }

            }
        });
    }

    private void armPwdField(final PasswordField field, String desc, Layout target, Validator... validators) {
        validComponentMap.put(field.getCaption(), new ValidComponent(field, false));
        field.setDescription(desc);
        CompositeValidator compositeValidator = new CompositeValidator(CompositeValidator.CombinationMode.OR, null);
        for (Validator validator : validators) {
            compositeValidator.addValidator(validator);
        }
        field.addValidator(compositeValidator);
        target.addComponent(field);
        field.addBlurListener(new FieldEvents.BlurListener() {
            public void blur(FieldEvents.BlurEvent event) {
                try {
                    if (field.getValue().trim().equals("")) {
                        throw new InvalidValueException(null);
                    } else {
                        field.validate();
                    }
                    field.setStyleName("v-textfield-ok");
                    validComponentMap.get(field.getCaption()).setValid(Boolean.TRUE);
                    enableRegisterButtonAttempt();
                } catch (InvalidValueException e) {
                    field.setStyleName("v-textfield-error");
                    validComponentMap.get(field.getCaption()).setValid(Boolean.FALSE);
                    registerButton.setEnabled(false);
                }

            }
        });
    }

    private void armTextField(final TextField field, String desc, Layout target, Validator... validators) {
        validComponentMap.put(field.getCaption(), new ValidComponent(field, false));
        field.setDescription(desc);
        CompositeValidator compositeValidator = new CompositeValidator(CompositeValidator.CombinationMode.OR, null);
        for (Validator validator : validators) {
            compositeValidator.addValidator(validator);
        }
        field.addValidator(compositeValidator);
        target.addComponent(field);
        field.addBlurListener(new FieldEvents.BlurListener() {
            public void blur(FieldEvents.BlurEvent event) {
                try {
                    if (field.getValue().trim().equals("")) {
                        throw new InvalidValueException(null);
                    } else {
                        field.validate();
                    }
                    field.setStyleName("v-textfield-ok");
                    validComponentMap.get(field.getCaption()).setValid(Boolean.TRUE);
                    enableRegisterButtonAttempt();
                } catch (InvalidValueException e) {
                    field.setStyleName("v-textfield-error");
                    validComponentMap.get(field.getCaption()).setValid(Boolean.FALSE);
                    registerButton.setEnabled(false);
                }

            }

        });
    }

    private void enableRegisterButtonAttempt() {
        for (String s : validComponentMap.keySet()) {
            if (validComponentMap.get(s).isValid().booleanValue() == Boolean.FALSE) {
                return;
            }
        }
        registerButton.setEnabled(true);
    }

    private void initComponents() {
        ViewUtils.generateHeaderFooter(header, footer);
        
        registerFormLayout.addStyleName("horiz");
        armTextField(usernameField, "4-32 letters required", registerFormLayout, new RegexpValidator("^[a-zA-Z]{4,32}$", false, "!"));
        if (Boolean.TRUE == Const.TEST_ENABLED) {
            registerFormLayout.addComponent(fillButton);
        }
        armTextField(phoneField, "11-13 digits required", registerFormLayout, new RegexpValidator("(\\d)?(\\d)?\\d{11}", "!"));
        armTextField(emailField, "valid email required", registerFormLayout, new EmailValidator("!"));
        armTextField(firstnameField, "2-32 letters required", registerFormLayout, new RegexpValidator("^[a-zA-Z-'` ]{2,32}$", "!"));
        armTextField(lastnameField, "2-32 letters required", registerFormLayout, new RegexpValidator("^[a-zA-Z-'` ]{2,32}$", "!"));
        armTextField(countryField, "2-32 letters required", registerFormLayout, new RegexpValidator("^[a-zA-Z-'` ]{2,32}$", "!"));
        armTextField(cityField, "2-32 letters required", registerFormLayout, new RegexpValidator("^[0-9a-zA-Z-'` ]{2,32}$", "!"));
        armTextField(codeField, "2-9 characters required", registerFormLayout, new RegexpValidator("^[0-9a-zA-Z-'` ]{2,9}$", "!"));
        armTextField(addressField, "4-64 characters required", registerFormLayout, new RegexpValidator("^[0-9a-zA-Z-'`./ ]{4,64}$", "!"));
        armPwdField(password1Field, "8 characters, at least one lowercase letter, one uppercase letter, one digit, one special symbol", registerFormLayout, new RegexpValidator("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$", "!"));
        armRePwdField(password1Field, repassword1Field, "required match with first password input", registerFormLayout, new RegexpValidator("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$", "!"));
        registerFormLayout.addComponent(roleSelect);
        roleSelect.addItem(Const.DEVELOPER_GROUP);
        roleSelect.addItem(Const.CUSTOMER_GROUP);
        roleSelect.setNullSelectionAllowed(false);
        roleSelect.select(Const.DEVELOPER_GROUP);
        roleSelect.setRows(1);
        registerFormLayout.addComponent(registerButton);
        if (Boolean.FALSE == Const.TEST_ENABLED) {
            registerButton.setEnabled(false);
        }
        registerFormLayout.addComponent(new Label(""));
        registerFormLayout.addComponent(navigateToLoginButton);
        if (Boolean.TRUE == Const.TEST_ENABLED) {
            registerFormLayout.addComponent(fakeLoginButton);
        }
        Panel light = new Panel("mCloud Registration", registerFormLayout);
        light.addStyleName(Runo.PANEL_LIGHT);
        layout.addComponent(light);
        this.setSizeFull();
        layout.setMargin(true);
        layout.setWidth(350, Sizeable.Unit.PIXELS);
        
        ViewUtils.attachHeader(this, header);
        
        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.addComponent(layout);
        this.setExpandRatio(layout, 80);
        
        ViewUtils.attachFooter(this, footer);      
    }
}
