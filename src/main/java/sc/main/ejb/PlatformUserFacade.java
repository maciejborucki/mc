/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sc.main.ejb;

import java.security.MessageDigest;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import sc.main.entity.PlatformUser;
import sc.main.exception.MCloudException;
import sc.main.util.Const;
import sc.main.util.Utils;
import sun.misc.BASE64Encoder;

/**
 *
 * @author bor
 */
@Stateless
public class PlatformUserFacade extends AbstractFacade<PlatformUser> {
    @PersistenceContext(unitName = "pl.mi_mcloud-selfcare_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlatformUserFacade() {
        super(PlatformUser.class);
    }
    
    @Override
    public void create(PlatformUser entity) throws MCloudException, Exception {
        
        if(checkIfUsernameExists(entity.getUsername())) {
            throw new MCloudException("This username is already registered");
        } else if(checkIfEmailExists(entity.getEmail())) {
            throw new MCloudException("This email is already registered");
        } else if(checkIfPhoneExists(entity.getPhone())) {
            throw new MCloudException("This phone number is already registered");
        } else {
            try {
                getEntityManager().persist(entity);
            } catch (ConstraintViolationException e) {
                for (ConstraintViolation cv : e.getConstraintViolations()) {
                    Utils.messageLog(Level.SEVERE, cv.getInvalidValue().toString(), "");
                    throw e;
                }
            } catch (Exception e) {
                Utils.messageLog(Level.SEVERE, e.getMessage(), " EXCEPTION ");
                throw e;
            }
        }
    }
    
    public List<PlatformUser> findByUsername(String username) {
        return em.createNamedQuery("PlatformUser.findByUsername").setParameter("username", username).getResultList();
    }
    
    public PlatformUser findByUsernameAndPassword(String username, String password) {
        try {
            return em.createNamedQuery("PlatformUser.findByUsernameAndPassword", PlatformUser.class)
                    .setParameter("password", hashPassword(password, username))
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException nre) {
            Utils.logWarning("PlatformUser findByLoginAndPassword NoResultException", false);
            return null;
        } catch (Exception e) {
            Utils.logWarning("PlatformUser findByLoginAndPassword Exception", false);
            return null;
        }
    }
    
    public Boolean checkIfUsernameExists(String username) {
            return !em.createNamedQuery("PlatformUser.findByUsername", PlatformUser.class).setParameter("username", username).getResultList().isEmpty();
    }    
    
    public Boolean checkIfEmailExists(String email) {
            return !em.createNamedQuery("PlatformUser.findByEmail", PlatformUser.class).setParameter("email", email).getResultList().isEmpty();
    }
    
    public Boolean checkIfPhoneExists(String phone) {
            return !em.createNamedQuery("PlatformUser.findByPhone", PlatformUser.class).setParameter("phone", phone).getResultList().isEmpty();
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
}
