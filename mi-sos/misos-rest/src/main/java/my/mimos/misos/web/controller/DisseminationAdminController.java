package my.mimos.misos.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import my.mimos.misos.common.enums.RequestParameters;
import my.mimos.misos.domain.integrationservice.ChannelTypesResponseResource;
import my.mimos.misos.domain.integrationservice.SeverityLevelsResponseResource;
import my.mimos.misos.domain.resource.SearchChannelMessageResponseResource;
import my.mimos.misos.domain.resource.SearchChannelRequestResource;
import my.mimos.misos.domain.resource.SummaryReportRequestResource;
import my.mimos.misos.domain.resource.SummaryReportResponseResource;
import my.mimos.misos.service.IntegrationService;
import my.mimos.misos.web.service.DisseminationAdminService;
import my.mimos.misos.web.service.ReportsService;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsExporterBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsxExporterBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.exception.DRException;

/**
 * 
 * @author beaula.fernandez
 * web services for mi-sos admin
 */

@Controller
@RequestMapping(value = "/admin")
public class DisseminationAdminController {
	
	@Autowired 
	DisseminationAdminService adminService;
	
	@Autowired 
	ReportsService reportService;
	
	@Autowired
	IntegrationService integrationService;
	
	/**
	 * dissemination messages based on search criteria for audit report
	 * @param req
	 * @return 
	 */
	@RequestMapping(value = "/secure/auditdetailreport", method = RequestMethod.POST)
	public ResponseEntity<SearchChannelMessageResponseResource> auditDetailReportChannelMessages(@RequestBody SearchChannelRequestResource req){
		SearchChannelMessageResponseResource messages = adminService.auditDetailReportChannelMessages(req);
		return new ResponseEntity<SearchChannelMessageResponseResource>(messages , HttpStatus.OK);
	}
	
