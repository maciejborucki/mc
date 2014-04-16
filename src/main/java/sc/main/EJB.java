/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sc.main;

import sc.main.ejb.AccessGroupFacade;
import sc.main.ejb.ComplaintFacade;
import sc.main.ejb.ComplaintHistoryFacade;
import sc.main.ejb.JobFacade;
import sc.main.ejb.JobHistoryFacade;
import sc.main.ejb.PlatformComponentFacade;
import sc.main.ejb.PlatformModuleFacade;
import sc.main.ejb.PlatformServiceFacade;
import sc.main.ejb.PlatformUserFacade;
import sc.main.ejb.PriorityFacade;
import sc.main.ejb.StatusFacade;



/**
 *
 * @author bor
 */
public class EJB {
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
