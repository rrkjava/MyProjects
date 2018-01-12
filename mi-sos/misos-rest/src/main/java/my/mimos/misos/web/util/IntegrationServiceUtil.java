package my.mimos.misos.web.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import my.mimos.misos.common.enums.RequestParameters;
import my.mimos.misos.domain.integrationservice.ChannelTypeResource;
import my.mimos.misos.domain.integrationservice.ChannelTypesResponseResource;
import my.mimos.misos.domain.integrationservice.NotificationTypeResource;
import my.mimos.misos.domain.integrationservice.NotificationTypesResponseResource;
import my.mimos.misos.domain.integrationservice.SeverityLevelResource;
import my.mimos.misos.domain.integrationservice.SeverityLevelsResponseResource;
import my.mimos.misos.service.IntegrationService;

@Component
public class IntegrationServiceUtil {
	
	@Autowired
	IntegrationService integrationService;
	
	public Map<String,String> convertChannelTypesListToMap(List<ChannelTypeResource> channelTypesList){
		Map<String,String> channelTypesMap = new HashMap<String,String>();
		try{
			channelTypesList.forEach(channelType -> channelTypesMap.put(channelType.getTargetChannelTypeCode(), channelType.getTargetChannelType()));
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return channelTypesMap;		
	}
	
	public Map<String,String> convertNotificationTypesListToMap(List<NotificationTypeResource> notificationTypeList){
		Map<String,String> notificationTypesMap = new HashMap<String,String>();
		try{
			notificationTypeList.forEach(notificationType -> notificationTypesMap.put(notificationType.getNotificationTypeCode(), notificationType.getNotificationType()));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return notificationTypesMap;		
	}	
	
	public Map<String,String> convertSeverityTypesListToMap(List<SeverityLevelResource> severityLevelList){
		Map<String,String> severityLevelMap = new HashMap<String,String>();
		try{
			severityLevelList.forEach(severityLevel -> severityLevelMap.put(severityLevel.getSeverityLevelCode(), severityLevel.getSeverityLevel()));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return severityLevelMap;
		
	}
	
	public Map<String,String> getAllChannelTypes(){		
		Map<String,String> channelTypeMap= null;
		try{
			String serviceEndpoint=integrationService.resolveEndPoint(RequestParameters.channelType.toString(), false);
			ChannelTypesResponseResource channelTypes = integrationService.getChannelTypes(serviceEndpoint);
			channelTypeMap=convertChannelTypesListToMap(channelTypes.getChannelTypes());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return channelTypeMap;
	}
	
	public Map<String,String> getAllNotificationTypes(){		
		Map<String,String> notificationTypesMap= null;
		try{
			String serviceEndpoint=integrationService.resolveEndPoint(RequestParameters.notificationType.toString(), false);
			NotificationTypesResponseResource notificationTypes = integrationService.getNotificationTypes(serviceEndpoint);
			notificationTypesMap=convertNotificationTypesListToMap(notificationTypes.getNotificationTypes());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return notificationTypesMap;
	}
	
	public Map<String,String> getAllSeverityLevels(){		
		Map<String,String> severityLevelsMap= null;
		try{
			String serviceEndpoint=integrationService.resolveEndPoint(RequestParameters.severityLevel.toString(), false);
			SeverityLevelsResponseResource severityTypes = integrationService.getSeverityTypes(serviceEndpoint);
			severityLevelsMap=convertSeverityTypesListToMap(severityTypes.getSeverityLevels());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return severityLevelsMap;
	}
	

}
