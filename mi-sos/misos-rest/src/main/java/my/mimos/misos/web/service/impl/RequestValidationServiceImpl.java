package my.mimos.misos.web.service.impl;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.common.enums.RequestParameters;
import my.mimos.misos.common.enums.StatusType;
import my.mimos.misos.config.IntegrationServiceConfig;
import my.mimos.misos.domain.integrationservice.CertainityLevelResource;
import my.mimos.misos.domain.integrationservice.CertainityLevelResponseResource;
import my.mimos.misos.domain.integrationservice.ChannelTypeResource;
import my.mimos.misos.domain.integrationservice.ChannelTypeResponseResource;
import my.mimos.misos.domain.integrationservice.LocationTypeResource;
import my.mimos.misos.domain.integrationservice.LocationTypeResponseResource;
import my.mimos.misos.domain.integrationservice.MessageFormatResource;
import my.mimos.misos.domain.integrationservice.MessageFormatResponseResource;
import my.mimos.misos.domain.integrationservice.MessageTemplateResource;
import my.mimos.misos.domain.integrationservice.MessageTemplateResponseResource;
import my.mimos.misos.domain.integrationservice.MessageTypeResource;
import my.mimos.misos.domain.integrationservice.MessageTypeResponseResource;
import my.mimos.misos.domain.integrationservice.NotificationTypeResource;
import my.mimos.misos.domain.integrationservice.NotificationTypeResponseResource;
import my.mimos.misos.domain.integrationservice.SeverityLevelResource;
import my.mimos.misos.domain.integrationservice.SeverityLevelResponseResource;
import my.mimos.misos.domain.integrationservice.UserGroupResource;
import my.mimos.misos.domain.integrationservice.UserGroupResponseResource;
import my.mimos.misos.domain.resource.DisseminationResponseResource;
import my.mimos.misos.service.IntegrationService;
import my.mimos.misos.web.service.RequestValidationService;

/**
 * 
 * @author beaula.fernandez
 *
 */
@Log4j
@Component
public class RequestValidationServiceImpl implements RequestValidationService{

	@Autowired
	IntegrationServiceConfig intergrationServiceConfig;

	@Autowired
	IntegrationService integrationService;

	/**
	 * Form the integration service url from the json parameter
	 */
	public boolean getDataFromIntegrationService(String requestJsonKey,String requestJsonValue){

		String endPoint=null;
		boolean isExists = true;
		try{
			endPoint =	integrationService.resolveEndPoint(requestJsonKey, true);

			if(requestJsonKey.equalsIgnoreCase(RequestParameters.channelType.toString())){				
				isExists=isChannelTypeExists(requestJsonKey,requestJsonValue,endPoint);
			}else if(requestJsonKey.equalsIgnoreCase(RequestParameters.targetUserGroups.toString())){
				isExists=isTargetUserGroupsExists(requestJsonKey,requestJsonValue,endPoint);				
			}else if(requestJsonKey.equalsIgnoreCase(RequestParameters.messageFormat.toString())){
				isExists=isMessageFormatExists(requestJsonKey,requestJsonValue,endPoint);
			}else if(requestJsonKey.equalsIgnoreCase(RequestParameters.messageType.toString())){
				isExists=isMessageTypeExists(requestJsonKey,requestJsonValue,endPoint);
			}else if(requestJsonKey.equalsIgnoreCase(RequestParameters.notificationType.toString())){	
				isExists=isNotificationTypeExists(requestJsonKey,requestJsonValue,endPoint);
			}else if(requestJsonKey.equalsIgnoreCase(RequestParameters.locationType.toString())){
				isExists=isLocationTypeExists(requestJsonKey,requestJsonValue,endPoint);
			}else if(requestJsonKey.equalsIgnoreCase(RequestParameters.severityLevel.toString())){
				isExists=isSeverityLevelExists(requestJsonKey,requestJsonValue,endPoint);
			}else if(requestJsonKey.equalsIgnoreCase(RequestParameters.certainityLevel.toString())){
				isExists=isCertainityLevelExists(requestJsonKey,requestJsonValue,endPoint);
			}else if(requestJsonKey.equalsIgnoreCase(RequestParameters.refTemplateId.toString())){
				isExists=isMessageTemplateExists(requestJsonKey,requestJsonValue,endPoint);
			}
		}catch(NullPointerException nullException){

			log.error("Unexpected NULL value! Please check the configuration for Integration service.");		
			nullException.printStackTrace();
			throw new RuntimeException("Error in calling integration services");

		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException("Error in calling integration services");
		}
		return isExists;

	}	


