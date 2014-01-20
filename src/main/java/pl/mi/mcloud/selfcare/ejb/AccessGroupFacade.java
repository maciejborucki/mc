/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mi.mcloud.selfcare.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.mi.mcloud.selfcare.entity.AccessGroup;

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
    
}
