/**
 * 
 */
package my.mimos.mdc.domain.mapper;


import org.springframework.stereotype.Component;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import my.mimos.mdc.domain.entity.Attachment;
import my.mimos.mdc.domain.entity.BroadCast;
import my.mimos.mdc.domain.entity.BroadCastRecipient;
import my.mimos.mdc.domain.entity.BroadCastResponse;
import my.mimos.mdc.resources.broadcast.BroadCastInboxResource;
import my.mimos.mdc.resources.broadcast.BroadCastResource;
import my.mimos.mdc.resources.broadcast.BroadCastResponseResource;
import my.mimos.mdc.resources.query.AttachmentResource;

/**
 * @author krishna.redabotu
 *
 */

@Component
public class BroadCastMapper extends ConfigurableMapper {

	MapperFacade mapperFacade;

	protected void configure(MapperFactory factory) {
		
		factory.classMap(BroadCastResource.class, BroadCast.class)		
	    .field("messageId", "messageId")
	    .field("subject", "subject")
	    .field("message", "message")
	    .field("broadcastDate", "broadcastDate")
	    .field("attachment{uploadId}", "attachment{uploadId}")
	    .field("attachment{uploadTitle}", "attachment{uploadTitle}")
	    .field("attachment{uploadDate}", "attachment{uploadDate}")
	    .field("attachment{uploadBy.username}", "attachment{uploadBy.username}")
	    .field("attachment{uploadBy.department}", "attachment{uploadBy.department.deptName}")
	    .field("attachment{uploadBy.firstName}", "attachment{uploadBy.firstName}")
	    .field("attachment{uploadBy.userId}", "attachment{uploadBy.userId}")
	    .field("broadcastBy.firstName", "broadcastBy.firstName")
	    .field("broadcastBy.department", "broadcastBy.department.deptName")
	    .field("broadcastBy.username", "broadcastBy.username")
	    .field("broadcastBy.userId", "broadcastBy.userId")
	    .register(); 
		
		factory.classMap(BroadCastInboxResource.class, BroadCastRecipient.class)
	    .field("messageId", "broadCast.messageId")
	    .field("subject", "broadCast.subject")
	    .field("broadcastDate", "broadCast.broadcastDate")
	    .field("receivedDate", "receivedDate")
	    .field("readStatus", "readStatus")
	    .field("readDate", "readDate")
	    .field("lastActivityDate", "lastActivityDate")
	    .field("broadcastBy.firstName", "broadCast.broadcastBy.firstName")
	    .field("broadcastBy.department", "broadCast.broadcastBy.department.deptName")
	    .field("broadcastBy.username", "broadCast.broadcastBy.username")
	    .field("broadcastBy.userId", "broadCast.broadcastBy.userId")
	    .byDefault()
	    .register(); 
		
		factory.classMap(BroadCastResponseResource.class, BroadCastResponse.class)		
	    .field("responseId", "responseId")
	    .field("responseMessage", "responseMessage")
	    .field("broadCast", "broadCast.messageId")
	    .field("responseDate", "responseDate")
	    .field("attachment", "attachment")
	    .field("responseBy.firstName", "responseBy.firstName")
	    .field("responseBy.department", "responseBy.department.deptName")
	    .field("responseBy.username", "responseBy.username")
	    .field("responseBy.userId", "responseBy.userId")
	    .register(); 
		
		
		factory.classMap(AttachmentResource.class, Attachment.class)		
	    .field("uploadId", "uploadId")
	    .field("uploadTitle", "uploadTitle")
	    .field("content", "content")
	    .byDefault()
	    .register(); 
		
		mapperFacade = factory.getMapperFacade();
	}

	public MapperFacade getMapperFacade() {
		return mapperFacade;
	}
}
