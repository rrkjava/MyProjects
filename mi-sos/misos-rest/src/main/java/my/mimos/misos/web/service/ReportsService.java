package my.mimos.misos.web.service;


import my.mimos.misos.domain.resource.SearchChannelMessageResponseResource;
import my.mimos.misos.domain.resource.SummaryReportResponseResource;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;

public interface ReportsService {
	
	public JasperReportBuilder build(SearchChannelMessageResponseResource channelMsgLst);
	public JasperReportBuilder buildSummaryReport(SummaryReportResponseResource reportLst);

}
