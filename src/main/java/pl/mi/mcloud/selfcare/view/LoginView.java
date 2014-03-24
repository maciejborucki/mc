/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mi.mcloud.selfcare.view;

import com.sun.jersey.api.client.UniformInterfaceException;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;
import java.util.List;
import java.util.logging.Level;
import mcloud.integration.ldap.client.LdapUserClient;
import pl.mi.mcloud.selfcare.util.Const;
import pl.mi.mcloud.selfcare.view.util.Utils;
import pl.mi.mcloud.selfcare.view.util.ViewUtils;
import pl.mlife.mcloud.integration.ldap.entity.Password;
import pl.mlife.mcloud.integration.ldap.entity.User;

/**
 *
 * @author bor
 */
public class LoginView extends VerticalLayout implements View {

//    private final static Logger logger = Logger.getLogger(LoginView.class.getName());
    final LdapUserClient userAPI = new LdapUserClient(Const.BASE_URI, Const.MEDIA_TYPE);
//    final AbsoluteLayout footer = new AbsoluteLayout();
//    final AbsoluteLayout header = new AbsoluteLayout();
    final Navigator navigator = ViewUtils.getNavigator();
    final VerticalLayout layout = new VerticalLayout();
    final FormLayout loginFormLayout = new FormLayout();
    final TextField loginField = new TextField("Login");
    final PasswordField passwordField = new PasswordField("Password");
    final Button loginButton = new Button("Authenticate");
    final Button navigateToRegistrationButton = new Button("Register on platform");

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
//        Notification.show("Welcome to mCloud");
    }

    public LoginView() {

        loginButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    Utils.login(loginField.getValue(), passwordField.getValue());
//                    userAPI.check(loginField.getValue(), new Password(passwordField.getValue()));
//                    VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogged", Boolean.TRUE);
//                    VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogin", loginField.getValue());
//                    ViewUtils.messageLog(Level.FINEST, "User logged, session attrs set - userLogin:", VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userLogin").toString(), "userLogged:", VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userLogged").toString());
//                    navigator.addView(Const.WELCOME_VIEW, new WelcomeView());
                    navigator.navigateTo(Const.WELCOME_VIEW);
                } catch (UniformInterfaceException e) {
                    ViewUtils.dualMessage(Level.FINEST, Const.footer, "User credentials are not valid");
                } catch (Exception e) {
                    ViewUtils.dualMessage(Level.FINEST, Const.footer, "User login attempt failed");
//                    e.printStackTrace();
                }
            }
        });

        navigateToRegistrationButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                navigator.navigateTo(Const.REGISTER_VIEW);
            }
        });

        initComponents();
    }

    private void initComponents() {
//        ViewUtils.generateHeaderFooter(header, footer);

        layout.setMargin(true);
        addComponent(layout);
//        loginFormLayout.addComponent(new Label("PLATFORM USER LOGIN"));
        loginFormLayout.addComponent(loginField);
        loginFormLayout.addComponent(passwordField);
        loginFormLayout.addComponent(loginButton);
        loginFormLayout.addComponent(new Label(""));
        loginFormLayout.addComponent(navigateToRegistrationButton);
        Panel light = new Panel("Login", loginFormLayout);
        light.addStyleName(Runo.PANEL_LIGHT);

        layout.addComponent(light);
        this.setSizeFull();

        layout.setMargin(true);
        layout.setWidth(350, Sizeable.Unit.PIXELS);

        ViewUtils.attachHeader(this, Const.header);

        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        this.addComponent(layout);
        this.setExpandRatio(layout, 80);

        ViewUtils.attachFooter(this, Const.footer);

        if (Boolean.TRUE == Const.TEST_ENABLED) {
            List<User> list = userAPI.findAll(User.class, null, null);
            for (User user : list) {
                ViewUtils.messageLog(Level.FINEST, user.getUid(), " ", user.getUserpassword());
            }
        }
    }
}
