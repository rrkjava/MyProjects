package my.mimos.misos.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.command.handler.DisseminationCommandHandler;
import my.mimos.misos.common.enums.RequestParameters;
import my.mimos.misos.config.AdminConfig;
import my.mimos.misos.config.IntegrationServiceConfig;
import my.mimos.misos.domain.entity.trx.Message;
import my.mimos.misos.domain.integrationservice.ApplicationAlertRequestResource;
import my.mimos.misos.domain.integrationservice.ApplicationAlertResource;
import my.mimos.misos.domain.integrationservice.BaseResponseStatusResource;
import my.mimos.misos.domain.integrationservice.CertainityLevelRequestResource;
import my.mimos.misos.domain.integrationservice.CertainityLevelResource;
import my.mimos.misos.domain.integrationservice.CertainityLevelResponseResource;
import my.mimos.misos.domain.integrationservice.ChannelRecipientRequestResource;
import my.mimos.misos.domain.integrationservice.ChannelRecipientResource;
import my.mimos.misos.domain.integrationservice.ChannelRecipientResponseResource;
import my.mimos.misos.domain.integrationservice.ChannelTypeRequestResource;
import my.mimos.misos.domain.integrationservice.ChannelTypeResource;
import my.mimos.misos.domain.integrationservice.ChannelTypeResponseResource;
import my.mimos.misos.domain.integrationservice.ChannelTypesResponseResource;
import my.mimos.misos.domain.integrationservice.DisseminationChannel;
import my.mimos.misos.domain.integrationservice.ImageRequestResource;
import my.mimos.misos.domain.integrationservice.ImageResource;
import my.mimos.misos.domain.integrationservice.LocationTypeRequestResource;
import my.mimos.misos.domain.integrationservice.LocationTypeResource;
import my.mimos.misos.domain.integrationservice.LocationTypeResponseResource;
import my.mimos.misos.domain.integrationservice.MessageFormatRequestResource;
import my.mimos.misos.domain.integrationservice.MessageFormatResource;
import my.mimos.misos.domain.integrationservice.MessageFormatResponseResource;
import my.mimos.misos.domain.integrationservice.MessageTemplateRequestResource;
import my.mimos.misos.domain.integrationservice.MessageTemplateResource;
import my.mimos.misos.domain.integrationservice.MessageTemplateResponseResource;
import my.mimos.misos.domain.integrationservice.MessageTypeRequestResource;
import my.mimos.misos.domain.integrationservice.MessageTypeResource;
import my.mimos.misos.domain.integrationservice.MessageTypeResponseResource;
import my.mimos.misos.domain.integrationservice.NotificationTypeRequestResource;
import my.mimos.misos.domain.integrationservice.NotificationTypeResource;
import my.mimos.misos.domain.integrationservice.NotificationTypeResponseResource;
import my.mimos.misos.domain.integrationservice.NotificationTypesResponseResource;
import my.mimos.misos.domain.integrationservice.Poi;
import my.mimos.misos.domain.integrationservice.RecipientRequestResource;
import my.mimos.misos.domain.integrationservice.RecipientResponseResource;
import my.mimos.misos.domain.integrationservice.SeverityLevelRequestResource;
import my.mimos.misos.domain.integrationservice.SeverityLevelResource;
import my.mimos.misos.domain.integrationservice.SeverityLevelResponseResource;
import my.mimos.misos.domain.integrationservice.SeverityLevelsResponseResource;
import my.mimos.misos.domain.integrationservice.SocialAccountRequestResource;
import my.mimos.misos.domain.integrationservice.SocialAccountResponseResource;
import my.mimos.misos.domain.integrationservice.UserGroupRequestResource;
import my.mimos.misos.domain.integrationservice.UserGroupResource;
import my.mimos.misos.domain.integrationservice.UserGroupResponseResource;
import my.mimos.misos.service.IntegrationService;
import my.mimos.misos.util.SSLCertHandler;


/**
 * 
 * @author beaula.fernandez
 *
 */

@Log4j
@Component
public class IntegrationServiceImpl implements IntegrationService{

	@Autowired
	IntegrationServiceConfig intergrationServiceConfig;
	
	
	@Autowired
	private DisseminationCommandHandler disseminationCommand;
	
	@Autowired
	AdminConfig adminConfig;

