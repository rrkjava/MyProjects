package my.mimos.misos.service;

import my.mimos.misos.domain.integrationservice.ApplicationAlertResource;
import my.mimos.misos.domain.integrationservice.BaseResponseStatusResource;
import my.mimos.misos.domain.integrationservice.CertainityLevelResponseResource;
import my.mimos.misos.domain.integrationservice.ChannelRecipientRequestResource;
import my.mimos.misos.domain.integrationservice.ChannelRecipientResponseResource;
import my.mimos.misos.domain.integrationservice.ChannelTypeResponseResource;
import my.mimos.misos.domain.integrationservice.ChannelTypesResponseResource;
import my.mimos.misos.domain.integrationservice.ImageRequestResource;
import my.mimos.misos.domain.integrationservice.ImageResource;
import my.mimos.misos.domain.integrationservice.LocationTypeResponseResource;
import my.mimos.misos.domain.integrationservice.MessageFormatResponseResource;
import my.mimos.misos.domain.integrationservice.MessageTemplateResponseResource;
import my.mimos.misos.domain.integrationservice.MessageTypeResponseResource;
import my.mimos.misos.domain.integrationservice.NotificationTypeResponseResource;
import my.mimos.misos.domain.integrationservice.NotificationTypesResponseResource;
import my.mimos.misos.domain.integrationservice.RecipientRequestResource;
import my.mimos.misos.domain.integrationservice.RecipientResponseResource;
import my.mimos.misos.domain.integrationservice.SeverityLevelResponseResource;
import my.mimos.misos.domain.integrationservice.SeverityLevelsResponseResource;
import my.mimos.misos.domain.integrationservice.SocialAccountRequestResource;
import my.mimos.misos.domain.integrationservice.SocialAccountResponseResource;
import my.mimos.misos.domain.integrationservice.UserGroupResponseResource;
/**
 * 
 * @author beaula.fernandez
 *
 */

public interface IntegrationService {

	public ChannelTypeResponseResource getChannelTypeByCode(String channelTypeCode,String endPoint);
	public UserGroupResponseResource getTargetUserGroupsByCode(String targetUserGroupCode,String endPoint);
	public MessageFormatResponseResource getMessageFormatByCode(String messageFormatCode,String endPoint);
	public MessageTypeResponseResource getMessageTypeByCode(String messageTypeCode,String endPoint);
	public NotificationTypeResponseResource getNotificationTypeByCode(String notificationTypeCode,String endPoint);
	public LocationTypeResponseResource getLocationTypeByCode(String locationTypeCode,String endPoint);
	public CertainityLevelResponseResource getCertainityLevelByCode(String certainityLevelCode,String endPoint);
	public SeverityLevelResponseResource getSeverityLevelByCode(String severityLevelCode,String endPoint);
	public MessageTemplateResponseResource getMessageTemplateById(String messageTemplateId,String endPoint);
	
	public ChannelTypesResponseResource getChannelTypes(String endPoint);
	public NotificationTypesResponseResource getNotificationTypes(String endPoint);
	public SeverityLevelsResponseResource getSeverityTypes(String endPoint);
	
	public ChannelRecipientResponseResource getUsersByGroupAndPOI(ChannelRecipientRequestResource request);
	public ChannelRecipientResponseResource getUsersAll(ChannelRecipientRequestResource request);
	public RecipientResponseResource getRecipients(RecipientRequestResource request);
	
	public String resolveEndPoint(String parameter, boolean byId );
	public ImageResource getImage(ImageRequestResource req);
	
	public BaseResponseStatusResource applicationAlert(ApplicationAlertResource req);
	
	public SocialAccountResponseResource getsocialAccount(SocialAccountRequestResource req);
	
}
