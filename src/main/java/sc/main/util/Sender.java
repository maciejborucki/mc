/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sc.main.util;

import pl.mi.mcloud.selfcare.util.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author bor
 */
public class Sender {

    private final static Logger logger = Logger.getLogger(Sender.class.getName());
    String host = Const.REGISTRATION_MAIL_HOST;
    String address = Const.REGISTRATION_MAIL_ADDRESS;
    String pwd = Const.REGISTRATION_MAIL_PWD;
    String fromPersonal = Const.REGISTRATION_MAIL_FROM;
    int port = Const.REGISTRATION_MAIL_PORT;
    private WebResource webResource;

    public Sender() {
        Client client = Client.create();
        webResource = client.resource(Const.REGISTRATION_SMS_API_URL);
    }

    public String sendSms(String msisdn, String message) {

        String response = "";
        MultivaluedMap queryParams = new MultivaluedMapImpl();
        queryParams.add("username", "mlife");
        queryParams.add("password", "4872bd2962c187ca9ce7f1affdf66cc5");
        queryParams.add("from", "Alert");
        queryParams.add("to", msisdn);
        queryParams.add("message", message);
        logger.log(Level.FINEST, "sendSms "+webResource.queryParams(queryParams).getURI());
        System.out.println("!!! SmsApi uri: " + webResource.queryParams(queryParams).getURI());        
        response = webResource.queryParams(queryParams).get(String.class);
        System.out.println("!!! SmsApi uri: " + response); 
        return response;
    }

    public void sendMail(String recipient, String subject, String message) throws MessagingException {

        try {
            Properties properties = System.getProperties();
            Session session = Session.getDefaultInstance(properties);
            MimeMessage msg = new MimeMessage(session);
            //for (String r : recipients) {
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            //}
            msg.setSubject(subject);
            msg.setContent(message, "text/html; charset=utf-8");
            try {
                msg.setFrom(new javax.mail.internet.InternetAddress(address, fromPersonal));
            } catch (UnsupportedEncodingException ex) {
                logger.log(Level.WARNING, "msg.setFrom");
            }
            Transport t = session.getTransport("smtp");
            t.connect(host, port, address, pwd);

            t.sendMessage(msg, msg.getAllRecipients());
        } catch (MessagingException ex) {
            logger.log(Level.WARNING, "MessagingException ex", ex);
        }
    }
}
