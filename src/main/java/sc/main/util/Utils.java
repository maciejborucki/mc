/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sc.main.util;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import sc.main.view.MenuView;

//import pl.mi.mcloud.selfcare.util.Const;
//import pl.mlife.mcloud.integration.ldap.entity.User;

/**
 *
 * @author bor
 */
public class Utils {

    private static Navigator navigator;
    final private static AbsoluteLayout footer = new AbsoluteLayout();
    final private static AbsoluteLayout header = new AbsoluteLayout();
    final private static HorizontalLayout menuBar = new MenuView();

    private final static Logger logger = Logger.getLogger(Utils.class.getName());

//    static {
//        header.setWidth(100, Sizeable.Unit.PERCENTAGE);
//        Label title = new Label("mCloud");
//        title.setStyleName("title-label");
//        header.addComponent(title);
//        header.setPrimaryStyleName("header-style");
////        Const.header.setHeight(50, Sizeable.Unit.PIXELS);
//
////        Const.header.addComponent(new Label("header"));
//        footer.setWidth(100, Sizeable.Unit.PERCENTAGE);
//        footer.setPrimaryStyleName("footer-style");
////        Const.footer.setHeight(150, Sizeable.Unit.PIXELS);
//
//        logger.log(Level.FINEST, "createFooterHeaderMenu called");
//    }

    public static String generateRandomCode(int bits) {
        return new BigInteger(bits, new SecureRandom()).toString();
    }

    public static void checkAuthorization() {
        if (VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userLogged") == Boolean.FALSE) {
            Utils.getNavigator().navigateTo(Const.LOGOUT_VIEW);
            Utils.logWarning("checkAuthorization failed", false);
            VaadinService.getCurrent().findUI(VaadinService.getCurrentRequest()).close();
        }
    }

    public static void setNavigator(Navigator navigator) {
        Utils.navigator = navigator;
    }

    public static Navigator getNavigator() {
        return Utils.navigator;
    }

    public static void logInfo(String msg, boolean showOnUI) {
        logger.log(Level.INFO, msg);
        if (showOnUI) {
            Utils.notification(msg);
        }
    }

    public static void logWarning(String msg, boolean showOnUI) {
        logger.log(Level.WARNING, msg);
        if (showOnUI) {
            Utils.notification(msg);
        }
    }

    public static void logSevere(String msg, boolean showOnUI) {
        logger.log(Level.SEVERE, msg);
        if (showOnUI) {
            Utils.notification(msg);
        }
    }

    public static void notification(String msg) {
        Notification.show(msg, Notification.Type.HUMANIZED_MESSAGE);
    }

    public static void messageLog(Level level, String msg, String... args) {
        logger.setLevel(Const.LOG_LEVEL);
        StringBuilder msgWithArgs = new StringBuilder(256);
        msgWithArgs.append(msg);
        for (String string : args) {
            msgWithArgs.append(" ");
            msgWithArgs.append(string);
        }
        logger.log(level, msgWithArgs.toString());
    }

    public static void wrapView(VerticalLayout main, Component part) {

        main.setSizeFull();

        main.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        main.addComponent(header);
        main.setExpandRatio(header, 5);

        main.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        main.addComponent(menuBar);
        main.setExpandRatio(menuBar, 5);

        main.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        main.addComponent(part);
        main.setExpandRatio(part, 75);

        main.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
        main.addComponent(footer);
        main.setExpandRatio(footer, 15);
        
        header.setWidth(100, Sizeable.Unit.PERCENTAGE);
        Label title = new Label("mCloud");
        title.setStyleName("title-label");
        header.addComponent(title);
        header.setPrimaryStyleName("header-style");
//        Const.header.setHeight(50, Sizeable.Unit.PIXELS);

//        Const.header.addComponent(new Label("header"));
        footer.setWidth(100, Sizeable.Unit.PERCENTAGE);
        footer.setPrimaryStyleName("footer-style");
    }

//    public static void attachFooterHeaderMenu(Layout target, boolean enableMenu) {
//        target.addComponent(header);
//        target.addComponent(menuBar);
//        target.addComponent(footer);
////        header.setWidth(100, Sizeable.Unit.PERCENTAGE);
////        Label title = new Label("mCloud");
////        title.setStyleName("title-label");
////        header.addComponent(title);
////        header.setPrimaryStyleName("header-style");
//////        Const.header.setHeight(50, Sizeable.Unit.PIXELS);
////
//////        Const.header.addComponent(new Label("header"));
////        footer.setWidth(100, Sizeable.Unit.PERCENTAGE);
////        footer.setPrimaryStyleName("footer-style");
//////        Const.footer.setHeight(150, Sizeable.Unit.PIXELS);
////
////        logger.log(Level.FINEST, "createFooterHeaderMenu called");
//    }

    public static AbsoluteLayout getFooter() {
        return footer;
    }

    public static AbsoluteLayout getHeader() {
        return header;
    }

    public static HorizontalLayout getMenuBar() {
        return menuBar;
    }
}
