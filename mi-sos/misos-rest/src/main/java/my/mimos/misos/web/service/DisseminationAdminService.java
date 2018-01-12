package my.mimos.misos.web.service;

import my.mimos.misos.domain.resource.SearchChannelMessageResponseResource;
import my.mimos.misos.domain.resource.SearchChannelRequestResource;
import my.mimos.misos.domain.resource.SummaryReportRequestResource;
import my.mimos.misos.domain.resource.SummaryReportResponseResource;

/**
 * 
 * @author beaula.fernandez
 *
 */

public interface DisseminationAdminService {
	
	public SummaryReportResponseResource summaryReportChannelMessages(SummaryReportRequestResource req);
	SearchChannelMessageResponseResource auditDetailReportChannelMessages(SearchChannelRequestResource req);
	SearchChannelMessageResponseResource recentDissemination();

}
