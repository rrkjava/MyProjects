package my.mimos.mdc.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;
import my.mimos.mdc.command.QueryTrackerCommand;
import my.mimos.mdc.domain.entity.Query;
import my.mimos.mdc.domain.entity.QueryTracker;
import my.mimos.mdc.domain.entity.Response;
import my.mimos.mdc.domain.entity.Role;
import my.mimos.mdc.domain.entity.User;
import my.mimos.mdc.resources.query.SearchTrackerRequest;
import my.mimos.mdc.service.QueryTrackerService;
import my.mimos.mdc.service.UserManagementService;
import my.mimos.mdc.utils.MdcConstants;

@Log4j
@Component
public class QueryTrackerServiceImpl implements QueryTrackerService{
	
	@Autowired
	QueryTrackerCommand queryTrackerCommand;
	
	@Autowired
	UserManagementService userService;
	
	public void addQueryAction(Query query, String action,Long deptId,Response response){
		QueryTracker tracker = null;
		log.info("Adding action into tracker :" + action);
		try{
			tracker = new QueryTracker();
			tracker.setQuery(query);
			tracker.setQueryAction(action);
			Date now = new Date();
			tracker.setStartDate(now);
			tracker.setDeadlineDate(DateUtils.addHours(now, 12));
			tracker.setEndDate(null);			
			tracker.setValid(true);
			tracker.setActionStatus(false);   
			if(null != response){
				tracker.setResponse(response);
			}
			
			//RESOLVE ROLE/DEPARTMENT -  ACTION TO BE COMPLETED BY WHICH ROLE
			if(action.equalsIgnoreCase(MdcConstants.ACTION_FOCAL_ACK_QUERY)){
				tracker.setActionByRole( MdcConstants.ROLE_FOCAL); 
				tracker.setActionByDept(deptId);
			}else if(action.equalsIgnoreCase(MdcConstants.ACTION_ADMIN_ACK_QUERY)){
				tracker.setActionByRole( MdcConstants.ROLE_ADMIN); 
			}else if(action.equalsIgnoreCase(MdcConstants.ACTION_FOCAL_REPLY)){
				tracker.setActionByRole( MdcConstants.ROLE_FOCAL); 
				tracker.setActionByDept(deptId);
			}else if(action.equalsIgnoreCase(MdcConstants.ACTION_FOCAL_SUP_APPROVE)){
				tracker.setActionByRole( MdcConstants.ROLE_FOCAL_SUPERVISOR); 
				tracker.setActionByDept(deptId);
			}else if(action.equalsIgnoreCase(MdcConstants.ACTION_ADMIN_APPROVE)){
				tracker.setActionByRole( MdcConstants.ROLE_ADMIN); 
			}else if(action.equalsIgnoreCase(MdcConstants.ACTION_ADMIN_REPLY)){
				tracker.setActionByRole( MdcConstants.ROLE_ADMIN); 
			}else if(action.equalsIgnoreCase(MdcConstants.ACTION_KLN_CLARIFY)){
				tracker.setActionByRole( MdcConstants.ROLE_KLN); 
			}			
			queryTrackerCommand.addQueryAction(tracker);			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public List<QueryTracker> searchQueryAction(SearchTrackerRequest searchCriteria) {
		List<QueryTracker> trackerList = null;
		try{
			trackerList = queryTrackerCommand.searchQuery(searchCriteria);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return trackerList;
		
		
	}

	@Override
	public void updateQueryAction(Long trackerId, boolean completed, boolean disable,User actionCompletedBy) {
		try{
			QueryTracker tracker = queryTrackerCommand.findByTrackerId(trackerId);
			if(completed){
				tracker.setEndDate(new Date());
				tracker.setActionStatus(true);
				tracker.setActionDoneBy(actionCompletedBy);
			}
			if(disable)	{
				tracker.setValid(false);
			}
			queryTrackerCommand.addQueryAction(tracker);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void markQueryActionAscompleted(Query query,User actionBy,String action,String role,Long deptId,Response response){
		try{
			SearchTrackerRequest searchCriteriaTracker = new SearchTrackerRequest();
			searchCriteriaTracker.setQueryId(query.getQueryId());
			searchCriteriaTracker.setRole(role);
			searchCriteriaTracker.setValid(true);
			searchCriteriaTracker.setStatus(false);
			searchCriteriaTracker.setQueryAction(action);
			if(null != deptId){
				searchCriteriaTracker.setDeptId(deptId);
			}
			if(null != response){
				searchCriteriaTracker.setResponseId(response.getResponseId());
			}
			List<QueryTracker> trackers = searchQueryAction(searchCriteriaTracker);
			if(null != trackers && !trackers.isEmpty()){
				Long trackerId = searchQueryAction(searchCriteriaTracker).get(0).getTrackerId();
				updateQueryAction(trackerId, true, false, actionBy);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * When response is added, another tracker for approval for response is added
	 * The response approval tracker is added for supervisor or admin.
	 * Response approval has additional parameter, response id along with others.
	 */
	public void sendResponseTracker(Query query,Response response,User loggedInUser,boolean directReply,boolean supervisorExists){
		try{
			//USER ROLES
			boolean roleAdmin = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_ADMIN::equals);
			boolean roleFocal = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL::equals);
			boolean roleFocalSup = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL_SUPERVISOR::equals);
			
			//UPDATE TRACKER
			if(roleAdmin){	
				if(directReply){
					markQueryActionAscompleted(query, loggedInUser, MdcConstants.ACTION_ADMIN_ACK_QUERY,MdcConstants.ROLE_ADMIN,null,null);
					markQueryActionAscompleted(query, loggedInUser, MdcConstants.ACTION_ADMIN_REPLY,MdcConstants.ROLE_ADMIN,null,null);
				}else{
					markQueryActionAscompleted(query, loggedInUser, MdcConstants.ACTION_ADMIN_REPLY,MdcConstants.ROLE_ADMIN,null,null);
				}
			}else if(roleFocal || roleFocalSup){
				markQueryActionAscompleted(query, loggedInUser, MdcConstants.ACTION_FOCAL_REPLY,MdcConstants.ROLE_FOCAL,loggedInUser.getDepartment().getDeptId(),null);
				if(roleFocal && supervisorExists){
					addQueryAction(query, MdcConstants.ACTION_FOCAL_SUP_APPROVE,loggedInUser.getDepartment().getDeptId(),response);
				}else{
					addQueryAction(query, MdcConstants.ACTION_ADMIN_APPROVE,null,response);
				}			
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}	
	
	/**
	 * fetch reply from focal point with query and response id and mark it as complete.
	 * if focal sup -> add a new tracker for approval from admin
	 */
	public void approveResponseTracker(Response response,User loggedInUser){
		try{
			//USER ROLES
			boolean roleAdmin = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_ADMIN::equals);
			boolean roleFocalSup = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL_SUPERVISOR::equals);
			
			//UPDATE TRACKER
			if(roleFocalSup){
				markQueryActionAscompleted(response.getQuery(), loggedInUser,MdcConstants.ACTION_FOCAL_SUP_APPROVE,
						MdcConstants.ROLE_FOCAL_SUPERVISOR,loggedInUser.getDepartment().getDeptId(),response);
				addQueryAction(response.getQuery(), MdcConstants.ACTION_ADMIN_APPROVE,null,response);
			}else if(roleAdmin){
				markQueryActionAscompleted(response.getQuery(), loggedInUser, MdcConstants.ACTION_ADMIN_APPROVE,MdcConstants.ROLE_ADMIN,null,response);
			}		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * If admin ack the query it is marked as complete and a tracker for reply is added(if accept)
	 * If focal point ack the query it is marked as complete and a tracker for reply is added(if accept)
	 * for focal point, each dept/agency will have a seperate tracker for their ack and reply
	 */
	public void acknowledgeQueryTracker(Query query,User loggedInUser,boolean accept){
		try{
			//USER ROLES
			boolean roleAdmin = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_ADMIN::equals);
			boolean roleFocal = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL::equals);
			
			//UPDATE TRACKER
			if(roleAdmin){
				markQueryActionAscompleted(query, loggedInUser, MdcConstants.ACTION_ADMIN_ACK_QUERY,MdcConstants.ROLE_ADMIN,null,null);
				if(accept){
					addQueryAction(query, MdcConstants.ACTION_ADMIN_REPLY,null,null);
				}
			}else if(roleFocal){
				markQueryActionAscompleted(query, loggedInUser, MdcConstants.ACTION_FOCAL_ACK_QUERY,MdcConstants.ROLE_FOCAL,loggedInUser.getDepartment().getDeptId(),null);
				if(accept){
					addQueryAction(query, MdcConstants.ACTION_FOCAL_REPLY,loggedInUser.getDepartment().getDeptId(),null);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	/**
	 * when a new query is sent to admin, a tracker is added
	 * awaiting for the admin's acknowledgemnt(revert /accept /direct reply)
	 */
	public void sendQueryTracker(Query query){
		try{
			addQueryAction(query, MdcConstants.ACTION_ADMIN_ACK_QUERY,null,null);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * when the query is forwarded to focal point or agency
	 * a tracker is added awaiting the acknowledgment from the focal point.
	 * if query is forwarded to multiple recipients, one tracker is added for every agency.
	 * if any one user from an agency acknowledges, the tracker marks the action as complete.
	 */
	public void forwardQueryTracker(Query query, Set<Long> depts){
		try{
			if(null != depts && !depts.isEmpty()){
				for(Long deptId: depts){
					addQueryAction(query, MdcConstants.ACTION_FOCAL_ACK_QUERY,deptId,null);
				}
			}			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
		
 
}
