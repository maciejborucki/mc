/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mi.mcloud.selfcare.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.mi.mcloud.selfcare.entity.PlatformUser;

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
    
    public List<PlatformUser> findByUsername(String username) {
        return em.createNamedQuery("PlatformUser.findByUsername").setParameter("username", username).getResultList();
    }
}
