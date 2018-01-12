package my.mimos.misos.command;

import java.util.List;

import my.mimos.misos.domain.entity.trx.MisosReportView;
import my.mimos.misos.domain.resource.SearchChannelRequestResource;
import my.mimos.misos.domain.resource.SummaryReportRequestResource;
import my.mimos.misos.domain.resource.SummaryReportResource;

public interface DisseminationAdminCommand {
	
	public List<SummaryReportResource> summaryReportChannelMessages(SummaryReportRequestResource req) throws RuntimeException;
	public List<MisosReportView> auditDetailReportChannelMessages(SearchChannelRequestResource req)
			throws RuntimeException;
	List<MisosReportView> recentDissemination() throws RuntimeException;
}
