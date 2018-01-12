package my.mimos.mdc.scheduler;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import my.mimos.mdc.command.QueryCommand;
import my.mimos.mdc.command.QueryTrackerCommand;
import my.mimos.mdc.domain.entity.QueryTracker;
import my.mimos.mdc.domain.entity.Recipient;
import my.mimos.mdc.resources.query.SearchRecipientRequestResource;
import my.mimos.mdc.resources.query.SearchTrackerRequest;
import my.mimos.mdc.service.AsyncService;
import my.mimos.mdc.utils.MdcConstants;

@Component
public class ScheduledTasks {
   
	@Autowired
	QueryTrackerCommand queryTrackerCommand;	
	@Autowired
	QueryCommand queryCommand;
	@Autowired
	AsyncService asyncService;
	
	/**
	 * Send reminders for all pending queries
	 * Sends everyday at 8 am -> changed to every 4 hours
	 */
     //@Scheduled(cron = "0 0 8 * * *") // second-0 minute-0 hour-8 day(month)-0 month-0 day(week)-0 
	 @Scheduled(cron = "0 0 */4 * * *") // every 4 hours
	 public void dailyReminders() {
		 System.out.println("CRON JOB FOR REMINDERS .....RUNNING............@"+ new Date());
    	try{
    		// FILTER PENDING QUERY ACTIONS FROM TRACKER
    		SearchTrackerRequest searchCriteriaTracker  = new SearchTrackerRequest();
    		searchCriteriaTracker.setStatus(false); 	//query action is not completed yet
    		searchCriteriaTracker.setValid(true);   	//query action is valid
    		searchCriteriaTracker.setDeadLine(true);	//query has not been attended to more than 12 hrs.
    		List<QueryTracker> trackers = queryTrackerCommand.searchQuery(searchCriteriaTracker);
    		
    		// FILTER RECIPIENTS RESPOSNSIBLE FOR QUERY ACTION
    		List<Long> notifyUser = new ArrayList<>();
    		List<Recipient> recipients = new ArrayList<>();
    		if(null!= trackers && !trackers.isEmpty()){
	    		for(QueryTracker tracker : trackers){
	    			
	    			// search recipients responsible for query action in each tracker based on role or dept(for focal) 
					String role = tracker.getActionByRole();
	    			Long deptId = tracker.getActionByDept();
	    			SearchRecipientRequestResource searchCriteriaRecipient = new SearchRecipientRequestResource();
	    			searchCriteriaRecipient.setQueryId(tracker.getQuery().getQueryId());
	    			if(StringUtils.isNotBlank(role)){
	    				searchCriteriaRecipient.setRole(role);
	    			}
	    			if(null != deptId){
	    				searchCriteriaRecipient.setDeptId(deptId);
	    			}
	    			recipients = queryCommand.searchQueryRecipient(searchCriteriaRecipient);   
	    			//get all user ids of recipients
	    			recipients.forEach(recipient-> notifyUser.add(recipient.getRecipient().getUserId()));    			   			
	    		}    
	    		// PUSH NOTIFICATION
	    		if(null != notifyUser && !notifyUser.isEmpty()){
		    		asyncService.pushNotificationToDevices(0L,notifyUser,MdcConstants.NOTIFY_REMINDER,MdcConstants.NOTIFY_TYPE_QUERY);
					asyncService.sendEmailNotification(0L,notifyUser,MdcConstants.NOTIFY_REMINDER);
	    		}
    		}
    		
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    //private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    /*@Scheduled(fixedRate = 5000) // every 5 seconds
    public void testScheduler() {
        System.out.println("Scheduled Tasks .....TESTING............The time is now {}"+ dateFormat.format(new Date()));
    }*/

}
