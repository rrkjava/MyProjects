package my.mimos.mdc.domain.mapper;

import org.springframework.stereotype.Component;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import my.mimos.mdc.domain.entity.Acknowledgment;
import my.mimos.mdc.domain.entity.Attachment;
import my.mimos.mdc.domain.entity.Comment;
import my.mimos.mdc.domain.entity.Query;
import my.mimos.mdc.domain.entity.Recipient;
import my.mimos.mdc.domain.entity.Response;
import my.mimos.mdc.domain.entity.ResponseApproval;
import my.mimos.mdc.domain.entity.UrgencyLevel;
import my.mimos.mdc.domain.entity.User;
import my.mimos.mdc.resources.query.AckResource;
import my.mimos.mdc.resources.query.AttachmentResource;
import my.mimos.mdc.resources.query.CommentResource;
import my.mimos.mdc.resources.query.DisplayUserResource;
import my.mimos.mdc.resources.query.QueryResource;
import my.mimos.mdc.resources.query.QueryResponseResource;
import my.mimos.mdc.resources.query.RecipientResource;
import my.mimos.mdc.resources.query.ResponseApprovalResource;
import my.mimos.mdc.resources.query.SendQueryRequestResource;
import my.mimos.mdc.resources.query.UpdateQueryRequestResource;
import my.mimos.mdc.resources.query.UploadMetadataResource;
import my.mimos.mdc.resources.query.UrgencyResource;

@Component
public class QueryMapper extends ConfigurableMapper {	
	
	MapperFacade mapperFacade;
	
	protected void configure(MapperFactory factory) {
		factory.classMap(QueryResource.class, Query.class)		
		    .field("queryId", "queryId")
		    .field("subject", "subject")
		    .field("description", "description")
		    .field("approvalStatus", "queryStatus")	
		    .field("approvalComment", "approvalComment")
		    .field("approvalDate", "approvedDate")
		    .field("approvedBy", "approvedBy")
		    .field("createdBy", "sentBy")	
		    .field("createdDate", "createdDate")
		    .field("attachment", "attachment")		
		    .field("urgencyLevel", "urgencyLevel.urgencyLevel")
		    .field("readReciept", "readReciept")
		    .field("forwardFlag", "forwardFlag")
		    .byDefault()
		    .register(); 
		
			factory.classMap(UpdateQueryRequestResource.class, Query.class)		
		    .field("queryId", "queryId")
		    .field("subject", "subject")
		    .field("description", "description")	
		    .byDefault()
		    .register();
		
		factory.classMap(RecipientResource.class, Recipient.class)	
			.field("id","recepientId")
		    .field("query","query.queryId")
		    .field("recipientUserId","recipient.userId")
		    .field("groupId","group.groupId")
		    .field("deptId","dept.deptId")
		    .field("recipientType","recipientType")
		    .field("assignedDate","assignedDate")
		    .byDefault()
		    .register();
		
		factory.classMap(QueryResponseResource.class, Response.class)	
			.field("responseId","responseId")
			.field("description", "description")
			.field("attachment", "attachment")	
			.field("createdDate", "createdDate")
			.field("createdBy", "responseBy")
		    .field("query","query.queryId")		    
		    .field("responseStatus", "responseStatus")
		    .field("approvalComment", "approvalComment")
		    .field("approvalDate", "approvalDate")
		    .field("approvedBy", "approvedBy")
		    .field("readReciept", "readReciept")
		    .field("directReply", "directReplyFlag")
		    .field("finalReplyFlag", "finalReplyFlag")
		    .field("sendFinalReplyFlag", "sendFinalReplyFlag")
		    .byDefault()
		    .register();
		
		factory.classMap(CommentResource.class, Comment.class)	
			.field("commentId", "commentId")
		    .field("commentDesc", "commentDesc")
		    .field("createdDate", "createdDate")
		    .field("commentBy", "commentBy.userId")
		    .field("createdBy", "commentBy")
		    .field("query", "query.queryId")
		    .field("readReciept", "readReciept")
		    .byDefault()
		    .register(); 
		
		factory.classMap(AckResource.class, Acknowledgment.class)	
			.field("ackId", "ackId")		    
		    .field("ackStatus", "ackStatus")
		    .field("ackComment", "ackComment")
		    .field("createdDate", "createdDate")
		    .field("createdBy", "ackBy")	
		    .field("readReciept", "readReciept")
		    .byDefault()
		    .register();
		
		factory.classMap(SendQueryRequestResource.class, Query.class)		
		    .field("subject", "subject")
		    .field("description", "description")
		    .byDefault()
		    .register(); 	
		
		factory.classMap(DisplayUserResource.class, User.class)		
		    .field("firstName", "firstName")
		    .field("username", "username")	    
		    .field("userId", "userId")	
		    .field("department","department.deptName")
		    .byDefault()
		    .register(); 
		
		factory.classMap(UrgencyResource.class, UrgencyLevel.class)		
		    .field("urgencyId", "urgencyLevelId")
		    .field("urgencyLevel", "urgencyLevel")
		    .byDefault()
		    .register(); 
		
		factory.classMap(AttachmentResource.class, Attachment.class)		
		    .field("uploadId", "uploadId")
		    .field("uploadTitle", "uploadTitle")
		    .field("content", "content")
		    .byDefault()
		    .register(); 
		
		factory.classMap(UploadMetadataResource.class, Attachment.class)		
		    .field("uploadId", "uploadId")
		    .field("uploadTitle", "uploadTitle")
		    .field("uploadDate", "uploadDate")
		    .field("uploadBy", "uploadBy")
		    .byDefault()
		    .register();
		
		factory.classMap(ResponseApprovalResource.class, ResponseApproval.class)	
			.field("approvalId", "approvalId")		    
		    .field("approvalStatus", "approvalStatus")
		    .field("approvalComment", "approvalComment")
		    .field("createdDate", "createdDate")
		    .field("createdBy", "approvalBy")	
		    .field("readReciept", "readReciept")
		    .byDefault()
		    .register();
		
		mapperFacade = factory.getMapperFacade();
	}
	
	
	
	public MapperFacade getMapperFacade(){
		return mapperFacade;
	}

}
