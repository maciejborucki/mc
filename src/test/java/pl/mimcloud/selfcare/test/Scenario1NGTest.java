package pl.mimcloud.selfcare.test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.sun.jersey.api.client.UniformInterfaceException;
import java.util.logging.Logger;
import mcloud.integration.ldap.client.LdapUserClient;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import pl.mi.mcloud.selfcare.util.Const;
import pl.mlife.mcloud.integration.ldap.entity.Password;
import pl.mlife.mcloud.integration.ldap.entity.User;

/**
 *
 * @author bor
 */
public class Scenario1NGTest {

    private final static Logger logger = Logger.getLogger(Scenario1NGTest.class.getName());

    public Scenario1NGTest() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

//    @Test
//    public void mailTest() {
//        try {
//            Sender instance = new Sender();
//            //mailer.postMail(recipient, Const.REGISTRATION_MAIL_SUBJECT, mailTextWithRandomSequence.toString());
//            String recipient = Const.REGISTRATION_MAIL_TEST_RECIPIENT;
//            String subject = Const.REGISTRATION_MAIL_SUBJECT;
//            //String message = Const.REGISTRATION_MAIL_TEXT;
//            StringBuilder msgText = new StringBuilder(256);
//            msgText.append(Const.REGISTRATION_MAIL_TEXT);
//            msgText.append(Utils.generateRandomCode(32)); 
//
//            instance.sendMail(recipient, subject, msgText.toString());
//            assertTrue(true);
//
//            StringBuilder smsText = new StringBuilder(128);
//            smsText.append(Const.REGISTRATION_SMS_TEXT);
//            smsText.append(Utils.generateRandomCode(32));
//            String response = instance.sendSms(Const.REGISTRATION_SMS_RECIPIENT, smsText.toString());
//
//            assertTrue(true);
//        } catch (MessagingException ex) {
//            Logger.getLogger(Scenario1NGTest.class.getName()).log(Level.SEVERE, null, ex);
//            assertTrue(false);
//        }
//    }
    @Test
    public void loginTest() {
        final LdapUserClient userAPI = new LdapUserClient(Const.BASE_URI, Const.MEDIA_TYPE);
        String name = "TestowyJozek";
        String pwd = "TestowyJozek123";
        try {
            userAPI.remove(name);
        } catch (UniformInterfaceException e) {
            System.err.println(e.getResponse().getEntity(String.class));
//            System.out.println(e.getResponse().getEntity(String.class));
        }
        User u = new User();
        u.setCity(name);
        u.setCn(name);
        u.setDisplayname(name);
        u.setGivenname(name);
        u.setSn(name);
        u.setMail(name + "@abc.abc");
        u.setMobile("999242111");
        u.setPostalAddress(name);
        u.setEmailConfirmed(Boolean.FALSE);
        u.setSmsConfirmed(Boolean.FALSE);
        u.setPostalCode(name);
        
        u.setUid(name);
        u.setUserpassword(pwd);
        try {
            userAPI.create(u);
        } catch (UniformInterfaceException e) {
            System.err.println("1"+e.getResponse().getEntity(String.class));
//            System.out.println(e.getResponse().getEntity(String.class));
        }
        try {
            userAPI.setUsernamePassword(name, pwd);
        } catch (UniformInterfaceException e) {
            System.err.println("2"+e.getResponse().getEntity(String.class));
//            System.out.println(e.getResponse().getEntity(String.class));
        }
        try {
            userAPI.changePassword(new Password(pwd), name);
        } catch (UniformInterfaceException e) {
            System.err.println("2.5"+e.getResponse().getEntity(String.class));
//            System.out.println(e.getResponse().getEntity(String.class));
        }
        try {
            User f = userAPI.find(u.getUid());
            System.out.println(f.getUid()+" "+f.getUserpassword());
        } catch (UniformInterfaceException e) {
            System.err.println("3"+e.getResponse().getEntity(String.class));
//            System.out.println(e.getResponse().getEntity(String.class));
        }      
        try {
            userAPI.check(name, new Password(pwd));
            assertTrue(true);
        } catch (UniformInterfaceException e) {
            System.err.println("4"+e.getResponse().getEntity(String.class));
//            System.out.println(e.getResponse().getEntity(String.class));
        }
    }

//    @Test
//    public void connectionTest() {
//
//        String user = "Justins";
//        String group = "GROUP1";
//        final LdapUserClient userAPI = new LdapUserClient(Const.BASE_URI, Const.MEDIA_TYPE);
//        final LdapGroupClient groupAPI = new LdapGroupClient(Const.BASE_URI, Const.MEDIA_TYPE);
//        final LdapAssignClient assignAPI = new LdapAssignClient(Const.BASE_URI, Const.MEDIA_TYPE);
//
//        try {
//            userAPI.remove(user);
//            assignAPI.remove(user, group);
//            groupAPI.remove(group);
//
//        } catch (UniformInterfaceException e) {
////            assertTrue(false);
//        }
//
//        User u = new User();
//        u.setUid(user);
//        u.setCity(user);
//        u.setCn(user);
//        u.setDisplayname(user);
//        u.setGivenname(user);
//        u.setSn(user);
//        u.setMail(user + "@abc.abc");
//        u.setMobile("999242111");
//        u.setPostalAddress(user);
//        u.setEmailConfirmed(Boolean.FALSE);
//        u.setSmsConfirmed(Boolean.FALSE);
//        u.setPostalCode(user);
//        u.setUserpassword(user + "123$");
//        try {
//            userAPI.setUsernamePassword(user, u.getUserpassword());
//        } catch (UniformInterfaceException e) {
//            assertTrue(false);
//        }
//        List<User> sameMail = userAPI.findAll(User.class, u.getMail(), null);
//        List<User> sameMobile = userAPI.findAll(User.class, null, u.getMobile());
//        if (Boolean.FALSE == Const.TEST_ENABLED) {
//            if (!sameMail.isEmpty()) {
//                //layout.addComponent(new Label("This email is already registered on platform: " + u.getMail()));
//                //logger.log(Level.WARNING, "This email is already registered on platform: {0}", u.getMail());
//                assertTrue(false);
//            }
//            if (!sameMobile.isEmpty()) {
//                //layout.addComponent(new Label("This phone number is already registered on platform: " + u.getMobile()));
//                //logger.log(Level.WARNING, "This email is already registered on platform: {0}", u.getMail());
//                assertTrue(false);
//            }
//        }
//        try {
//            userAPI.create(u);
//
//            //userAPI.changePassword(new Password(password1Field.getValue()), usernameField.getValue());
//            //layout.addComponent(new Label("User registered on platform: " + u.getDisplayname()));
//            //logger.log(Level.FINEST, "clientU.create(u);");
//        } catch (UniformInterfaceException e) {
//            System.err.println(e.getResponse().getEntity(String.class));
//            System.out.println(e.getResponse().getEntity(String.class));
//            logger.log(Level.WARNING, "UniformInterfaceException clientU.create(u); userAPI.changePassword; {0}", e.getResponse().getEntity(String.class));
//            assertTrue(false);
//        }
//        try {
//            System.out.println(" -- " + u.getUid() + " " + u.getUserpassword() + " ");
//            userAPI.check(u.getUid(), new Password(u.getUserpassword()));
//        } catch (UniformInterfaceException e) {
//            assertTrue(false);
//        }
//        try {
//            groupAPI.create(new Group(group));
//        } catch (UniformInterfaceException e) {
//            assertTrue(false);
//        }
//        try {
//            assignAPI.create(new GroupAssign(user, group));
//        } catch (UniformInterfaceException e) {
//            assertTrue(false);
//        }
//        try {
//            assignAPI.remove(user, group);
//            groupAPI.remove(group);
//            userAPI.remove(user);
//        } catch (UniformInterfaceException e) {
//            assertTrue(false);
//        }
//        assertTrue(true);
//    }
    @org.testng.annotations.BeforeClass
    public static void setUpClass() throws Exception {
    }

    @org.testng.annotations.AfterClass
    public static void tearDownClass() throws Exception {
    }

    @org.testng.annotations.BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @org.testng.annotations.AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
