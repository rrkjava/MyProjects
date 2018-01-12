package my.mimos.misos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author beaula.fernandez
 *
 */

@Component
@Getter
@Setter
public class IntegrationServiceConfig {
	
	  	@Value("${IntegrationServiceConfig.baseURL}")
		private String baseURL;
	  	
	  	@Value("${IntegrationServiceConfig.service}")
	    private String service;
	    
	  	@Value("${IntegrationServiceConfig.messageTypeService}")
	    private String messageTypeService;
	  	
	  	@Value("${IntegrationServiceConfig.refTemplateIdService}")
	    private String refTemplateIdService;
	  	
		@Value("${IntegrationServiceConfig.messageFormatService}")
	    private String messageFormatService;	    

		@Value("${IntegrationServiceConfig.targetUserGroupsService}")
	    private String targetUserGroupsService;
	    
		@Value("${IntegrationServiceConfig.channelTypeService}")
	    private String channelTypeService;
		
		@Value("${IntegrationServiceConfig.severityLevelService}")
	    private String severityLevelService;
	    
		@Value("${IntegrationServiceConfig.certainityLevelService}")
	    private String certainityLevelService;
		
		@Value("${IntegrationServiceConfig.locationTypeService}")
	    private String locationTypeService;
		
		@Value("${IntegrationServiceConfig.notificationTypeService}")
	    private String notificationTypeService;
	    
	    
		@Value("${IntegrationServiceConfig.messageTypeByCodeService}")
	    private String messageTypeByCodeService;
	  	
	  	@Value("${IntegrationServiceConfig.refTemplateIdByIdService}")
	    private String refTemplateIdByIdService;
	  	
		@Value("${IntegrationServiceConfig.messageFormatByCodeService}")
	    private String messageFormatByCodeService;	    

		@Value("${IntegrationServiceConfig.targetUserGroupsByCodeService}")
	    private String targetUserGroupsByCodeService;
	    
		@Value("${IntegrationServiceConfig.channelTypeByCodeService}")
	    private String channelTypeByCodeService;
		
		@Value("${IntegrationServiceConfig.severityLevelByCodeService}")
	    private String severityLevelByCodeService;
	    
		@Value("${IntegrationServiceConfig.certainityLevelByCodeService}")
	    private String certainityLevelByCodeService;
		
		@Value("${IntegrationServiceConfig.locationTypeByCodeService}")
	    private String locationTypeByCodeService;
		
		@Value("${IntegrationServiceConfig.notificationTypeByCodeService}")
	    private String notificationTypeByCodeService;
		
		@Value("${IntegrationServiceConfig.userDetailsByPoiGroupType}")
	    private String userDetailsByPoiGroupType;
		
		@Value("${IntegrationServiceConfig.userDetailsAll}")
	    private String userDetailsAll;
		
		@Value("${IntegrationServiceConfig.channelRecipients}")
		private String channelRecipients;
		
		@Value("${IntegrationServiceConfig.disseminationImageByDisseMessage}")
		private String disseminationImageByDisseMessage;
		
		@Value("${IntegrationServiceConfig.applicationAlertInsertUpdateById}")
		private String applicationAlertInsertUpdateById;
		
		@Value("${IntegrationServiceConfig.socialAccountByType}")
		private String socialAccountByType;
}
