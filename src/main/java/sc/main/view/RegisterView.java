package sc.main.view;

//import com.sun.jersey.api.client.UniformInterfaceException;
import com.vaadin.data.Validatable;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;
import java.security.MessageDigest;
import java.util.Map.Entry;

import sc.main.EJB;
import sc.main.entity.AccessGroup;
import sc.main.entity.PlatformUser;
import sc.main.exception.MCloudException;
import sc.main.util.Const;
import sc.main.util.CountryCodes;
import sc.main.util.Sender;
import sc.main.util.Utils;
import sun.misc.BASE64Encoder;

/**
 *
 * @author bor
 */
public class RegisterView extends VerticalLayout implements View {

    /*LAYOUT*/
    final VerticalLayout main = new VerticalLayout();
    final FormLayout formLayout = new FormLayout();
    final Panel panel = new Panel("mCloud Registration");

    /* FIELDS - BUTTONS */
    final TextField usernameField = new TextField("Username");
    final TextField phoneField = new TextField("Phone number");
    final TextField emailField = new TextField("Email address");
    final TextField firstnameField = new TextField("First name");
    final TextField lastnameField = new TextField("Last name");
    final ListSelect countrySelect = new ListSelect("Country");
    final TextField cityField = new TextField("City");
    final TextField codeField = new TextField("Postal code");
    final TextField addressField = new TextField("Address");
    final PasswordField passwordField = new PasswordField("Password");
    final ListSelect roleSelect = new ListSelect("Platform Role");
    final Button navigateToLoginButton = new Button("Skip to Login Page");
    final Button registerButton = new Button("Register");
    final Button fillButton = new Button("Fill");

    /* OTHERS */
    final Sender sender = new Sender();

    @Override
    public void enter(ViewChangeEvent event) {
        Utils.logInfo("RegisterView enter(ViewChangeEvent event)", false);
//        Notification.show("Welcome to mCloud");
        Utils.wrapView(this, main);
    }

    public RegisterView() {
        Utils.logInfo("RegisterView RegisterView()", false);
        initComponents();
    }

    private void initComponents() {
        Utils.logInfo("RegisterView initComponents()", false);
        this.constructRegisterForm();
        this.addStyles();
        panel.setContent(formLayout);
        main.addComponent(panel);
//        Utils.attachFooterHeaderMenu(false);
//        Utils.wrapView(this, main);
    }

    private void addStyles() {
        Utils.logInfo("RegisterView addStyles()", false);
        panel.addStyleName(Runo.PANEL_LIGHT);
        main.setMargin(true);
        main.setWidth(350, Sizeable.Unit.PIXELS);
    }

    private void constructRegisterForm() {
        Utils.logInfo("RegisterView constructRegisterForm()", false);
        formLayout.addComponent(usernameField);
        formLayout.addComponent(roleSelect);
        formLayout.addComponent(phoneField);
        formLayout.addComponent(emailField);
        formLayout.addComponent(firstnameField);
        formLayout.addComponent(lastnameField);
        formLayout.addComponent(countrySelect);
        formLayout.addComponent(cityField);
        formLayout.addComponent(codeField);
        formLayout.addComponent(addressField);
        formLayout.addComponent(passwordField);
        formLayout.addComponent(registerButton);
        formLayout.addComponent(navigateToLoginButton);
        if (Const.TEST_ENABLED) {
            formLayout.addComponent(fillButton);
        }
        this.fillFormWithData();
        this.addValidation();
        this.addButtonLogic();
    }

    private void fillFormWithData() {
        Utils.logInfo("RegisterView fillFormWithData()", false);
        CountryCodes countryCodes = new CountryCodes();
        roleSelect.addItem(Const.DEVELOPER_GROUP);
        roleSelect.addItem(Const.CUSTOMER_GROUP);
        roleSelect.setNullSelectionAllowed(false);
        roleSelect.select(Const.CUSTOMER_GROUP);
        roleSelect.setRows(1);
        for (Entry country : countryCodes.getMap().entrySet()) {
            countrySelect.addItem(country.getKey());
        }
        countrySelect.setNullSelectionAllowed(false);
        countrySelect.select(Const.DEFAULT_COUNTRY);
        countrySelect.setRows(1);
    }

    private void addValidation() {
        addValidatorToField(usernameField, (new RegexpValidator(Const.REGEXP_USERNAME, true, Const.MESSAGE_INVALID_INPUT)));
        addValidatorToField(emailField, new EmailValidator(Const.MESSAGE_INVALID_INPUT));
        addValidatorToField(phoneField, new RegexpValidator(Const.REGEXP_PHONE_NUMBER, true, Const.MESSAGE_INVALID_INPUT));
        addValidatorToField(firstnameField, new RegexpValidator(Const.REGEXP_ALPHA, true, Const.MESSAGE_INVALID_INPUT));
        addValidatorToField(lastnameField, new RegexpValidator(Const.REGEXP_ALPHA, true, Const.MESSAGE_INVALID_INPUT));
        addValidatorToField(cityField, new RegexpValidator(Const.REGEXP_ALPHA, true, Const.MESSAGE_INVALID_INPUT));
        addValidatorToField(codeField, new RegexpValidator(Const.REGEXP_ALPHANUMERIC, true, Const.MESSAGE_INVALID_INPUT));
        addValidatorToField(addressField, new RegexpValidator(Const.REGEXP_ALPHANUMERIC, true, Const.MESSAGE_INVALID_INPUT));
        addValidatorToField(passwordField, new RegexpValidator(Const.REGEXP_PASSWORD, true, Const.MESSAGE_INVALID_INPUT));
    }

