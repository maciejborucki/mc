/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mi.mcloud.selfcare.ejb;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.mi.mcloud.selfcare.entity.Complaint;
import pl.mi.mcloud.selfcare.entity.PlatformUser;
import pl.mi.mcloud.selfcare.util.Const;

/**
 *
 * @author bor
 */
@Stateless
public class ComplaintFacade extends AbstractFacade<Complaint> {
    @PersistenceContext(unitName = "pl.mi_mcloud-selfcare_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComplaintFacade() {
        super(Complaint.class);
    }

    public Collection<? extends Complaint> findUserComplaints(PlatformUser platformUser, int page) {
        return em.createNamedQuery("Complaint.findByCreator")
                .setParameter("creator", platformUser)
                .setFirstResult(Const.TABLE_DATA_ROWS_DISPLAYED*(page-1))
                .setMaxResults(Const.TABLE_DATA_ROWS_DISPLAYED)
                .getResultList();
    }

    public int countUserComplaints(PlatformUser platformUser) {
        return ((Number)em.createNamedQuery("Complaint.findByCreatorCount").setParameter("creator", platformUser).getSingleResult()).intValue();
    }
    
}
