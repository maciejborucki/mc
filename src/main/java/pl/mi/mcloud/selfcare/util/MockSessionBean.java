/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mi.mcloud.selfcare.util;

import javax.ejb.Stateless;

/**
 *
 * @author bor
 */
@Stateless
public class MockSessionBean {

    public String businessMethod() {
        return "mCloud sc ";
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
