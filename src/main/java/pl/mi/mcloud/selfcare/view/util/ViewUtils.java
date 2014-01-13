/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mi.mcloud.selfcare.view.util;

import pl.mi.mcloud.selfcare.view.*;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.mi.mcloud.selfcare.util.Const;
//import pl.mlife.mcloud.integration.ldap.entity.User;

/**
 *
 * @author bor
 */
public class ViewUtils {

    private static Navigator navigator;
//    private static Layout footer;
//    private static Layout header;

//    public static Layout getHeader() {
//        return header;
//    }
//
//    public static void setHeader(Layout header) {
//        Utils.header = header;
//    }
    private final static Logger logger = Logger.getLogger(ViewUtils.class.getName());

    public static String generateRandomCode(int bits) {
        return new BigInteger(bits, new SecureRandom()).toString();
    }

    public static void checkAuthorization(UI ui) {
        if (VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userLogged") == Boolean.FALSE) {
            ui.close();
        }
    }

    public static void setNavigator(Navigator navigator) {
        ViewUtils.navigator = navigator;
    }

    public static Navigator getNavigator() {
        return ViewUtils.navigator;
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

    public static void messageUI(Layout footer, String msg, String... args) {
        StringBuilder msgWithArgs = new StringBuilder(256);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        msgWithArgs.append(dateFormat.format(cal.getTime()));
        msgWithArgs.append("> ");
        msgWithArgs.append(msg);
        msgWithArgs.append(" ");     
        for (String string : args) {
            msgWithArgs.append(" ");
            msgWithArgs.append(string);
        }
        
        Iterator<Component> iterator = footer.iterator();
        List<Component> components = new ArrayList<Component>();
        while (iterator.hasNext()) {
//            System.out.println("obiket");
            components.add(iterator.next());
        }
        footer.removeAllComponents();
        footer.addComponent(new Label(msgWithArgs.toString()));
//        footer.addComponent(new Label("-"));
        for (Component component : components) {
            footer.addComponent(component);
        }
        
//        footer.addComponents((Component[])components.toArray());
    }

    public static void notification(String msg, String... args) {
        StringBuilder msgWithArgs = new StringBuilder(256);
        msgWithArgs.append(msg);
        for (String string : args) {
            msgWithArgs.append(" ");
            msgWithArgs.append(string);
        }
        Notification.show(msgWithArgs.toString(), Notification.Type.TRAY_NOTIFICATION);
    }

    public static void dualMessage(Level level, Layout footer, String msg, String... args) {
        messageLog(level, msg, args);
        messageUI(footer, msg, args);
    }

    public static void tripleMessage(Level level, Layout footer, String msg, String... args) {
        messageLog(level, msg, args);
        messageUI(footer, msg, args);
        notification(msg, args);
    }

//    public static boolean validateUserObject(User userToValidate) {
//        return true;
//    }

    public static void generateHeaderFooter(Layout header, Layout footer) {
        header.setWidth(100, Sizeable.Unit.PERCENTAGE);
        Label title = new Label("mCloud");
        title.setStyleName("title-label");
        header.addComponent(title);
        header.setPrimaryStyleName("header-style");

        footer.setWidth(100, Sizeable.Unit.PERCENTAGE);
        footer.setPrimaryStyleName("footer-style");
    }

    public static void attachHeader(VerticalLayout target, AbsoluteLayout header) {
        target.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        target.addComponent(header);
        target.setExpandRatio(header, 5);
    }

    public static void attachFooter(VerticalLayout target, AbsoluteLayout footer) {
        target.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
        target.addComponent(footer);
        target.setExpandRatio(footer, 15);
    }
}
