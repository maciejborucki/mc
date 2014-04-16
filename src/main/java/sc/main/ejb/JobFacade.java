/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc.main.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.mi.mcloud.selfcare.util.Const;
import sc.main.entity.Job;
import sc.main.entity.PlatformUser;

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
