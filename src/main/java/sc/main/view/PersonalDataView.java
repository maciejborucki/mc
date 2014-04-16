/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sc.main.view;

import com.vaadin.data.Validatable;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;
import java.util.Map.Entry;
import sc.main.EJB;
import sc.main.entity.PlatformUser;
import sc.main.util.Const;
import sc.main.util.CountryCodes;
import sc.main.util.Utils;

/**
 *
 * @author bor
 */
class PersonalDataView extends VerticalLayout implements View {

    /*LAYOUT*/
    final VerticalLayout main = new VerticalLayout();
    final FormLayout formLayout = new FormLayout();
    final Panel panel = new Panel("mCloud personal data");
    final GridLayout grid = new GridLayout(5, 6);

    /* FIELDS - BUTTONS */
    final TextField usernameField = new TextField("Username");
    final TextField phoneField = new TextField("Phone number");
    final TextField emailField = new TextField("Email address");
    final TextField firstnameField = new TextField("First name");
    final TextField lastnameField = new TextField("Last name");
    final TextField cityField = new TextField("City");
    final TextField codeField = new TextField("Postal code");
    final TextField addressField = new TextField("Address");
    final ListSelect countrySelect = new ListSelect("Country");
    final PasswordField passwordField = new PasswordField("New password");
    final TextField phoneVerificationField = new TextField("Phone verification code");
    final TextField emailVerificationField = new TextField("Email verification code");
    final Button verifyEmailButton = new Button("Verify email");
    final Button changeAddressButton = new Button("Change address");
    final Button changePasswordButton = new Button("Change password");
    final Button verifyPhoneButton = new Button("Verify phone");

    /* OTHERS */
    private PlatformUser user = null;

    public PersonalDataView() {
        Utils.logInfo("PersonalDataView RegisterView()", false);
        initComponents();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Utils.logInfo("PersonalDataView enter(ViewChangeEvent event)", false);
        Utils.checkAuthorization();
        try {
            String username = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("username").toString();
            Utils.logInfo("PersonalDataView "+username, false);
            user = EJB.platformUserFacade.findByUsername(username).get(0);
        } catch (Exception e) {
            Utils.logWarning("PersonalDataView enter(ViewChangeEvent event) Exception ", false);
            user = null;
        }

        if (user == null) {
            Utils.logSevere("PersonalDataView enter user==null", false);
            Utils.getNavigator().navigateTo(Const.LOGOUT_VIEW);
        }
//        this.initComponents();
        Utils.logInfo("PersonalDataView "+user, false);
        usernameField.setValue(user.getUsername());
        phoneField.setValue(user.getPhone());
        emailField.setValue(user.getEmail());
        firstnameField.setValue(user.getFirstname());
        lastnameField.setValue(user.getLastname());
        cityField.setValue(user.getCity());
        codeField.setValue(user.getCode());
        addressField.setValue(user.getAddress());
        countrySelect.select(user.getCountry());

        Utils.wrapView(this, main);
    }

    private void initComponents() {
        Utils.logInfo("PersonalDataView initComponents()", false);
        Utils.getMenuBar().setEnabled(false);
        this.constructPersonalDataForm();
        this.addStyles();
//        panel.setContent(formLayout);
        main.addComponent(panel);
        main.addComponent(grid);
        
    }

    private void constructPersonalDataForm() {
        Utils.logInfo("PersonalDataView constructPersonalDataForm()", false);

        

//        main.addComponent(grid);

        grid.addComponent(usernameField);
        grid.addComponent(phoneField);
        grid.addComponent(emailField);
        grid.addComponent(new Label(""));
        grid.addComponent(new Label(""));

        grid.addComponent(firstnameField);
        grid.addComponent(lastnameField);
        grid.addComponent(new Label(""));
        grid.addComponent(new Label(""));
        grid.addComponent(new Label(""));

        grid.addComponent(cityField);
        grid.addComponent(codeField);
        grid.addComponent(addressField);
        grid.addComponent(countrySelect);
        grid.addComponent(changeAddressButton);

        grid.addComponent(passwordField);        
        grid.addComponent(new Label(""));
        grid.addComponent(new Label(""));
        grid.addComponent(new Label(""));
        grid.addComponent(changePasswordButton);

        grid.addComponent(phoneVerificationField);       
        grid.addComponent(new Label(""));
        grid.addComponent(new Label(""));
        grid.addComponent(new Label(""));
        grid.addComponent(verifyPhoneButton);

        grid.addComponent(emailVerificationField);        
        grid.addComponent(new Label(""));
        grid.addComponent(new Label(""));
        grid.addComponent(new Label(""));
        grid.addComponent(verifyEmailButton);

        usernameField.setEnabled(false);
        phoneField.setEnabled(false);
        emailField.setEnabled(false);
        firstnameField.setEnabled(false);
        lastnameField.setEnabled(false);
        
        this.fillFormWithData();
        this.addValidation();
        this.addButtonLogic();
    }

    private void addStyles() {
        verifyEmailButton.setStyleName("alignButtonDown");
        changeAddressButton.setStyleName("alignButtonDown");
        changePasswordButton.setStyleName("alignButtonDown");
        verifyPhoneButton.setStyleName("alignButtonDown");
        panel.addStyleName(Runo.PANEL_LIGHT);
        main.setMargin(true);
        main.setWidth(1000, Sizeable.Unit.PIXELS);
    }

    private void addButtonLogic() {
        changeAddressButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
//                user.setCity(cityField.getValue());
//                user.setPostalCode(codeField.getValue());
//                user.setPostalAddress(addressField.getValue());
//                user.setCo(countryField.getValue());
//                userAPI.edit(user);
//                ViewUtils.tripleMessage(Level.INFO, footer, "User address has been changed: ", user.getDisplayname());
//                PersonalDataView.this.markAsDirtyRecursive();
            }
        }); 
    }

    private void fillFormWithData() {
        Utils.logInfo("PersonalDataView fillFormWithData()", false);
        CountryCodes countryCodes = new CountryCodes();
        for (Entry country : countryCodes.getMap().entrySet()) {
            countrySelect.addItem(country.getKey());
        }
        countrySelect.setNullSelectionAllowed(false);
        
        countrySelect.setRows(1);
    }

    private void addValidation() {
        addValidatorToField(cityField, new RegexpValidator(Const.REGEXP_ALPHA, true, Const.MESSAGE_INVALID_INPUT));
        addValidatorToField(codeField, new RegexpValidator(Const.REGEXP_ALPHANUMERIC, true, Const.MESSAGE_INVALID_INPUT));
        addValidatorToField(addressField, new RegexpValidator(Const.REGEXP_ALPHANUMERIC, true, Const.MESSAGE_INVALID_INPUT));
    }
    
    private void addValidatorToField(Validatable validatable, Validator validator) {
        if (validatable == null) {
            throw new RuntimeException();
        }
        validatable.addValidator(validator);
    }

}
