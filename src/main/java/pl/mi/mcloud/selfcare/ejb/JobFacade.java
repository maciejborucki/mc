/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mi.mcloud.selfcare.ejb;

import com.vaadin.server.VaadinService;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import pl.mi.mcloud.selfcare.entity.Job;
import pl.mi.mcloud.selfcare.entity.PlatformUser;
import pl.mi.mcloud.selfcare.util.Const;

/**
 *
 * @author bor
 */
@Stateless
public class JobFacade extends AbstractFacade<Job> {

    @PersistenceContext(unitName = "pl.mi_mcloud-selfcare_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JobFacade() {
        super(Job.class);
    }
    
    public List<Job> findUserJobs(PlatformUser platformUser, Integer page) {
        
        return em.createNamedQuery("Job.findByCreator")
                .setParameter("creator", platformUser)
                .setFirstResult(Const.TABLE_DATA_ROWS_DISPLAYED*(page-1))
                .setMaxResults(Const.TABLE_DATA_ROWS_DISPLAYED)
                .getResultList();
    }
    
    public Integer countUserJobs(PlatformUser platformUser) {
//        return em.createNamedQuery("Job.findByCreator")
//                .setParameter("creator", platformUser)
//                .
        return ((Number)em.createNamedQuery("Job.findByCreatorCount").setParameter("creator", platformUser).getSingleResult()).intValue();
    }

}
