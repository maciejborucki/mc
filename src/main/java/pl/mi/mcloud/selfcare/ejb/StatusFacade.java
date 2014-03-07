/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mi.mcloud.selfcare.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import pl.mi.mcloud.selfcare.entity.Priority;
import pl.mi.mcloud.selfcare.entity.Status;

/**
 *
 * @author bor
 */
@Stateless
public class StatusFacade extends AbstractFacade<Status> {
    @PersistenceContext(unitName = "pl.mi_mcloud-selfcare_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StatusFacade() {
        super(Status.class);
    }
    
    public Status findByStatusName(String statusName) {
        try {
            Object o = em.createNamedQuery("Status.findByStatusName").setParameter("statusName", statusName).getSingleResult();
            return (Status) o;
        } catch (NoResultException nre) {
            return null;
        }
    }
    
}