	/**
	 * Get channel type from main database through integration service
	 * @param id	 
	 * @param endPoint
	 * @return 
	 */
	public ChannelTypeResponseResource getChannelTypeByCode(String channelTypeCode,String endPoint){

		RestTemplate rest = new RestTemplate();	

		ChannelTypeRequestResource channelTypeRequestResource = new ChannelTypeRequestResource();
		channelTypeRequestResource.setChannelTypeCode(channelTypeCode);
		channelTypeRequestResource.setDescription("channel_type_code");
		channelTypeRequestResource.setMethodType("1");
		channelTypeRequestResource.setTransactionFrom("Dissemination");
		
		SSLCertHandler.disableSslVerification(); 
		
		ChannelTypeResponseResource response=rest.postForObject(endPoint, 
				channelTypeRequestResource, ChannelTypeResponseResource.class);
		log.info("Response from integration service : " +response);

		if(response.getStatus().getStatusCode().equals("200")){ 
			ChannelTypeResource channel=response.getChannelType();
			if(StringUtils.isNotEmpty(channel.getTargetChannelTypeCode())){
				log.info("Integration service : The channel type exists in the database!");
			}else{
				log.error("The channel type doesnot in the database!");
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
		}
		return response;		
	}

	/**
	 * Get target user group from main database through integration service
	 * @param id	 
	 * @param endPoint
	 * @return
	 */
	public UserGroupResponseResource getTargetUserGroupsByCode(String targetUserGroupCode,String endPoint){

		RestTemplate rest = new RestTemplate();	

		UserGroupRequestResource userGroupRequestResource = new UserGroupRequestResource();
		userGroupRequestResource.setUserGroupCode(targetUserGroupCode);
		userGroupRequestResource.setDescription("user_group_code");
		userGroupRequestResource.setMethodType("1");
		userGroupRequestResource.setTransactionFrom("dissemination");

		SSLCertHandler.disableSslVerification();
		
		UserGroupResponseResource response=rest.postForObject(endPoint, 
				userGroupRequestResource, UserGroupResponseResource.class);
		log.info("Response from integration service : " +response);

		if(response.getStatus().getStatusCode().equals("200")){ 
			UserGroupResource userGroup=response.getUserGroup();
			if(StringUtils.isNotEmpty(userGroup.getUserGroupCode())){
				log.info("Integration service : The user group exists in the database!");
			}else{
				log.error("The user group doesnot in the database!");
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
		}
		return response;		
	}

	/**
	 * Get message format from main database through integration service
	 * @param id	 
	 * @param endPoint
	 * @return
	 */
	public MessageFormatResponseResource getMessageFormatByCode(String messageFormatCode,String endPoint){

		RestTemplate rest = new RestTemplate();	

		MessageFormatRequestResource messageFormatRequestResource = new MessageFormatRequestResource();
		messageFormatRequestResource.setMessageFormatCode(messageFormatCode);
		messageFormatRequestResource.setDescription("message_format_code");
		messageFormatRequestResource.setMethodType("1");
		messageFormatRequestResource.setTransactionFrom("dissemination");

		SSLCertHandler.disableSslVerification();
		
		MessageFormatResponseResource response=rest.postForObject(endPoint, 
				messageFormatRequestResource, MessageFormatResponseResource.class);
		log.info("Response from integration service : " +response);

		if(response.getStatus().getStatusCode().equals("200")){ 
			MessageFormatResource messageFormat=response.getMessageFormat();
			if(StringUtils.isNotEmpty(messageFormat.getMessageFormatCode())){
				log.info("Integration service : The message format exists in the database!");
			}else{
				log.error("The message format doesnot in the database!");
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
		}
		return response;		
	}

	/**
	 * Gets message type from main database through integration service
	 * @param id	 
	 * @param endPoint
	 * @return
	 */
	public MessageTypeResponseResource getMessageTypeByCode(String messageTypeCode,String endPoint){

		RestTemplate rest = new RestTemplate();	

		MessageTypeRequestResource messageTypeRequestResource = new MessageTypeRequestResource();
		messageTypeRequestResource.setMessageTypeCode(messageTypeCode);
		messageTypeRequestResource.setDescription("message_type_code");
		messageTypeRequestResource.setMethodType("1");
		messageTypeRequestResource.setTransactionFrom("dissemination");
		
		SSLCertHandler.disableSslVerification();

		MessageTypeResponseResource response=rest.postForObject(endPoint, 
				messageTypeRequestResource, MessageTypeResponseResource.class);
		log.info("Response from integration service : " +response);

		if(response.getStatus().getStatusCode().equals("200")){ 
			MessageTypeResource messageType=response.getMessageType();
			if(StringUtils.isNotEmpty(messageType.getMessageTypeCode())){
				log.info("Integration service : The message type exists in the database!");
			}else{
				log.error("The message type doesnot in the database!");
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
		}
		return response;		
	}

	/**
	 * Gets notification type from main database through integration service
	 * @param id	 
	 * @param endPoint
	 * @return
	 */
	public NotificationTypeResponseResource getNotificationTypeByCode(String notificationTypeCode,String endPoint){

		RestTemplate rest = new RestTemplate();	

		NotificationTypeRequestResource notificationTypeRequestResource = new NotificationTypeRequestResource();
		notificationTypeRequestResource.setNotificationTypeCode(notificationTypeCode);
		notificationTypeRequestResource.setDescription("notification_type_code");
		notificationTypeRequestResource.setMethodType("1");
		notificationTypeRequestResource.setTransactionFrom("dissemination");
		
		SSLCertHandler.disableSslVerification();

		NotificationTypeResponseResource response=rest.postForObject(endPoint, 
				notificationTypeRequestResource, NotificationTypeResponseResource.class);
		log.info("Response from integration service : " +response);

		if(response.getStatus().getStatusCode().equals("200")){ 
			NotificationTypeResource notificationType=response.getNotificationType();
			if(StringUtils.isNotEmpty(notificationType.getNotificationTypeCode())){
				log.info("Integration service : The notification type exists in the database!");
			}else{
				log.error("The notification type doesnot in the database!");
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
		}
		return response;		
	}

	/**
	 * Gets location type from main database through integration service
	 * @param id	 
	 * @param endPoint
	 * @return
	 */
	public LocationTypeResponseResource getLocationTypeByCode(String locationTypeCode,String endPoint){

		RestTemplate rest = new RestTemplate();	

		LocationTypeRequestResource locationTypeRequestResource = new LocationTypeRequestResource();
		locationTypeRequestResource.setLocationTypeCode(locationTypeCode);
		locationTypeRequestResource.setDescription("location_type_code");
		locationTypeRequestResource.setMethodType("1");
		locationTypeRequestResource.setTransactionFrom("dissemination");
		
		SSLCertHandler.disableSslVerification();

		LocationTypeResponseResource response=rest.postForObject(endPoint, 
				locationTypeRequestResource, LocationTypeResponseResource.class);
		log.info("Response from integration service : " +response);

		if(response.getStatus().getStatusCode().equals("200")){ 
			LocationTypeResource locationType=response.getLocationType();
			if(StringUtils.isNotEmpty(locationType.getLocationTypeCode())){
				log.info("Integration service : The Location type exists in the database!");
			}else{
				log.error("The Location type doesnot in the database!");
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
		}
		return response;		
	}

	/**
	 * Gets certainty level from main database through integration service
	 * @param id	 
	 * @param endPoint
	 * @return
	 */
	public CertainityLevelResponseResource getCertainityLevelByCode(String certainityLevelCode,String endPoint){

		RestTemplate rest = new RestTemplate();	

		CertainityLevelRequestResource certainityLevelRequestResource = new CertainityLevelRequestResource();
		certainityLevelRequestResource.setCertainityLevelCode(certainityLevelCode);
		certainityLevelRequestResource.setDescription("certainity_level_code");
		certainityLevelRequestResource.setMethodType("1");
		certainityLevelRequestResource.setTransactionFrom("dissemination");
		
		SSLCertHandler.disableSslVerification();

		CertainityLevelResponseResource response=rest.postForObject(endPoint, 
				certainityLevelRequestResource, CertainityLevelResponseResource.class);
		log.info("Response from integration service : " +response);

		if(response.getStatus().getStatusCode().equals("200")){ 
			CertainityLevelResource CertainityLevel=response.getCertainityLevel();
			if(StringUtils.isNotEmpty(CertainityLevel.getCertainityLevelCode())){
				log.info("Integration service : The certainity level exists in the database!");
			}else{
				log.error("The certainity level doesnot in the database!");
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
		}
		return response;		
	}

	/**
	 * Get severity level from main database through integration service
	 * @param id
	 * @param endPoint
	 * @return
	 */
	public SeverityLevelResponseResource getSeverityLevelByCode(String severityLevelCode,String endPoint){

		RestTemplate rest = new RestTemplate();	

		SeverityLevelRequestResource severityLevelRequestResource = new SeverityLevelRequestResource();
		severityLevelRequestResource.setSeverityLevelCode(severityLevelCode);
		severityLevelRequestResource.setDescription("severity_level_code");
		severityLevelRequestResource.setMethodType("1");
		severityLevelRequestResource.setTransactionFrom("dissemination");
		
		SSLCertHandler.disableSslVerification();

		SeverityLevelResponseResource response=rest.postForObject(endPoint, 
				severityLevelRequestResource, SeverityLevelResponseResource.class);
		log.info("Response from integration service : " +response);

		if(response.getStatus().getStatusCode().equals("200")){ 
			SeverityLevelResource severityLevel=response.getSeverityLevel();
			if(StringUtils.isNotEmpty(severityLevel.getSeverityLevelCode())){
				log.info("Integration service : The severity level exists in the database!");
			}else{
				log.error("The severity level doesnot in the database!");
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
		}
		return response;		
	}

	/**
	 * Get message template from main database through integration service
	 * @param id	 
	 * @param endPoint
	 * @return
	 */
	public MessageTemplateResponseResource getMessageTemplateById(String messageTemplateId,String endPoint){

		RestTemplate rest = new RestTemplate();	

		MessageTemplateRequestResource messageTemplateRequestResource = new MessageTemplateRequestResource();
		messageTemplateRequestResource.setMessageTemplateId(messageTemplateId);
		messageTemplateRequestResource.setDescription("message_template_id");
		messageTemplateRequestResource.setMethodType("1");
		messageTemplateRequestResource.setTransactionFrom("dissemination");
		
		SSLCertHandler.disableSslVerification();

		MessageTemplateResponseResource response=rest.postForObject(endPoint, 
				messageTemplateRequestResource, MessageTemplateResponseResource.class);
		log.info("Response from integration service : " +response);

		if(response.getStatus().getStatusCode().equals("200")){ 
			MessageTemplateResource messageTemplate=response.getMessageTemplate();
			if(StringUtils.isNotEmpty(messageTemplate.getMessageTemplateId())){
				log.info("Integration service : The message template exists in the database!");
			}else{
				log.error("The message template doesnot in the database!");
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
		}
		return response;		
	}

	/**
	 * Get All channel types from main database through integration service
	 * @param endPoint
	 * @return
	 */
	public ChannelTypesResponseResource getChannelTypes(String endPoint) {

		RestTemplate rest = new RestTemplate();	

		ChannelTypeRequestResource channelTypeRequestResource = new ChannelTypeRequestResource();
		channelTypeRequestResource.setDescription("channel_type");
		channelTypeRequestResource.setMethodType("1");
		channelTypeRequestResource.setTransactionFrom("Dissemination");
		
		SSLCertHandler.disableSslVerification();

		ChannelTypesResponseResource response=rest.postForObject(endPoint,channelTypeRequestResource,ChannelTypesResponseResource.class);
		log.info("Response from integration service : " +response);

		if(response.getStatus().getStatusCode().equals("200")){ 
			List<ChannelTypeResource> channels=response.getChannelTypes();
			if(!channels.isEmpty()){
				log.info("The integration service returned all channel types !");
			}else{
				log.error("No channel types returned from the integration service");
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
		}
		return response;	
	}

	/**
	 * Get All notification types from main database through integration service
	 * @param endPoint
	 * @return
	 */
	public NotificationTypesResponseResource getNotificationTypes(String endPoint) {

		RestTemplate rest = new RestTemplate();	

		NotificationTypeRequestResource notificationTypeRequestResource = new NotificationTypeRequestResource();
		notificationTypeRequestResource.setDescription("notification_type");
		notificationTypeRequestResource.setMethodType("1");
		notificationTypeRequestResource.setTransactionFrom("dissemination");

		SSLCertHandler.disableSslVerification();
		
		NotificationTypesResponseResource response=rest.postForObject(endPoint, 
				notificationTypeRequestResource, NotificationTypesResponseResource.class);
		log.info("Response from integration service : " +response);

		if(response.getStatus().getStatusCode().equals("200")){ 
			List<NotificationTypeResource> notificationTypes=response.getNotificationTypes();
			if(!notificationTypes.isEmpty()){
				log.info("The integration service returned all notification types!");
			}else{
				log.error("No notification types returned from the integration service!");
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
		}
		return response;
	}

	/**
	 * Get All severity types from main database through integration service
	 * @param endPoint
	 * @return
	 */
	public SeverityLevelsResponseResource getSeverityTypes(String endPoint) {

		RestTemplate rest = new RestTemplate();	

		SeverityLevelRequestResource severityLevelRequestResource = new SeverityLevelRequestResource();
		severityLevelRequestResource.setDescription("severity_level_code");
		severityLevelRequestResource.setMethodType("1");
		severityLevelRequestResource.setTransactionFrom("dissemination");
		
		SSLCertHandler.disableSslVerification();

		SeverityLevelsResponseResource response=rest.postForObject(endPoint, 
				severityLevelRequestResource, SeverityLevelsResponseResource.class);
		log.info("Response from integration service : " +response);

		if(response.getStatus().getStatusCode().equals("200")){ 
			List<SeverityLevelResource> severityLevels=response.getSeverityLevels();
			if(!severityLevels.isEmpty()){
				log.info("The integration service returned all  severity levels!");
			}else{
				log.error("No severity level returned from the integration service!");
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
		}
		return response;
	}
	
	/**
	 * Get users for dissemination based on poi and group and user type
	 * @param request
	 * @return
	 */
	public ChannelRecipientResponseResource getUsersByGroupAndPOI(ChannelRecipientRequestResource request){
		
		RestTemplate rest = new RestTemplate();	
		String endPoint = integrationServicesForUsers(true);
		
		SSLCertHandler.disableSslVerification();
		
		ChannelRecipientResponseResource response=rest.postForObject(endPoint, 
				request, ChannelRecipientResponseResource.class);
		log.info("Response from integration service for users : " +response);

		if(response.getStatus().getStatusCode().equals("200")){ 
			List<ChannelRecipientResource> users=response.getRecipientList();
			if(!users.isEmpty()){
				log.info("The integration service returned all users!");
			}else{
				log.error("No users returned from the integration service!");
			}
		}else if(response.getStatus().getStatusCode().equals("201")){
			log.error("No Content: "+response.getStatus().getStatus());
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
		}
		return response;		
	}
	
	/**
	 * Get All users for dissemination
	 * @param request
	 * @return
	 */
	public ChannelRecipientResponseResource getUsersAll(ChannelRecipientRequestResource request) {
		RestTemplate rest = new RestTemplate();	
		String endPoint = integrationServicesForUsers(false);
		
		SSLCertHandler.disableSslVerification();		
		
		ChannelRecipientResponseResource response=rest.postForObject(endPoint, 
				request, ChannelRecipientResponseResource.class);
		log.info("Response from integration service for users : " +response);

		if(response.getStatus().getStatusCode().equals("200")){ 
			List<ChannelRecipientResource> users=response.getRecipientList();
			if(!users.isEmpty()){
				log.info("The integration service returned all users!");
			}else{
				log.error("No users returned from the integration service!");
			}
		}else{
			log.error("Error from integration services : "+response.getStatus().getStatus());
		}
		return response;
	}
	
	/** To get all recipients for channel dissemination
	 * based on l
	 * @param request
	 * @return
	 */
	public RecipientResponseResource getRecipients(RecipientRequestResource request){
		
		RestTemplate rest = new RestTemplate();	
		String baseURL= intergrationServiceConfig.getBaseURL().concat(intergrationServiceConfig.getService());
		String endPoint = baseURL + intergrationServiceConfig.getChannelRecipients();
		RecipientResponseResource response= null;
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			log.info("service " + endPoint);
			log.debug("Reciepents request ============= " + mapper.writeValueAsString(request));
			SSLCertHandler.disableSslVerification();
			response=rest.postForObject(endPoint, request, RecipientResponseResource.class);
			log.info("Response from integration service for users : " + mapper.writeValueAsString(response));

			if (response.getStatus().getStatusCode().equals("200")) {
				DisseminationChannel users = response.getDisseminationChannel();
				if (!users.getEmailChannel().isEmpty()) {
					log.info("The integration service returned all emails!");
				} else if (!users.getSmsChannel().isEmpty()) {
					log.info("The integration service returned all sms numbers!");
				} else if (!users.getSirenChannel().isEmpty()) {
					log.info("The integration service returned all siren numbers!");
				} else if (!users.getFaxChannel().isEmpty()) {
					log.info("The integration service returned all fax emails!");
				} else if (!users.getMobileChannel().isEmpty()) {
					log.info("The integration service returned all mobile!");
				} else {
					log.error("No users returned from the integration service!");
				}
			}else if(response.getStatus().getStatusCode().equals("201")){
				log.error("No Content: "+response.getStatus().getStatus());
			}else{
				log.error("Error from integration services : "+response.getStatus().getStatus());
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		return response;		
	}
	
	
	/**
	 * Get end point URL for integration services for reference tables
	 * @param parameter
	 * @return
	 */
	public String resolveEndPoint(String parameter, boolean byId ){
		
		String endPoint = null;
		try{
			if(byId == true){
				endPoint = integrationServicesById(parameter);
			}else{
				endPoint = integrationServicesAllData(parameter);
			}
		}catch(Exception ex){
			log.error("Error in resolving integration service end point");
			ex.printStackTrace();
		}
		return endPoint;
	}

	/**
	 * Get the end point URL for integration (find by ID)services 
	 * @param parameter
	 * @return
	 */
	public String integrationServicesById(String parameter){

		String endPoint=null;
		String service = null;
		String baseURL= intergrationServiceConfig.getBaseURL().concat(intergrationServiceConfig.getService());

		if(parameter.equalsIgnoreCase(RequestParameters.channelType.toString())){
			service = intergrationServiceConfig.getChannelTypeByCodeService();	
		}else if(parameter.equalsIgnoreCase(RequestParameters.locationType.toString())){
			service = intergrationServiceConfig.getLocationTypeByCodeService();
		}else if(parameter.equalsIgnoreCase(RequestParameters.messageFormat.toString())){
			service = intergrationServiceConfig.getMessageFormatByCodeService();
		}else if(parameter.equalsIgnoreCase(RequestParameters.messageType.toString())){
			service = intergrationServiceConfig.getMessageTypeByCodeService();
		}else if(parameter.equalsIgnoreCase(RequestParameters.notificationType.toString())){
			service = intergrationServiceConfig.getNotificationTypeByCodeService();
		}else if(parameter.equalsIgnoreCase(RequestParameters.refTemplateId.toString())){
			service = intergrationServiceConfig.getRefTemplateIdByIdService();
		}else if(parameter.equalsIgnoreCase(RequestParameters.severityLevel.toString())){
			service = intergrationServiceConfig.getSeverityLevelByCodeService();
		}else if(parameter.equalsIgnoreCase(RequestParameters.targetUserGroups.toString())){
			service = intergrationServiceConfig.getTargetUserGroupsByCodeService();
		}else if(parameter.equalsIgnoreCase(RequestParameters.certainityLevel.toString())){
			service = intergrationServiceConfig.getCertainityLevelByCodeService();
		}else{
			log.error("Unable to resolve integration service endpoint");
		}
		endPoint = baseURL + service ;
		return endPoint;

	}
	
	/**
	 * Get the end point URL for integration (find All)services 
	 * @param parameter
	 * @return
	 */
	public String integrationServicesAllData(String parameter){

		String endPoint=null;
		String service = null;
		String baseURL= intergrationServiceConfig.getBaseURL().concat(intergrationServiceConfig.getService());

		if(parameter.equalsIgnoreCase(RequestParameters.channelType.toString())){
			service = intergrationServiceConfig.getChannelTypeService();	
		}else if(parameter.equalsIgnoreCase(RequestParameters.locationType.toString())){
			service = intergrationServiceConfig.getLocationTypeService();
		}else if(parameter.equalsIgnoreCase(RequestParameters.messageFormat.toString())){
			service = intergrationServiceConfig.getMessageFormatService();
		}else if(parameter.equalsIgnoreCase(RequestParameters.messageType.toString())){
			service = intergrationServiceConfig.getMessageTypeService();
		}else if(parameter.equalsIgnoreCase(RequestParameters.notificationType.toString())){
			service = intergrationServiceConfig.getNotificationTypeService();
		}else if(parameter.equalsIgnoreCase(RequestParameters.refTemplateId.toString())){
			service = intergrationServiceConfig.getRefTemplateIdService();
		}else if(parameter.equalsIgnoreCase(RequestParameters.severityLevel.toString())){
			service = intergrationServiceConfig.getSeverityLevelService();
		}else if(parameter.equalsIgnoreCase(RequestParameters.targetUserGroups.toString())){
			service = intergrationServiceConfig.getTargetUserGroupsService();
		}else if(parameter.equalsIgnoreCase(RequestParameters.certainityLevel.toString())){
			service = intergrationServiceConfig.getCertainityLevelService();
		}else{
			log.error("Unable to resolve integration service endpoint");
		}
		endPoint = baseURL + service ;
		return endPoint;

	}
	
	/**
	 * Get end point URL for integration service to get recipients 
	 * @param userTypeFlag
	 * @return
	 */
	public String integrationServicesForUsers(boolean userTypeFlag){
		String endPoint=null;
		String service=null;
		String baseURL= intergrationServiceConfig.getBaseURL().concat(intergrationServiceConfig.getService());
		if(userTypeFlag){
			service = intergrationServiceConfig.getUserDetailsByPoiGroupType();
		}else{
			service = intergrationServiceConfig.getUserDetailsAll(); 
		}
		endPoint = baseURL + service ;
		return endPoint;
	}

	@Override
	public ImageResource getImage(ImageRequestResource req) {

		ImageResource image = null;
		try {
			
			RestTemplate rest = new RestTemplate();
			String baseURL = intergrationServiceConfig.getBaseURL().concat(intergrationServiceConfig.getService());
			String endPoint = baseURL + intergrationServiceConfig.getDisseminationImageByDisseMessage();

			Message message = disseminationCommand.findByIocMessageId(req.getMessageId());

			req.setTransactionFrom("Dissemination");
			req.setMethodType("1");
			req.setNotificationTypeCode(message.getNotificationTypeId());
			req.setSeverityLevelCode(message.getSeverityLevelId());
			req.setImpactedArea(message.getImpactedArea());
			List<Poi> poiList = new ArrayList<Poi>();
			List<String> dbPoiList = message.getPoi();
			if (!dbPoiList.isEmpty()) {
				for (String str : dbPoiList) {
					Poi poi = new Poi();
					poi.setPoiId(str);
					poiList.add(poi);
				}
			}
			req.setPoiList(poiList);
			req.setMessageDate(message.getCreatedDate());
			req.setFloodTargetDate(message.getTargetDate());
			SSLCertHandler.disableSslVerification();
			ObjectMapper mapper = new ObjectMapper();
			log.debug("Image request ============= " + mapper.writeValueAsString(req));
			image = rest.postForObject(endPoint, req, ImageResource.class);

		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			throw new RuntimeException(ex.getMessage());
		}
		return image;
	}

	@Override
	public BaseResponseStatusResource applicationAlert(ApplicationAlertResource reqst) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String currentDate=sdf.format(new Date());
		ApplicationAlertRequestResource req=new ApplicationAlertRequestResource();
		BaseResponseStatusResource reponse = null;
		RestTemplate rest = new RestTemplate();
		String baseURL = intergrationServiceConfig.getBaseURL().concat(intergrationServiceConfig.getService());
		String endPoint = baseURL + intergrationServiceConfig.getApplicationAlertInsertUpdateById();
		SSLCertHandler.disableSslVerification();
		req.setTransactionFrom("Dissemination");
		req.setMethodType("2");
		reqst.setAlertCategory("Dessimination Message");
		reqst.setAlertDate(currentDate);
		reqst.setAlertTitle("Dessimination Message Status");
		reqst.setApplicationCode("DS");
		reqst.setRedirectLink(adminConfig.getUrl());
		reqst.setUpdatedBy("misos");
		reqst.setUpdatedDate(currentDate);
		log.debug("alert request " + reqst );
		req.setApplicationAlert(reqst);
		reponse = rest.postForObject(endPoint, req, BaseResponseStatusResource.class);
		return reponse;
	}

	@Override
	public SocialAccountResponseResource getsocialAccount(SocialAccountRequestResource req) {
		SocialAccountResponseResource response = null;
		try {
			RestTemplate rest = new RestTemplate();
			String baseURL = intergrationServiceConfig.getBaseURL().concat(intergrationServiceConfig.getService());
			String endPoint = baseURL + intergrationServiceConfig.getSocialAccountByType();
			SSLCertHandler.disableSslVerification();
			req.setTransactionFrom("Dissemination");
			req.setMethodType("1");
			req.setType(req.getType());
			req.setDescription(req.getDescription());
			log.debug("Social account ====== " + req);
			response = rest.postForObject(endPoint, req, SocialAccountResponseResource.class);
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			throw new RuntimeException(ex.getMessage());
		}
		return response;
	}
	


}
