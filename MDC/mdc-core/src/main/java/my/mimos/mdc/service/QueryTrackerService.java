package my.mimos.mdc.service;

import java.util.List;
import java.util.Set;

import my.mimos.mdc.domain.entity.Query;
import my.mimos.mdc.domain.entity.QueryTracker;
import my.mimos.mdc.domain.entity.User;
import my.mimos.mdc.domain.entity.Response;
import my.mimos.mdc.resources.query.SearchTrackerRequest;

public interface QueryTrackerService {	
	
	public void addQueryAction(Query query, String action, Long deptId,Response response);
	public List<QueryTracker> searchQueryAction(SearchTrackerRequest searchCriteria);
	public void updateQueryAction(Long trackerId, boolean completed, boolean disbale,User actionCompletedBy);
	public void markQueryActionAscompleted(Query query,User actionBy,String action,String role,Long deptId,Response response);
	
	
	public void sendQueryTracker(Query query);
	public void acknowledgeQueryTracker(Query query,User loggedInUser,boolean accept);
	public void sendResponseTracker(Query query,Response exisitingResponse,User loggedInUser,boolean directReply,boolean supervisorExists);
	public void approveResponseTracker(Response existingResponse,User loggedInUser);
	public void forwardQueryTracker(Query query, Set<Long> depts);
	
}
