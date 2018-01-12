package my.mimos.mdc.command.handler;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.querydsl.core.BooleanBuilder;

import my.mimos.mdc.command.QueryTrackerCommand;
import my.mimos.mdc.domain.entity.QQueryTracker;
import my.mimos.mdc.domain.entity.QueryTracker;
import my.mimos.mdc.domain.repository.QueryTrackerRepository;
import my.mimos.mdc.resources.query.SearchTrackerRequest;

@Component
public class QueryTrackerCommandHandler implements QueryTrackerCommand{

@Autowired
QueryTrackerRepository queryTrackerRepository;

	@Override
	public void addQueryAction(QueryTracker tracker) {
		try{
			queryTrackerRepository.save(tracker);				
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public QueryTracker findByTrackerId(Long trackerId) {
		QueryTracker tracker = null;
		try{
			tracker = queryTrackerRepository.findOne(trackerId);			
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
		return tracker;
	}
	
	@Override
	public List<QueryTracker> searchQuery(SearchTrackerRequest searchCriteria) {
		List<QueryTracker> filteredQueries = null;
		try{
			QQueryTracker queryTracker = QQueryTracker.queryTracker;			
			BooleanBuilder where = new BooleanBuilder();
			
			if(null != searchCriteria.getQueryId()){
				where.and(queryTracker.query.queryId.eq(searchCriteria.getQueryId()));
			}
			if(null != searchCriteria.getResponseId()){
				where.and(queryTracker.response.responseId.eq(searchCriteria.getResponseId()));
			}
			if(null != searchCriteria.getTrackerId()){
				where.and(queryTracker.trackerId.eq(searchCriteria.getTrackerId()));
			} 
			if(StringUtils.isNotBlank(searchCriteria.getRole())){
				where.and(queryTracker.actionByRole.eq(searchCriteria.getRole()));
			}
			if(null != searchCriteria.getDeptId()){
				where.and(queryTracker.actionByDept.eq(searchCriteria.getDeptId()));
			}
			if(null != searchCriteria.getStartDate()){
				where.and(queryTracker.startDate.eq(searchCriteria.getStartDate()));
			}
			if(searchCriteria.isDeadLine()){
				where.and(queryTracker.deadlineDate.before(new Date()));
			}
			if(null != searchCriteria.getEndDate()){
				where.and(queryTracker.endDate.eq(searchCriteria.getEndDate()));
			}
			if(searchCriteria.isStatus()){
				where.and(queryTracker.actionStatus.eq(true));
			}else{where.and(queryTracker.actionStatus.eq(false));}
			
			if(searchCriteria.isValid()){
				where.and(queryTracker.valid.eq(true));
			}else{where.and(queryTracker.valid.eq(false));}
			
			if(StringUtils.isNotBlank(searchCriteria.getQueryAction())){
				where.and(queryTracker.queryAction.equalsIgnoreCase(searchCriteria.getQueryAction()));
			}
 			if(where.hasValue()){
				filteredQueries = (List<QueryTracker>) queryTrackerRepository.findAll(where);	
			}/*else{
				filteredQueries = queryRepository.findAll();
			}*/ // this else condition displays all queries if there is no search criteria
		}
		catch(Exception ex){		
			throw new RuntimeException(ex);
		}
		return filteredQueries;
	}

}
