package my.mimos.misos.web.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.command.DisseminationAdminCommand;
import my.mimos.misos.common.enums.StatusType;
import my.mimos.misos.domain.entity.trx.MisosReportView;
import my.mimos.misos.domain.resource.SearchChannelMessageResource;
import my.mimos.misos.domain.resource.SearchChannelMessageResponseResource;
import my.mimos.misos.domain.resource.SearchChannelRequestResource;
import my.mimos.misos.domain.resource.SummaryReportRequestResource;
import my.mimos.misos.domain.resource.SummaryReportResource;
import my.mimos.misos.domain.resource.SummaryReportResponseResource;
import my.mimos.misos.mapper.MisosReportMapper;
import my.mimos.misos.service.IntegrationService;
import my.mimos.misos.web.service.DisseminationAdminService;
import my.mimos.misos.web.util.IntegrationServiceUtil;
/**
 * 
 * @author beaula.fernandez
 *
 */

@Log4j
@Component
public class DisseminationAdminServiceImpl implements DisseminationAdminService{

	@Autowired
	DisseminationAdminCommand adminCommand;
	
	@Autowired
	MisosReportMapper misosReportMapper;
	
	@Autowired
	IntegrationService integrationService;
	
	@Autowired
	IntegrationServiceUtil integrationServiceUtil;
	
	@Override
	public SummaryReportResponseResource summaryReportChannelMessages(SummaryReportRequestResource req) {
		SummaryReportResponseResource response = new SummaryReportResponseResource();
		try {
			List<SummaryReportResource> respList = adminCommand.summaryReportChannelMessages(req);
			Map<String,String> channelTypeMap = integrationServiceUtil.getAllChannelTypes();			
			respList.forEach(message -> 
			message.setChannelType(channelTypeMap.get(message.getChannelType())));
			response.setSummaryReportList(respList);
			response.setStatusType(StatusType.SUCCESS);
			response.setStatusCode("S0010");
			response.setStatus("successfully retrieved channel messages");
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			log.error(ExceptionUtils.getStackTrace(e));
			response.setStatusType(StatusType.ERROR);
			response.setStatusCode("E1021");
			response.setStatus("error in retrieved channel messages");
		}
		return response;
	}
	
	public SearchChannelMessageResponseResource auditDetailReportChannelMessages(SearchChannelRequestResource req) {
		SearchChannelMessageResponseResource response = new SearchChannelMessageResponseResource();
		try {
			List<MisosReportView> respList = adminCommand.auditDetailReportChannelMessages(req);
			List<SearchChannelMessageResource> channelMsgLst=misosReportMapper.getMapperFacade().mapAsList(respList, SearchChannelMessageResource.class);
			Map<String,String> channelTypeMap = integrationServiceUtil.getAllChannelTypes();			
			channelMsgLst.forEach(message -> 
			message.setChannelType(channelTypeMap.get(message.getChannelType())));					

			Map<String,String> severityTypeMap = integrationServiceUtil.getAllSeverityLevels();
			channelMsgLst.forEach(message -> 
			message.setSeverityLevel(severityTypeMap.get(message.getSeverityLevel())));
			
			Map<String,String> notificationTypeMap = integrationServiceUtil.getAllNotificationTypes();					
			channelMsgLst.forEach(message -> 
			message.setNotificationType(notificationTypeMap.get(message.getNotificationType())));
			
			response.setChannelMessageList(channelMsgLst);
			response.setStatusType(StatusType.SUCCESS);
			response.setStatusCode("S0010");
			response.setStatus("successfully retrieved channel messages");	
			log.info("successfully retrieved records!" + response );
		} 
		catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			response.setStatusType(StatusType.ERROR);
			response.setStatusCode("E1021");
			response.setStatus("error in retrieved channel messages");
		}
		return response;
	}

	@Override
	public SearchChannelMessageResponseResource recentDissemination() {
		SearchChannelMessageResponseResource response = new SearchChannelMessageResponseResource();
		try{
			List<MisosReportView> respList = adminCommand.recentDissemination();
			List<SearchChannelMessageResource> channelMsgLst=misosReportMapper.getMapperFacade().mapAsList(respList, SearchChannelMessageResource.class);
			
			Map<String,String> channelTypeMap = integrationServiceUtil.getAllChannelTypes();			
			channelMsgLst.forEach(message -> 
			message.setChannelType(channelTypeMap.get(message.getChannelType())));					

			Map<String,String> severityTypeMap = integrationServiceUtil.getAllSeverityLevels();
			channelMsgLst.forEach(message -> 
			message.setSeverityLevel(severityTypeMap.get(message.getSeverityLevel())));
			
			Map<String,String> notificationTypeMap = integrationServiceUtil.getAllNotificationTypes();					
			channelMsgLst.forEach(message -> 
			message.setNotificationType(notificationTypeMap.get(message.getNotificationType())));
			
			response.setChannelMessageList(channelMsgLst);
			response.setStatusType(StatusType.SUCCESS);
			response.setStatusCode("S0010");
			response.setStatus("successfully retrieved channel messages");	
			log.info("successfully retrieved records!");
			
		}catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			response.setStatusType(StatusType.ERROR);
			response.setStatusCode("E1021");
			response.setStatus("error in retrieved channel messages");
		}
		return response;
	}

}
