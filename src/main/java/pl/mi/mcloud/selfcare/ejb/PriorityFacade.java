///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package pl.mi.mcloud.selfcare.ejb;
//
//import java.util.List;
//import javax.ejb.Stateless;
//import javax.persistence.EntityManager;
//import javax.persistence.NoResultException;
//import javax.persistence.PersistenceContext;
//import pl.mi.mcloud.selfcare.entity.PlatformUserX;
//import pl.mi.mcloud.selfcare.entity.Priority;
//
///**
// *
// * @author bor
// */
//@Stateless
//public class PriorityFacade extends AbstractFacade<Priority> {
//    @PersistenceContext(unitName = "pl.mi_mcloud-selfcare_war_1.0-SNAPSHOTPU")
//    private EntityManager em;
//
//    @Override
//    protected EntityManager getEntityManager() {
//        return em;
//    }
//
//    public PriorityFacade() {
//        super(Priority.class);
//    }
//    
//    public Priority findByPriorityName(String priorityName) {
//        try {
//            Object o = em.createNamedQuery("Priority.findByPriorityName").setParameter("priorityName", priorityName).getSingleResult();
//            return (Priority) o;
//        } catch (NoResultException nre) {
//            return null;
//        }
//    }
//    
//}
