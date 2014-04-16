/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sc.main.util;

//import com.vaadin.ui.UI;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.HorizontalLayout;
import java.util.logging.Level;
import mcloud.integration.ldap.client.LdapAssignClient;
import mcloud.integration.ldap.client.LdapGroupClient;
import mcloud.integration.ldap.client.LdapUserClient;
import pl.mi.mcloud.selfcare.view.MenuView;
//import mcloud.integration.ldap.client.LdapAssignClient;
//import mcloud.integration.ldap.client.LdapGroupClient;
//import mcloud.integration.ldap.client.LdapUserClient;

/**
 *
 * @author bor
 */
public interface Const {
    static final Boolean TEST_ENABLED = Boolean.TRUE;
//    static final Boolean TEST_ENABLED = Boolean.FALSE;
    static final Level LOG_LEVEL = Level.ALL;
    
    
     
    
    
    static final String DEFAULT_COUNTRY = "Poland";
    static final String AUTOLOGIN_USERNAME = "testuser";
    static final Integer AUTOLOGIN_ID = new Integer(2);
    static final String DEFAULT_ACCESS_GROUP_NAME = "defaultAccessGroup";
    static final String DEFAULT_ACCESS_GROUP_STRING = "default_access";
    static final String DEFAULT_PRIORITY_STRING = "NORMAL";
    static final String DEFAULT_STATUS_STRING = "FRESH";
    
    //final static String BASE_URI = "http://192.168.20.71:8080/ldap/api";
    final static String BASE_URI = "http://127.0.0.1:6644/ldap/api";
    final static String MEDIA_TYPE = javax.ws.rs.core.MediaType.APPLICATION_JSON;
//    final static String MEDIA_TYPE = javax.ws.rs.core.MediaType.APPLICATION_XML;
//    
    static final LdapUserClient USER_API = new LdapUserClient(Const.BASE_URI, Const.MEDIA_TYPE);
    static final LdapGroupClient GROUP_API = new LdapGroupClient(Const.BASE_URI, Const.MEDIA_TYPE);
    static final LdapAssignClient ASSIGN_API = new LdapAssignClient(Const.BASE_URI, Const.MEDIA_TYPE);
    
    static final String REGISTER_VIEW = "RegisterView";
    static final String LOGIN_VIEW = "LoginView";
    static final String WELCOME_VIEW = "WelcomeView";
    static final String LOGOUT_VIEW = "LogoutView";
    static final String PERSONAL_DATA_VIEW = "PersonalDataView";
    static final String NEW_REQUEST_VIEW = "NewRequestView";
    static final String LIST_REQUEST_VIEW = "ListRequestView";
    static final String EDIT_REQUEST_VIEW = "EditRequestView";
    static final String NEW_COMPLAINT_VIEW = "NewComplaintView";
    static final String LIST_COMPLAINT_VIEW = "ListComplaintView";
    static final String EDIT_COMPLAINT_VIEW = "EditComplaintView";
    
    final static String DEVELOPER_GROUP = "Developer";
    final static String CUSTOMER_GROUP = "Customer";
    final static String ADMIN_GROUP = "Administrator";

    static final String REGISTRATION_SMS_API_URL = "http://api.smsapi.pl/sms.do";
    static final String REGISTRATION_SMS_TEXT = "mCloud verification code: ";
    static final String REGISTRATION_SMS_RECIPIENT = "880488509"; 
    
    static final String REGISTRATION_MAIL_HOST = "mail.mlife.pl";
    static final String REGISTRATION_MAIL_ADDRESS = "mcloud@mlife.pl";
    static final String REGISTRATION_MAIL_PWD = "PN&&4&lx7@R,";
    static final String REGISTRATION_MAIL_FROM = "mCloud";
    static final int REGISTRATION_MAIL_PORT = 587;
    static final String REGISTRATION_MAIL_TEST_RECIPIENT = "maciej.borucki@mlife.pl";
    static final String REGISTRATION_MAIL_SUBJECT = "mCloud registration";
    static final String REGISTRATION_MAIL_TEXT = "Please provide the code below in your selfcare panel to confirm your email: \n ";
    
    static final String VALIDATION_USERNAME = "Please provide valid username";
    
    static final String COMPONENT_TYPE_1 = "CT1";
    static final String NULL_SELECT_TEXT = "please choose";
   
    static final Integer TABLE_DATA_ROWS_DISPLAYED = 20;
    
    static final String REGEXP_USERNAME = "^[a-zA-Z]{4,32}$";
    static final String REGEXP_PHONE_NUMBER = "(\\d)?(\\d)?\\d{11}";
    static final String REGEXP_ALPHA = "^[a-zA-Z-'` ]{2,32}$";
    static final String REGEXP_ALPHANUMERIC = "^[0-9a-zA-Z-'` ]{2,9}$";
    /*"8 characters, at least one lowercase letter, one uppercase letter, one digit, one special symbol"*/
    static final String REGEXP_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$";
    
    static final String MESSAGE_INVALID_INPUT = "This input is not valid";
    static final String MESSAGE_EMPTY_INPUT = "This input cannot be empty";
    
    static final String SALT = "h3*(X1#a";
}
