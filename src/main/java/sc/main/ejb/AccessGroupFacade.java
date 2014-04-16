/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sc.main.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import sc.main.entity.AccessGroup;

/**
 *
 * @author bor
 */
@Stateless
public class AccessGroupFacade extends AbstractFacade<AccessGroup> {
    
    @PersistenceContext(unitName = "pl.mi_mcloud-selfcare_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccessGroupFacade() {
        super(AccessGroup.class);
    }
    
    public AccessGroup findByGroupName(String groupName)  {
        AccessGroup ag;
        try {
        ag = em.createNamedQuery("AccessGroup.findByGroupName", AccessGroup.class)
                .setParameter("groupName", groupName)
                .getSingleResult();
        } catch (NoResultException e ) {
            ag = null;
        }
        return ag;
    }
    
}
