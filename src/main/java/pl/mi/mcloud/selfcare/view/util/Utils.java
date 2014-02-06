/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mi.mcloud.selfcare.view.util;

import com.vaadin.server.VaadinService;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.NoResultException;
import mcloud.integration.ldap.client.LdapUserClient;
import pl.mi.mcloud.selfcare.EJBBus;
import pl.mi.mcloud.selfcare.entity.AccessGroup;
import pl.mi.mcloud.selfcare.entity.PlatformUser;
import pl.mi.mcloud.selfcare.util.Const;
import pl.mlife.mcloud.integration.ldap.entity.Password;
import pl.mlife.mcloud.integration.ldap.entity.User;

/**
 *
 * @author bor
 */
public class Utils {

    final static LdapUserClient userAPI = new LdapUserClient(Const.BASE_URI, Const.MEDIA_TYPE);

    public static void login(String login, String password) {
        userAPI.check(login, new Password(password));
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogged", Boolean.TRUE);
        VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userLogin", login);
        ViewUtils.messageLog(
                Level.FINEST,
                "User logged, session attrs set - userLogin:",
                VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userLogin").toString(),
                "userLogged:",
                VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userLogged").toString());
        synchronizeAccounts(login);
    }

    public static void synchronizeAccounts(String login) {
        User ldapUser = userAPI.find(login);
        List result = EJBBus.platformUserFacade.findByUsername(login);
        if (result.isEmpty()) {
            AccessGroup defaultAccessGroup = createDefaultAccessGroupIfNecessary();
            PlatformUser newPlatformUser = new PlatformUser();
            newPlatformUser.setUsername(login);
            newPlatformUser.setAccessGroup116(defaultAccessGroup.getId().toString());
            EJBBus.platformUserFacade.create(newPlatformUser);
        } else {

        }
    }

    public static AccessGroup createDefaultAccessGroupIfNecessary() {
        AccessGroup defaultAccessGroup = null;
        try {
            defaultAccessGroup = EJBBus.accessGroupFacade.findByGroupName(Const.DEFAULT_ACCESS_GROUP_NAME);
        } catch (NoResultException e) {
            ViewUtils.messageLog(Level.INFO, "NoResultException EJBBus.accessGroupFacade.findByGroupName(Const.DEFAULT_ACCESS_GROUP_NAME)", "");
        }
        if (defaultAccessGroup == null) {
            defaultAccessGroup = new AccessGroup();
            defaultAccessGroup.setGroupName(Const.DEFAULT_ACCESS_GROUP_NAME);
            defaultAccessGroup.setAccessResources(Const.DEFAULT_ACCESS_GROUP_STRING);
            defaultAccessGroup.setAccessType(Const.DEFAULT_ACCESS_GROUP_STRING);
        }
        return defaultAccessGroup;
    }
}
