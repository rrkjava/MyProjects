/**
 * 
 */
package my.mimos.misos.mapper;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ma.glasnost.orika.MapperFactory;
import my.mimos.misos.domain.channel.EmailChannelRequestResource;
import my.mimos.misos.domain.channel.FaxChannelRequestResource;
import my.mimos.misos.domain.channel.FbChannelRequestResource;
import my.mimos.misos.domain.channel.MobileChannelRequestResource;
import my.mimos.misos.domain.channel.MobileGatewayRequestResource;
import my.mimos.misos.domain.channel.PublicPortalChannelRequestResource;
import my.mimos.misos.domain.channel.SirenChannelRequestResource;
import my.mimos.misos.domain.channel.SmsChannelRequestResource;
import my.mimos.misos.domain.channel.TwitterChannelRequestResource;
import my.mimos.misos.domain.entity.trx.ChannelMessage;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Component
public class ChannelMapper extends MapperBase {
	//@Getter
	//private MapperFacade mapperFacade;

	@Override
	protected void configure(MapperFactory factory) {

		// Object mapping between ChannelMessage and FbChannelRequestResource
		factory.classMap(FbChannelRequestResource.class, ChannelMessage.class)
				//.field("messageId", "message.id")
				.field("iocMessageId", "message.iocMesageId")
				.field("fbMessage", "messageContent")
				//.field("channelType", "targetChannelType.id")
				.field("channelType", "targetChannelTypeId") /**change due to removing join in entity*/
				.field("messageFormat", "messageFormatId")
				.field("targetUserGroups", "subscriberTypeId")
				.register();

		// Object mapping between ChannelMessage and TwitterChannelRequestResource
		factory.classMap(TwitterChannelRequestResource.class, ChannelMessage.class)
				//.field("messageId", "message.id")
				.field("iocMessageId", "message.iocMesageId")
				.field("twitterMessage", "messageContent")
				//.field("channelType", "targetChannelType.id")
				.field("channelType", "targetChannelTypeId")
				.field("messageFormat", "messageFormatId")
				.field("targetUserGroups", "subscriberTypeId")
				.register();

		// Object mapping between ChannelMessage and MobileChannelRequestResource
		factory.classMap(MobileChannelRequestResource.class, ChannelMessage.class)
				//.field("messageId", "message.id")
				.field("iocMessageId", "message.iocMesageId")
				.field("notificationMessage", "messageContent")
				.field("wktStringData", "message.impactedArea")
				//.field("channelType", "targetChannelType.id")
				.field("channelType", "targetChannelTypeId")
				//.field("targetUserGroups", "subscriberType.id")
				.field("targetUserGroups", "subscriberTypeId")
				//.field("messageType", "message.messageType.messageType")
				.field("messageType", "message.messageTypeId")
				.field("messageFormat", "messageFormatId")
				.field("poi", "message.poi")
				.register();
		
		// Object mapping between ChannelMessage and EmailChannelRequestResource
		factory.classMap(EmailChannelRequestResource.class, ChannelMessage.class)
				.field("iocMessageId", "message.iocMesageId")
				.field("channelType", "targetChannelTypeId")
				.field("targetUserGroups", "subscriberTypeId")
				.field("emailMessage", "messageContent")
				.field("messageFormat", "messageFormatId")
				.field("poi", "message.poi")
				.register();
				
		// Object mapping between ChannelMessage and MobileChannelRequestResource
		factory.classMap(SmsChannelRequestResource.class, ChannelMessage.class)
				.field("iocMessageId", "message.iocMesageId")
				.field("channelType", "targetChannelTypeId")
				.field("targetUserGroups", "subscriberTypeId")
				.field("smsMessage", "messageContent")
				.field("messageFormat", "messageFormatId")
				.field("poi", "message.poi")
				.register();
		
		factory.classMap(MobileChannelRequestResource.class, MobileGatewayRequestResource.class)
				.field("notificationMessage", "message")
				.field("wktStringData", "impactedArea")
				.field("messageType", "messageType")
				.field("channelType", "channelType")
				.field("iocMessageId", "iocMessageId")
				.field("targetUserGroups", "targetUserGroups")
				.register();
		
		// Object mapping between ChannelMessage and PublicPortalChannelRequestResource
		factory.classMap(PublicPortalChannelRequestResource.class, ChannelMessage.class)
				.field("certaintyLevelCode", "message.certaintyLevelId")
				.field("estimatedFloodDate", "message.estimatedStartDate")
				.field("impactedAreaCode", "message.impactedArea")
				.field("locationTypeCode", "message.locationTypeId")
				.field("message", "messageContent")
				.field("messageDate", "message.createdDate")
				.field("messageFormatCode", "messageFormatId")
				.field("messageId", "message.iocMesageId")
				.field("messageTypeCode", "message.messageTypeId")
				.field("notificationTypeCode", "message.notificationTypeId")
				.field("severityLevelCode", "message.severityLevelId")
				.field("targetChannelTypeCode", "targetChannelTypeId")
				.field("targetUserGroups", "subscriberTypeId")
				.field("poi", "message.poi")
				.register();

		// Object mapping between ChannelMessage and FaxChannelRequestResource
		factory.classMap(FaxChannelRequestResource.class, ChannelMessage.class)
		//.field("messageId", "message.id")
		.field("iocMessageId", "message.iocMesageId")
		.field("faxMessage", "messageContent")
		.field("channelType", "targetChannelTypeId")
		.field("targetUserGroups", "subscriberTypeId")
		.field("faxEmailAddress", "faxEmailAddress")
		.field("messageFormat", "messageFormatId")
		.register();
		
		
		// Object mapping between ChannelMessage and SirenChannelRequestResource
		factory.classMap(SirenChannelRequestResource.class, ChannelMessage.class)
				//.field("messageId", "message.id")
				.field("iocMessageId", "message.iocMesageId")
				.field("channelType", "targetChannelTypeId")
				.field("sirenMessage", "messageContent")
				.field("sirenMobileNo", "sirenMobileNo")
				.field("targetUserGroups", "subscriberTypeId")
				.field("messageFormat", "messageFormatId")
				.register();
				

		mapperFacade = factory.getMapperFacade();
	}
}
