/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.mi.mcloud.selfcare;

import pl.mi.mcloud.selfcare.ejb.AccessGroupFacade;
import pl.mi.mcloud.selfcare.ejb.ComplaintFacade;
import pl.mi.mcloud.selfcare.ejb.ComplaintHistoryFacade;
import pl.mi.mcloud.selfcare.ejb.JobFacade;
import pl.mi.mcloud.selfcare.ejb.JobHistoryFacade;
import pl.mi.mcloud.selfcare.ejb.PlatformComponentFacade;
import pl.mi.mcloud.selfcare.ejb.PlatformModuleFacade;
import pl.mi.mcloud.selfcare.ejb.PlatformServiceFacade;
import pl.mi.mcloud.selfcare.ejb.PlatformUserFacade;
import pl.mi.mcloud.selfcare.ejb.PriorityFacade;
import pl.mi.mcloud.selfcare.ejb.StatusFacade;

/**
 *
 * @author bor
 */
public class EJBBus {
    public static AccessGroupFacade accessGroupFacade = null;
    public static ComplaintFacade complaintFacade = null;
    public static ComplaintHistoryFacade complaintHistoryFacade = null;
    public static JobFacade jobFacade = null;
    public static JobHistoryFacade jobHistoryFacade = null;
    public static PlatformComponentFacade platformComponentFacade = null;
    public static PlatformModuleFacade platformModuleFacade = null;
    public static PlatformServiceFacade platformServiceFacade = null;
    public static PlatformUserFacade platformUserFacade = null;
    public static PriorityFacade priorityFacade = null;
    public static StatusFacade statusFacade = null;  
}
