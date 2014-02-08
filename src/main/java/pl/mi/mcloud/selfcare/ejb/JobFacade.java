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
    
    public List<Job> findUserJobs(PlatformUser platformUser) {
        return em.createNamedQuery("Job.findByCreator").setParameter("creator", platformUser).getResultList();
    }

//    public List<Job> findMy() {
//        javax.persistence.criteria.CriteriaQuery criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery();
//
//        Integer uid = Integer.parseInt(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userId").toString());
//
//        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//        Root<Job> from = criteriaQuery.from(Job.class);
//
//        CriteriaQuery<Job> select = criteriaQuery.select(from);
//
//        Predicate predicate = criteriaBuilder.equal(from.get("creator"), uid);
//        criteriaQuery.where(predicate);
//
//        TypedQuery<Job> typedQuery = em.createQuery(select);
//        return typedQuery.getResultList();
//
////        cq.select(cq.from(entityClass));
////        return getEntityManager().createQuery(cq).getResultList();
//    }
}