	/**
	 * loop through input json to validate the json inout parmeters to check if present in the database
	 */
	public void validateJson(String jsonString, DisseminationResponseResource response) throws RuntimeException {
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(jsonString.getBytes());
			String requestParamKey=null; // Holds the request Json input parameters
			String requestParamValue=null; //Holds the value of Json input parameters
			boolean isExists = true; // check if the value exists in the master tables in db

			Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
			while (iterator.hasNext()) {

				Map.Entry<String, JsonNode> item = iterator.next();			

				if (item.getValue().isArray()) {				
					for (JsonNode channelNode : item.getValue()) {
						Iterator<Map.Entry<String, JsonNode>> channelIterator = channelNode.fields();						
						while (channelIterator.hasNext()) {	
							Map.Entry<String, JsonNode> channelItem = channelIterator.next();	
							requestParamKey = channelItem.getKey().toString();
							requestParamValue = channelItem.getValue().asText();

							if(EnumUtils.isValidEnum(RequestParameters.class,requestParamKey) 
									&& StringUtils.isNotEmpty(requestParamValue) && (!requestParamValue.equals("null")) ){
								isExists=getDataFromIntegrationService(requestParamKey,requestParamValue);
								if(isExists == false){
									throw new RuntimeException(requestParamKey + " is invalid! The value doesnot exists in the database. ");
								}
							}															
						}
					}
				}
				else{
					requestParamKey = item.getKey().toString();
					requestParamValue = item.getValue().asText();
					if(EnumUtils.isValidEnum(RequestParameters.class,requestParamKey)  && StringUtils.isNotEmpty(requestParamValue)){
						isExists=getDataFromIntegrationService(requestParamKey,requestParamValue);
						if(isExists == false){
							throw new RuntimeException(requestParamKey + " is invalid! The value doesnot exists in the database.");
						}
					}
				}
			}

		} catch (RuntimeException re) {			
			response.setStatusType(StatusType.ERROR);
			response.setStatusCode("E0030");			
			response.setStatus(re.getMessage());			
			throw re;

		} catch (Exception e) {

			throw new RuntimeException(e);

		}
	}

	/**
	 * Checks if channel type exists in the database
	 * @param requestJsonKey
	 * @param requestJsonValue
	 * @param endPoint
	 * @return
	 */
	public boolean isChannelTypeExists(String requestJsonKey,String requestJsonValue,String endPoint){

		ChannelTypeResponseResource response = integrationService.getChannelTypeByCode(requestJsonValue, endPoint);

		if(response.getStatus().getStatusCode().equals("200")){ 
			ChannelTypeResource channel=response.getChannelType();
			if(StringUtils.isNotEmpty(channel.getTargetChannelTypeCode())){
				log.info("Integration service : The channel type exists in the database!");
				return true;
			}else{
				log.error("The channel type doesnot in the database!");
				return false;
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
			return false;
		}		
	}

	/**
	 * check if target user group exists in the database
	 * @param requestJsonKey
	 * @param requestJsonValue
	 * @param endPoint
	 * @return
	 */
	public boolean isTargetUserGroupsExists(String requestJsonKey,String requestJsonValue,String endPoint){

		UserGroupResponseResource response = integrationService.getTargetUserGroupsByCode(requestJsonValue, endPoint);

		if(response.getStatus().getStatusCode().equals("200")){ 
			UserGroupResource userGroup=response.getUserGroup();
			if(StringUtils.isNotEmpty(userGroup.getUserGroupCode())){
				log.info("Integration service : The user group exists in the database!");
				return true;
			}else{
				log.error("The user group doesnot in the database!");
				return false;
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
			return false;
		}		
	}

	/**
	 * check if message format present in the database
	 * @param requestJsonKey
	 * @param requestJsonValue
	 * @param endPoint
	 * @return
	 */
	public boolean isMessageFormatExists(String requestJsonKey,String requestJsonValue,String endPoint){

		MessageFormatResponseResource response = integrationService.getMessageFormatByCode(requestJsonValue, endPoint);

		if(response.getStatus().getStatusCode().equals("200")){ 
			MessageFormatResource messageFormat=response.getMessageFormat();
			if(StringUtils.isNotEmpty(messageFormat.getMessageFormatCode())){
				log.info("Integration service : The message format exists in the database!");
				return true;
			}else{
				log.error("The message format doesnot in the database!");
				return false;
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
			return false;
		}		
	}

	/**
	 * checks if message type present in the database
	 * @param requestJsonKey
	 * @param requestJsonValue
	 * @param endPoint
	 * @return
	 */
	public boolean isMessageTypeExists(String requestJsonKey,String requestJsonValue,String endPoint){

		MessageTypeResponseResource response= integrationService.getMessageTypeByCode(requestJsonValue, endPoint);

		if(response.getStatus().getStatusCode().equals("200")){ 
			MessageTypeResource messageType=response.getMessageType();
			if(StringUtils.isNotEmpty(messageType.getMessageTypeCode())){
				log.info("Integration service : The message type exists in the database!");
				return true;
			}else{
				log.error("The message type doesnot in the database!");
				return false;
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
			return false;
		}		
	}

	/**
	 * checks if notification type presents in the database
	 * @param requestJsonKey
	 * @param requestJsonValue
	 * @param endPoint
	 * @return
	 */
	public boolean isNotificationTypeExists(String requestJsonKey,String requestJsonValue,String endPoint){

		NotificationTypeResponseResource response=integrationService.getNotificationTypeByCode(requestJsonValue, endPoint);

		if(response.getStatus().getStatusCode().equals("200")){ 
			NotificationTypeResource notificationType=response.getNotificationType();
			if(StringUtils.isNotEmpty(notificationType.getNotificationTypeCode())){
				log.info("Integration service : The notification type exists in the database!");
				return true;
			}else{
				log.error("The notification type doesnot in the database!");
				return false;
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
			return false;
		}		
	}

	/**
	 * checks if location type exists in the database
	 * @param requestJsonKey
	 * @param requestJsonValue
	 * @param endPoint
	 * @return
	 */
	public boolean isLocationTypeExists(String requestJsonKey,String requestJsonValue,String endPoint){

		LocationTypeResponseResource response=integrationService.getLocationTypeByCode(requestJsonValue, endPoint);

		if(response.getStatus().getStatusCode().equals("200")){ 
			LocationTypeResource locationType=response.getLocationType();
			if(StringUtils.isNotEmpty(locationType.getLocationTypeCode())){
				log.info("Integration service : The Location type exists in the database!");
				return true;
			}else{
				log.error("The Location type doesnot in the database!");
				return false;
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
			return false;
		}		
	}

	/**
	 * checks if certainity level present in the database
	 * @param requestJsonKey
	 * @param requestJsonValue
	 * @param endPoint
	 * @return
	 */
	public boolean isCertainityLevelExists(String requestJsonKey,String requestJsonValue,String endPoint){		

		CertainityLevelResponseResource response=integrationService.getCertainityLevelByCode(requestJsonValue, endPoint);

		if(response.getStatus().getStatusCode().equals("200")){ 
			CertainityLevelResource CertainityLevel=response.getCertainityLevel();
			if(StringUtils.isNotEmpty(CertainityLevel.getCertainityLevelCode())){
				log.info("Integration service : The severity level exists in the database!");
				return true;
			}else{
				log.error("The severity level doesnot in the database!");
				return false;
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
			return false;
		}		
	}

	/**
	 * check if severity level present in the database
	 * @param requestJsonKey
	 * @param requestJsonValue
	 * @param endPoint
	 * @return
	 */
	public boolean isSeverityLevelExists(String requestJsonKey,String requestJsonValue,String endPoint){

		SeverityLevelResponseResource response=integrationService.getSeverityLevelByCode(requestJsonValue, endPoint);

		if(response.getStatus().getStatusCode().equals("200")){ 
			SeverityLevelResource severityLevel=response.getSeverityLevel();
			if(StringUtils.isNotEmpty(severityLevel.getSeverityLevelCode())){
				log.info("Integration service : The severity level exists in the database!");
				return true;
			}else{
				log.error("The severity level doesnot in the database!");
				return false;
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
			return false;
		}		
	}

	/**
	 * check if message template present in the database
	 * @param requestJsonKey
	 * @param requestJsonValue
	 * @param endPoint
	 * @return
	 */
	public boolean isMessageTemplateExists(String requestJsonKey,String requestJsonValue,String endPoint){

		MessageTemplateResponseResource response= integrationService.getMessageTemplateById(requestJsonValue, endPoint);

		if(response.getStatus().getStatusCode().equals("200")){ 
			MessageTemplateResource messageTemplate=response.getMessageTemplate();
			if(StringUtils.isNotEmpty(messageTemplate.getMessageTemplateId())){
				log.info("Integration service : The message template exists in the database!");
				return true;
			}else{
				log.error("The message template doesnot in the database!");
				return false;
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
			return false;
		}		
	}


}
