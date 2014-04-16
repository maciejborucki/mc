///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package pl.mi.mcloud.selfcare.view;
//
//import java.util.Date;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import sc.main.EJB;
//import pl.mi.mcloud.selfcare.entity.Complaint;
//import pl.mi.mcloud.selfcare.entity.ComplaintHistory;
//import pl.mi.mcloud.selfcare.entity.Job;
//import pl.mi.mcloud.selfcare.entity.JobHistory;
//import pl.mi.mcloud.selfcare.view.util.ViewUtils;
//
///**
// *
// * @author bor
// */
//public class HistoryService {    
//    
//    static boolean makeHistoricalEntry(Job newJob) {
//        Job oldJob = EJB.jobFacade.find(newJob.getId());
//        
//        JobHistory jobHistoryEntry = new JobHistory();
//        jobHistoryEntry.setAssignee(oldJob.getAssignee());
//        jobHistoryEntry.setComplaint(oldJob.getComplaint());
//        jobHistoryEntry.setComponent(oldJob.getComponent());
//        jobHistoryEntry.setContents(oldJob.getContents());
//        jobHistoryEntry.setCreator(oldJob.getCreator());
//        jobHistoryEntry.setDateAcknowledged(oldJob.getDateAcknowledged());
//        jobHistoryEntry.setDateClosed(oldJob.getDateClosed());
//        jobHistoryEntry.setDateCreated(oldJob.getDateCreated());
//        jobHistoryEntry.setDateDue(oldJob.getDateDue());
//        jobHistoryEntry.setHistoryEntryCreated(new Date());
//        jobHistoryEntry.setJobId(oldJob);
//        jobHistoryEntry.setModule(oldJob.getModule());
//        jobHistoryEntry.setPlannedWorkEnd(oldJob.getPlannedWorkEnd());
//        jobHistoryEntry.setPlannedWorkStart(oldJob.getPlannedWorkStart());
//        jobHistoryEntry.setPriority(oldJob.getPriority());
//        jobHistoryEntry.setService(oldJob.getService());
//        jobHistoryEntry.setStatus(oldJob.getStatus());
//        jobHistoryEntry.setTrackList(oldJob.getTrackList().concat(" | new changes"));
//        try {
//            EJB.jobHistoryFacade.create(jobHistoryEntry);
//            ViewUtils.messageLog(Level.INFO, " ", " CREATED jobHistoryFacade.create(jobHistoryEntry); "); 
//        } catch (Exception e) {
//            ViewUtils.messageLog(Level.SEVERE, e.getMessage(), " EXCEPTION jobHistoryFacade.create(jobHistoryEntry); ");
//            return false;
//        }
//        return true;
//    }
//    
//    static boolean makeHistoricalEntry(Complaint newComplaint) {
//        Complaint oldComplaint = EJB.complaintFacade.find(newComplaint.getId());
//        
//        ComplaintHistory complaintHistoryEntry = new ComplaintHistory();        
//        complaintHistoryEntry.setContents(oldComplaint.getContents());
//        complaintHistoryEntry.setCreator(oldComplaint.getCreator());
//        complaintHistoryEntry.setDateAcknowledged(oldComplaint.getDateAcknowledged());
//        complaintHistoryEntry.setDateClosed(oldComplaint.getDateClosed());
//        complaintHistoryEntry.setDateCreated(oldComplaint.getDateCreated());
//        complaintHistoryEntry.setDateDue(oldComplaint.getDateDue());
//        complaintHistoryEntry.setHistoryEntryCreated(new Date());
//        complaintHistoryEntry.setComplaintId(oldComplaint);
//        complaintHistoryEntry.setPriority(oldComplaint.getPriority());
//        complaintHistoryEntry.setStatus(oldComplaint.getStatus());
//        complaintHistoryEntry.setTrackList(oldComplaint.getTrackList().concat(" | new changes"));
//        try {
//            EJB.complaintHistoryFacade.create(complaintHistoryEntry);
//            ViewUtils.messageLog(Level.INFO, " ", " CREATED complaintHistoryFacade.create(complaintHistoryEntry) "); 
//        } catch (Exception e) {
//            ViewUtils.messageLog(Level.SEVERE, e.getMessage(), " EXCEPTION complaintHistoryFacade.create(complaintHistoryEntry) ");
//            return false;
//        }
//        return true;
//    }
//    
//}