	/**
	 * dissemination messages based on search criteria for summary report
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/secure/summaryreport", method = RequestMethod.POST)
	public ResponseEntity<SummaryReportResponseResource> summaryReportChannelMessages(@RequestBody SummaryReportRequestResource req){
		SummaryReportResponseResource messages = adminService.summaryReportChannelMessages(req);
		return new ResponseEntity<SummaryReportResponseResource>(messages , HttpStatus.OK);
	}
	
	@RequestMapping(value = "/reportPDF",method= RequestMethod.GET)
	public void generateReport(@RequestParam("fromDate") String fromDate,
							@RequestParam("toDate") String toDate,
							@RequestParam("severityLevel") String severityLevel,
							@RequestParam("channelType") String channelType,
							@RequestParam("fileFormat") String format,
							@RequestParam("Accept-Language") String locale,
							HttpServletResponse response) throws DRException, IOException{
		 
		SearchChannelRequestResource req = new SearchChannelRequestResource();
		req.setChannelType(channelType);
		req.setSeverityLevel(severityLevel);
		req.setToDate(toDate);
		req.setFromDate(fromDate);
		
		Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy") ;
        String currentDate =dateFormat.format(date);
        
        if (locale.equalsIgnoreCase("ms")) {
			LocaleContextHolder.setLocale(new Locale("ms", "MY"));
		} else {
			LocaleContextHolder.setLocale(new Locale(locale));
		}
		
		SearchChannelMessageResponseResource messages = adminService.auditDetailReportChannelMessages(req);
		JasperReportBuilder report = reportService.build(messages);
		if(format.equals("DOCX")){
			response.setContentType("application/x-msdownload");            
			response.setHeader("Content-disposition", "attachment; filename=DetailReport_"+currentDate+".docx");
			report.toDocx(response.getOutputStream());
		} if(format.equals("CSV")){
			response.setContentType("application/vnd.ms-excel");            
			response.setHeader("Content-disposition", "attachment; filename=DetailReport_"+currentDate+".xlsx");
			JasperXlsxExporterBuilder xlsxExporter = DynamicReports.export.xlsxExporter(response.getOutputStream());
	        xlsxExporter.setCollapseRowSpan(false);
	        xlsxExporter.setRemoveEmptySpaceBetweenColumns(true);
	        xlsxExporter.setRemoveEmptySpaceBetweenRows(false);
	        xlsxExporter.setDetectCellType(true);
	        xlsxExporter.setWhitePageBackground(false);
	        xlsxExporter.setIgnoreGraphics(false);
	        xlsxExporter.setOnePagePerSheet(true);
	        xlsxExporter.setMaxRowsPerSheet(Integer.MAX_VALUE);
	        report.toXlsx(xlsxExporter);
//			response.setContentType("text/csv");            
//			response.setHeader("Content-disposition", "attachment; filename=DetailReport.csv");
//			report.toCsv(response.getOutputStream());
		} else {
			response.addHeader("Content-disposition", "attachment;filename=DetailReport_"+currentDate+".pdf");
			response.setContentType("application/pdf");
			report.toPdf(response.getOutputStream());
		}
	}
	
	@RequestMapping(value = "/summaryReport",method= RequestMethod.GET)
	public void generateSummaryReport(@RequestParam("fromDate") String fromDate,
							@RequestParam("toDate") String toDate,
							@RequestParam("publishStatus") String status,
							@RequestParam("channelType") String channelType,
							@RequestParam("fileFormat") String format,
							@RequestParam("Accept-Language") String locale,
							HttpServletResponse response) throws DRException, IOException{
		 
		SummaryReportRequestResource req = new SummaryReportRequestResource();
		req.setStatus(status);
		req.setChannelType(channelType);
		req.setToDate(toDate);
		req.setFromDate(fromDate);
		
		if (locale.equalsIgnoreCase("ms")) {
			LocaleContextHolder.setLocale(new Locale("ms", "MY"));
		} else {
			LocaleContextHolder.setLocale(new Locale(locale));
		}
		
		Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy") ;
        String currentDate =dateFormat.format(date);
		
		SummaryReportResponseResource messages = adminService.summaryReportChannelMessages(req);
		messages.setFromDate(fromDate);
		messages.setToDate(toDate);
		JasperReportBuilder report = reportService.buildSummaryReport(messages);
		if(format.equals("DOCX")){
			response.setContentType("application/x-msdownload");            
			response.setHeader("Content-disposition", "attachment; filename=SummaryReport_"+currentDate+".docx");
			report.toDocx(response.getOutputStream());
		} if(format.equals("CSV")){
			response.setContentType("application/vnd.ms-excel");            
			response.setHeader("Content-disposition", "attachment; filename=SummaryReport_"+currentDate+".xlsx");
			JasperXlsxExporterBuilder xlsxExporter = DynamicReports.export.xlsxExporter(response.getOutputStream());
	        xlsxExporter.setCollapseRowSpan(false);
	        xlsxExporter.setRemoveEmptySpaceBetweenColumns(true);
	        xlsxExporter.setRemoveEmptySpaceBetweenRows(false);
	        xlsxExporter.setDetectCellType(true);
	        xlsxExporter.setWhitePageBackground(false);
	        xlsxExporter.setIgnoreGraphics(false);
	        xlsxExporter.setOnePagePerSheet(true);
	        xlsxExporter.setMaxRowsPerSheet(Integer.MAX_VALUE);
	        report.toXlsx(xlsxExporter);
		} else {
			response.addHeader("Content-disposition", "attachment;filename=SummaryReport_"+currentDate+".pdf");
			response.setContentType("application/pdf");
			report.toPdf(response.getOutputStream());
		}
	}
	
	@RequestMapping(value = "/recentDissemination" ,method = RequestMethod.POST)
	public ResponseEntity<SearchChannelMessageResponseResource> recentDissemination(){		
		SearchChannelMessageResponseResource response = adminService.recentDissemination();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/channelTypes" ,method = RequestMethod.POST)
	public ResponseEntity<ChannelTypesResponseResource> channelTypes(){		
		String serviceEndpoint=integrationService.resolveEndPoint(RequestParameters.channelType.toString(), false);
		ChannelTypesResponseResource channelTypes = integrationService.getChannelTypes(serviceEndpoint);
		return new ResponseEntity<>(channelTypes, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/severityTypes" ,method = RequestMethod.POST)
	public ResponseEntity<SeverityLevelsResponseResource> severityTypes(){		
		String serviceEndpoint=integrationService.resolveEndPoint(RequestParameters.severityLevel.toString(), false);
		SeverityLevelsResponseResource severityTypes = integrationService.getSeverityTypes(serviceEndpoint);
		return new ResponseEntity<>(severityTypes, HttpStatus.OK);
	}
		
	

}
