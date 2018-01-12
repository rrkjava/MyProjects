/**
 * 
 */
package my.mimos.misos.mapper;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ma.glasnost.orika.MapperFactory;
import my.mimos.misos.domain.entity.trx.ChannelMessage;
import my.mimos.misos.domain.entity.trx.Message;
import my.mimos.misos.domain.resource.ChannelRequestResource;
import my.mimos.misos.domain.resource.ChannelResponseResource;
import my.mimos.misos.domain.resource.DisseminationRequestResource;
import my.mimos.misos.domain.resource.DisseminationResponseResource;

/**
 * @author Shaiful Hisham Mat Jali
 *
 * To remove this mapper class. Used in channel-router
 */

@NoArgsConstructor(access=AccessLevel.PUBLIC)
@Component
public class DisseminationResourceMapper extends MapperBase {
	//@Getter
	//private MapperFacade mapperFacade;
	
	@Override
	protected void configure(MapperFactory factory) {
		// TODO Auto-generated method stub
		
		// Object mapping between ChannelResource and ChannelMessage
		factory.classMap(ChannelRequestResource.class, ChannelMessage.class)
			.field("channelType", "targetChannelTypeId")
			.field("targetUserGroups", "subscriberTypeId")
			.field("messageFormat", "messageFormatId")
			//.field("notificationType", "notificationType.notificationType")
			//.field("certaintyLevel", "certaintyLevel.certaintyLevel")
			//.field("severityLevel", "severityLevel.severityLevel")
			.field("refTemplateId", "messageTemplateId")
			.field("message", "messageContent")
			//.field("locationType", "locationType.locationType")
			//.field("locationData", "locationInfoContent")
			//.field("targetDate", "publishDate")
			.field("sirenMobileNo", "sirenMobileNo")
			.field("faxEmailAddress", "faxEmailAddress")
			
			.register();
				
		// Object mapping between DisseminationRequestResource and Message
		factory.classMap(DisseminationRequestResource.class, Message.class)
			.field("messageId", "iocMesageId")
			.field("messageDate", "createdDate")
			.field("userId", "createdBy")
			.field("channel", "channelMesages")
			.field("originator", "originator")
			.field("eventId", "eventId")
			.field("messageType", "messageTypeId")
			.field("notificationType", "notificationTypeId")
			.field("locationType", "locationTypeId")
			.field("severityLevel", "severityLevelId")
			.field("certainityLevel", "certaintyLevelId")
			.field("impactedArea", "impactedArea")
			.field("poi", "poi")
			.field("targetDate", "targetDate")
			.field("estimatedStartDate", "estimatedStartDate")
			.field("estimatedEndDate", "estimatedEndDate")
			.register();
		
		factory.classMap(ChannelRequestResource.class, ChannelResponseResource.class)
			.field("channelType", "channelType")
			.field("targetUserGroups", "targetUserGroups")
			.field("channelType", "channelType")
			.register();
			
		factory.classMap(DisseminationRequestResource.class, DisseminationResponseResource.class)
			.field("messageId", "messageId")
			.field("messageDate", "messageDate")
			.field("originator", "originator")
			.field("eventId", "eventId")
			.field("messageType", "messageType")
			.field("userId", "userId")
			.field("channel", "channels")
			.register();
		
		mapperFacade = factory.getMapperFacade();
	}	
}
