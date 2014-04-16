/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sc.main.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;
import sc.main.EJB;
import sc.main.entity.PlatformUser;
import sc.main.exception.MCloudException;
import sc.main.util.Const;
import sc.main.util.Utils;

/**
 *
 * @author bor
 */
public class LoginView extends VerticalLayout implements View {

    /*LAYOUT*/
    final VerticalLayout main = new VerticalLayout();
    final FormLayout formLayout = new FormLayout();
    final Panel panel = new Panel("mCloud Login");

    /* FIELDS - BUTTONS */
    final FormLayout loginFormLayout = new FormLayout();
    final TextField usernameField = new TextField("Username");
    final PasswordField passwordField = new PasswordField("Password");
    final Button loginButton = new Button("Authenticate");
    final Button navigateToRegistrationButton = new Button("Register on platform");
    final Button fakeLoginButton = new Button("Login immediate");

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Utils.logInfo("LoginView enter(ViewChangeEvent event)", false);
//        Utils.attachFooterHeaderMenu(this, false);
        Utils.wrapView(this, main);
    }

    public LoginView() {
        Utils.logInfo("LoginView RegisterView()", false);
        initComponents();
    }

    private void initComponents() {
        Utils.logInfo("LoginView initComponents()", false);
        this.constructLoginForm();
        this.addStyles();
        panel.setContent(formLayout);
        main.addComponent(panel);
//        Utils.attachFooterHeaderMenu(false);
        
    }

    private void constructLoginForm() {
        Utils.logInfo("LoginView constructRegisterForm()", false);
        formLayout.addComponent(usernameField);
        formLayout.addComponent(passwordField);
        formLayout.addComponent(loginButton);
        formLayout.addComponent(navigateToRegistrationButton);
        if (Const.TEST_ENABLED) {
            formLayout.addComponent(fakeLoginButton);
        }
        this.addButtonLogic();
    }

    private void addStyles() {
        Utils.logInfo("LoginView addStyles()", false);
        panel.addStyleName(Runo.PANEL_LIGHT);
        main.setMargin(true);
        main.setWidth(350, Sizeable.Unit.PIXELS);
    }

    private void addButtonLogic() {
        loginButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (!LoginView.this.isFormFilled()) {
                    Notification.show("Please enter your username and password");
                } else {
                    try {
                        LoginView.this.tryToLogin();
                    } catch (MCloudException mce) {
                        Utils.logWarning("Wrong credentials", true);
                        return;
                    } catch (Exception ex) {
                        Utils.logSevere("Login error", true);
                        return;
                    }
                    LoginView.this.buildViews();
                    Utils.getNavigator().navigateTo(Const.WELCOME_VIEW);
                }
            }

        });

        navigateToRegistrationButton.addClickListener(
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event
                    ) {
                        Utils.getNavigator().navigateTo(Const.REGISTER_VIEW);
                    }
                }
        );

        fakeLoginButton.addClickListener(
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        String username = Const.AUTOLOGIN_USERNAME;
                        Utils.logInfo(username + " logged in", true);
                        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogged", Boolean.TRUE);
                        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("username", username);
                        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userid", 0);
                        
                        LoginView.this.buildViews();
                        Utils.getNavigator().navigateTo(Const.WELCOME_VIEW);
                    }
                }
        );
    }

    private void tryToLogin() throws MCloudException, Exception {
        PlatformUser platformUser = null;
        try {
            platformUser = EJB.platformUserFacade.findByUsernameAndPassword(usernameField.getValue(), passwordField.getValue());
        } catch (Exception e) {
            throw e;
        }
        if (platformUser == null) {
            throw new MCloudException("Login failed");
        } else {
            Utils.logInfo(platformUser.getUsername() + " logged in", true);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogged", Boolean.TRUE);
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("username", platformUser.getUsername());
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userid", platformUser.getId());
        }
    }

    private boolean isFormFilled() {
        if (isEmpty(usernameField)
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

    private void buildViews() {
        Utils.getNavigator().addView(Const.WELCOME_VIEW, new WelcomeView());
        Utils.getNavigator().addView(Const.LOGOUT_VIEW, new LogoutView());
        Utils.getNavigator().addView(Const.PERSONAL_DATA_VIEW, new PersonalDataView());

//        Utils.getNavigator().addView(Const.NEW_REQUEST_VIEW, new NewRequestView());
//        Utils.getNavigator().addView(Const.LIST_REQUEST_VIEW, new ListRequestView());
//        Utils.getNavigator().addView(Const.EDIT_REQUEST_VIEW, new EditRequestView());
//
//        Utils.getNavigator().addView(Const.NEW_COMPLAINT_VIEW, new NewComplaintView());
//        Utils.getNavigator().addView(Const.LIST_COMPLAINT_VIEW, new ListComplaintView());
//        Utils.getNavigator().addView(Const.EDIT_COMPLAINT_VIEW, new EditComplaintView());
    }
}
