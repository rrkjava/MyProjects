package my.mimos.mdc.command;

import java.util.List;

import my.mimos.mdc.domain.entity.QueryTracker;
import my.mimos.mdc.resources.query.SearchTrackerRequest;

public interface QueryTrackerCommand {
	
	public void addQueryAction(QueryTracker tracker);

	public QueryTracker findByTrackerId(Long trackerId);

	public List<QueryTracker> searchQuery(SearchTrackerRequest searchCriteria);

}