    private void addValidatorToField(Validatable validatable, Validator validator) {
        if (validatable == null) {
            throw new RuntimeException();
        }
        validatable.addValidator(validator);
    }

    private boolean isFormFilled() {
        if (isEmpty(usernameField)
                || isEmpty(emailField)
                || isEmpty(phoneField)
                || isEmpty(firstnameField)
                || isEmpty(lastnameField)
                || isEmpty(cityField)
                || isEmpty(codeField)
                || isEmpty(addressField)
                || isEmpty(passwordField)) {
            return false;
        }
        return true;
    }

    private boolean isEmpty(Field field) {
        String v = (String) field.getValue();
        if (v.trim().equals("")) {
            return true;
        }
        return false;
    }

    private void addButtonLogic() {
        registerButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (!RegisterView.this.isFormFilled()) {
                    Notification.show("Please fill all information to create an account");
                } else {
                    PlatformUser u = new PlatformUser();
                    String mailCode = Utils.generateRandomCode(32);
                    String smsCode = Utils.generateRandomCode(32);
                    try {
                        AccessGroup groupName = EJB.accessGroupFacade.findByGroupName(roleSelect.getValue().toString());
                        u.setAccessGroup116(groupName.getGroupName());
                        u.setUsername(usernameField.getValue());
                        u.setEmail(emailField.getValue());
                        u.setPhone(phoneField.getValue());
                        u.setFirstname(firstnameField.getValue());
                        u.setLastname(lastnameField.getValue());
                        u.setCountry(countrySelect.getValue().toString());
                        u.setCity(cityField.getValue());
                        u.setCode(codeField.getValue());
                        u.setAddress(addressField.getValue());
                        u.setIsEmailConfirmed(mailCode);
                        u.setIsPhoneConfirmed(smsCode);
                        u.setPassword(hashPassword(passwordField.getValue(), usernameField.getValue()));
                        EJB.platformUserFacade.create(u); /*TODO check if exists, login, email, phone, username */

                    } catch (MCloudException mce) {
                        Utils.logWarning("Registration failed - " + mce.getMessage(), true);
                        return;
                    } catch (Exception ex) {
                        Utils.logWarning("Registration error", true);
                        return;
                    }
                    RegisterView.this.sendConfirmationMessages(u);
                    Utils.logInfo("Registration finished. Please log in.", true);
                    Utils.getNavigator().navigateTo(Const.LOGIN_VIEW);
                }
            }
        });

        navigateToLoginButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Utils.getNavigator().navigateTo(Const.LOGIN_VIEW);
            }
        });

        fillButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                usernameField.setValue("testuser");
                addressField.setValue("Fajna 6");
                cityField.setValue("Kosovo");
                codeField.setValue("7821");
                emailField.setValue("test@test.tst");
                firstnameField.setValue("Igor");
                lastnameField.setValue("Abramov");
                phoneField.setValue("19231123231");
                passwordField.setValue("Emlajf123$%^");
            }
        });
    }

    private String hashPassword(String password, String username) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String input = Const.SALT.concat(username).concat(password);
            md.update(input.getBytes("UTF-8"));
            byte[] digest = md.digest();
            Utils.logInfo("Password hash " + digest.toString(), false);
            String digestString = (new BASE64Encoder()).encode(digest);
            return digestString;
        } catch (Exception e) {
            Utils.logSevere("Password error", false);
            throw new MCloudException("Password error");
        }
    }

    private void sendConfirmationMessages(PlatformUser u) {
        String recipient = u.getEmail();
        try {
//                    String mailCode = Utils.generateRandomCode(32);
//                    String smsCode = Utils.generateRandomCode(32);
//                    UserVerificationCode uvc = new UserVerificationCode(mailCode, smsCode);
//                    userAPI.setVerificationCodes(u.getUid(), uvc);

            StringBuilder mailText = new StringBuilder(128);
            mailText.append(pl.mi.mcloud.selfcare.util.Const.REGISTRATION_MAIL_TEXT);
            mailText.append(u.getIsEmailConfirmed());
            sender.sendMail(recipient, Const.REGISTRATION_MAIL_SUBJECT, mailText.toString());
            Utils.logInfo("Email has been send", true);

            StringBuilder smsText = new StringBuilder(128);
            smsText.append(Const.REGISTRATION_SMS_TEXT);
            smsText.append(u.getIsPhoneConfirmed());
            sender.sendSms(u.getPhone(), smsText.toString());
            Utils.logInfo("Sms has been send", true);
        } catch (Exception e) {
            Utils.logSevere("Sending code error", true);
        }
//                try {
//                    assignAPI.create(new GroupAssign(usernameField.getValue(), roleSelect.getValue().toString()));
//                    pl.mi.mcloud.selfcare.view.util.ViewUtils.dualMessage(Level.INFO, pl.mi.mcloud.selfcare.util.Const.footer, "User", usernameField.getValue(), "has been assigned to group", roleSelect.getValue().toString());
//                    navigateToLoginButton.setEnabled(true);
//                } catch (Exception e) {
//                    pl.mi.mcloud.selfcare.view.util.ViewUtils.dualMessage(Level.WARNING, pl.mi.mcloud.selfcare.util.Const.footer, "User", usernameField.getValue(), "has NOT been assigned to group", roleSelect.getValue().toString());
//                }
    }

}
