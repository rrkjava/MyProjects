package my.mimos.mdc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;
import my.mimos.mdc.command.QueryCommand;
import my.mimos.mdc.command.ResponseCommand;
import my.mimos.mdc.command.UserManagementCommand;
import my.mimos.mdc.domain.entity.Attachment;
import my.mimos.mdc.domain.entity.Comment;
import my.mimos.mdc.domain.entity.Query;
import my.mimos.mdc.domain.entity.Recipient;
import my.mimos.mdc.domain.entity.Response;
import my.mimos.mdc.domain.entity.ResponseApproval;
import my.mimos.mdc.domain.entity.Role;
import my.mimos.mdc.domain.entity.User;
import my.mimos.mdc.domain.mapper.QueryMapper;
import my.mimos.mdc.enums.StatusType;
import my.mimos.mdc.resources.BaseResponseResource;
import my.mimos.mdc.resources.query.CommentResource;
import my.mimos.mdc.resources.query.QueryCommentRequestResource;
import my.mimos.mdc.resources.query.QueryCommentResponseResource;
import my.mimos.mdc.resources.query.QueryResponseResource;
import my.mimos.mdc.resources.query.ResponseApprovalRequestResource;
import my.mimos.mdc.resources.query.ResponseApprovalResponseResource;
import my.mimos.mdc.resources.query.SearchAttachmentRequestResource;
import my.mimos.mdc.resources.query.SearchCommentRequestResource;
import my.mimos.mdc.resources.query.SearchCommentResponseResource;
import my.mimos.mdc.resources.query.SearchRecipientRequestResource;
import my.mimos.mdc.resources.query.SearchResponsesRequestResource;
import my.mimos.mdc.resources.query.SearchResponsesResource;
import my.mimos.mdc.resources.query.SendResponseRequestResource;
import my.mimos.mdc.resources.query.SendResponseResource;
import my.mimos.mdc.resources.query.UpdateResponseRequestResource;
import my.mimos.mdc.resources.user.SearchUsersRequestResource;
import my.mimos.mdc.service.AsyncService;
import my.mimos.mdc.service.QueryService;
import my.mimos.mdc.service.QueryTrackerService;
import my.mimos.mdc.service.ResponseService;
import my.mimos.mdc.service.UserManagementService;
import my.mimos.mdc.utils.MdcConstants;
/**
 * 
 * @author beaula.fernandez
 *
 */

@Log4j
@Component
public class ResponseServiceImpl implements ResponseService{
	
	@Autowired
	QueryCommand queryCommand;
	
	@Autowired
	ResponseCommand responseCommand;
	
	@Autowired
	QueryMapper queryMapper;
	
	@Autowired
	UserManagementService userService;
	
	@Autowired
	UserManagementCommand userCommand;
	
	@Autowired
	AsyncService asyncService;
	
	@Autowired
	QueryService queryService;
	
	@Autowired
	QueryTrackerService queryTrackerService;
	
	@Override
	public SendResponseResource sendResponse(SendResponseRequestResource request) {
		SendResponseResource response = new SendResponseResource();
		try{		
			Response responseToSend = new Response();
			responseToSend.setDescription(request.getDescription());
			responseToSend.setCreatedDate(new Date());
			responseToSend.setReadReciept(false);
			
			//LOGGED IN USER
			User loggedInUser = userService.getUserDetailsFromSecurityContext();			
			responseToSend.setResponseBy(loggedInUser);
			
			//USER ROLES FOR ADD RESPONSE
			boolean roleAdmin = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_ADMIN::equals);
			boolean roleKLN = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_KLN::equals);			
			boolean roleFocal = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL::equals);
			boolean roleFocalSup = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL_SUPERVISOR::equals);	
			boolean supervisorExists = false;
				
			Query existingQuery = queryCommand.findByQueryId(request.getQueryId());
			
			//SET RESPONSE STATUS AND UPDATE THE ACTIVITY DATES
			if(roleAdmin){	
				if(request.isDirectReply()){
					//RESPONSE STATUS
					responseToSend.setResponseStatus(MdcConstants.STATUS_APPROVE);
					responseToSend.setDirectReplyFlag(true);
					
					//UPDATE LAST ACTIVITY DATE AND READ STATUS (to mark unread for sender and ordering of inbox))
					existingQuery.setModifiedDate(new Date());
					existingQuery.setSenderReadStatus(false);
					existingQuery.setQueryStatus(MdcConstants.STATUS_APPROVE);
				}else{
					//RESPONSE STATUS
					responseToSend.setResponseStatus(MdcConstants.STATUS_PENDING_AT_KLN);
					responseToSend.setDirectReplyFlag(false);
				}
			}else if(roleKLN){
				//RESPONSE STATUS
				responseToSend.setResponseStatus(MdcConstants.STATUS_APPROVE);
				
				/*Fix : Adding the KLN reply as final reply which has to be forwarded to FDM*/
				responseToSend.setFinalReplyFlag(true);
				
				//UPDATE LAST ACTIVITY DATE AND READ STATUS (to mark unread for sender and ordering of inbox)
				existingQuery.setSenderReadStatus(false); 
				existingQuery.setModifiedDate(new Date());
				
			}else if(roleFocal){
				//FILTER SUPERVISOR IN THE FOCAL DEPT
				SearchUsersRequestResource searchCriteria = new SearchUsersRequestResource();
				searchCriteria.setDeptId(loggedInUser.getDepartment().getDeptId());
				searchCriteria.setUserRole(MdcConstants.ROLE_FOCAL_SUPERVISOR);
				List<User> supervisors = userCommand.searchUsers(searchCriteria);
				
				//RESPONSE STATUS
				if(null != supervisors && !supervisors.isEmpty()){					
					responseToSend.setResponseStatus(MdcConstants.STATUS_PENDING_AT_SUPERVISOR);
					supervisorExists = true;
				}else{
					responseToSend.setResponseStatus(MdcConstants.STATUS_PENDING_AT_ADMIN);
				}
			}else if(roleFocalSup){
				//RESPONSE STATUS
				responseToSend.setResponseStatus(MdcConstants.STATUS_PENDING_AT_ADMIN);
			}
			
			// SAVE QUERY
			responseToSend.setQuery(existingQuery);
			Response queryResponse = responseCommand.saveResponse(responseToSend);
			QueryResponseResource responseResource = queryMapper.getMapperFacade().map(queryResponse, QueryResponseResource.class);
			response.setResponse(responseResource);		
			
			// ATTACHMENTS FOR RESPONSE
			if(null!=request.getUploadIds()&& !request.getUploadIds().isEmpty()){
				SearchAttachmentRequestResource searchCriteria = new SearchAttachmentRequestResource();
				searchCriteria.setUploadIds(request.getUploadIds());				
				List<Attachment> uploads = queryCommand.searchQueryAttachments(searchCriteria);
				if(null!= uploads && !uploads.isEmpty()){
					uploads.forEach(upload -> upload.setResponse(responseToSend));
					queryCommand.saveAttachment(uploads);
				}
			}
			
			//FILTER  RECIPIENTS FOR SENDING NOTIFICATION
			List<Long> notifyUser = new ArrayList<>();	
			List<String> notifyUserRoles = new ArrayList<>();
			SearchRecipientRequestResource searchCriteria = new SearchRecipientRequestResource();
			searchCriteria.setQueryId(existingQuery.getQueryId());			
			if(roleFocal || roleFocalSup){
				searchCriteria.setFocalDeptId(loggedInUser.getDepartment().getDeptId());
				searchCriteria.setFilterAdminAndfocalOfDept(true);
			}else if(roleAdmin){
				notifyUserRoles.add(MdcConstants.ROLE_ADMIN);
				notifyUserRoles.add(MdcConstants.ROLE_KLN);
				if(request.isDirectReply()){
					notifyUser.add(existingQuery.getSentBy().getUserId()); //EMBASSY
				}
			}else if(roleKLN){
				
				 /* fix : Removing FDM from being notified when KLN sends a reply*/
				//notifyUser.add(existingQuery.getSentBy().getUserId()); //EMBASSY
				
				notifyUserRoles.add(MdcConstants.ROLE_KLN);				
				notifyUserRoles.add(MdcConstants.ROLE_ADMIN);
			}	
			searchCriteria.setRoles(notifyUserRoles);			
			List<Recipient> existingRecipientList = queryCommand.searchQueryRecipient(searchCriteria);
			existingRecipientList.forEach(recipient -> notifyUser.add(recipient.getRecipient().getUserId()));
			
			//UPDATE LAST ACTIVITY DATE AND READ STATUS FOR RECIPIENTS (to mark unread for recipients since new reply added, order in inbox on top)
			for(Recipient recipient : existingRecipientList){
				recipient.setLastActivityDate(new Date());
				if(recipient.getRecipient().getUserId() != loggedInUser.getUserId()){
					recipient.setReadStatus("false");
				}					
			}	
			queryCommand.addRecipients(existingRecipientList);
			notifyUser.remove(loggedInUser.getUserId());
			
			//PUSH NOTIFICATION
			asyncService.pushNotificationToDevices(existingQuery.getQueryId(),notifyUser,MdcConstants.NOTIFY_REPLY,MdcConstants.NOTIFY_TYPE_QUERY);
			asyncService.sendEmailNotification(existingQuery.getQueryId(),notifyUser,MdcConstants.NOTIFY_REPLY);
			
			//UPDATE TRACKER
			queryTrackerService.sendResponseTracker(existingQuery, queryResponse, loggedInUser, request.isDirectReply(), supervisorExists);
			
			response.setStatus("Response sent.");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}

	@Override
	public SendResponseResource getResponse(String responseId) {
		SendResponseResource response = new SendResponseResource();
		try{
			Response queryResponse = responseCommand.findByResponseId(Long.valueOf(responseId));
			QueryResponseResource responseResource = queryMapper.getMapperFacade().map(queryResponse, QueryResponseResource.class);
			response.setResponse(responseResource);
			
			response.setStatus("Response retrieved.");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus("Unable to retrieve response to the query.");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}

	@Override
	public BaseResponseResource removeResponseFromQuery(String responseId) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			responseCommand.removeResponse(Long.valueOf(responseId));
			
			response.setStatus("Response retrieved.");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus("Unable to retrieve response to the query.");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}
	
	@Override
	public SearchResponsesResource searchResponses(SearchResponsesRequestResource searchCriteria) {
		SearchResponsesResource response = new SearchResponsesResource();
		try{
			List<Response> responseList = responseCommand.searchQueryResponse(searchCriteria);
			List<QueryResponseResource> responseResource = queryMapper.getMapperFacade().mapAsList(responseList, QueryResponseResource.class);
			response.setResponses(responseResource);
			
			response.setStatus("Records retrieved successfully");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}

	@Override
	public QueryCommentResponseResource addCommentToQuery(QueryCommentRequestResource request) {
		QueryCommentResponseResource response = new QueryCommentResponseResource();
		try{			
			Comment comment = new Comment();
			comment.setCommentDesc(request.getCommentDesc());
			comment.setCreatedDate(new Date());
			
			User loggedInUser =	userService.getUserDetailsFromSecurityContext();
			User commentedByUser = new User();
			commentedByUser.setUserId(loggedInUser.getUserId());
			comment.setCommentBy(commentedByUser);
			
			Query existingQuery = queryCommand.findByQueryId(request.getQueryId());
			//existingQuery.setModifiedDate(new Date());
			comment.setQuery(existingQuery);
			
			Comment savedComment = responseCommand.addComment(comment);
			CommentResource commentResource = queryMapper.getMapperFacade().map(savedComment, CommentResource.class);
			
			response.setComment(commentResource);
			response.setStatus("Comment added to Query");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus("Unable to add comment to the query.");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}
	
	@Override
	public QueryCommentResponseResource getComment(String commentId) {
		QueryCommentResponseResource response = new QueryCommentResponseResource();
		try{			
			Comment comment = responseCommand.getComment(commentId);
			CommentResource commentResource = queryMapper.getMapperFacade().map(comment, CommentResource.class);
			
			response.setComment(commentResource);
			response.setStatus("comment retrieved successfully");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}	

	@Override
	public SearchCommentResponseResource searchComments(SearchCommentRequestResource searchCriteria) {
		SearchCommentResponseResource response = new SearchCommentResponseResource();
		try{
			List<Comment> responseList = responseCommand.searchQueryComments(searchCriteria);
			List<CommentResource> commentResource = queryMapper.getMapperFacade().mapAsList(responseList, CommentResource.class);
			response.setComments(commentResource);
			
			response.setStatus("Records retrieved successfully");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}

	@Override
	public BaseResponseResource removeComment(String commentId) {
		BaseResponseResource response = new BaseResponseResource();
		try{			
			responseCommand.removeComment(Long.valueOf(commentId));
			
			response.setStatus("Comment deleted successfully");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);	
			
		}catch(NumberFormatException ex){
			throw new RuntimeException("Invalid recipient id");
		}
		catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}

	@Override
	public BaseResponseResource updateResponse(UpdateResponseRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		try{	
			Response queryResponse = responseCommand.findByResponseId(request.getResponseId());
			if(null == queryResponse){
				throw new RuntimeException("Query response doesnot exist");
			}
			User loggedInUser = userService.getUserDetailsFromSecurityContext();
			if(null == loggedInUser){
				throw new RuntimeException("user logged in cannot be found");
			}
			if(queryResponse.getResponseBy().getUserId() != loggedInUser.getUserId() ){
				throw new RuntimeException("The response was not sent by the user logged in.");
			}
			if(StringUtils.isNotBlank(request.getDescription())){
					queryResponse.setDescription(request.getDescription());
					queryResponse.setModifiedDate(new Date());
					responseCommand.saveResponse(queryResponse);
			}
			
			// Link uploaded attachments to the response
			if(null!=request.getUploadIds()&& !request.getUploadIds().isEmpty()){
				SearchAttachmentRequestResource searchCriteria = new SearchAttachmentRequestResource();
				searchCriteria.setUploadIds(request.getUploadIds());				
				List<Attachment> uploads = queryCommand.searchQueryAttachments(searchCriteria);
				if(null!= uploads && !uploads.isEmpty()){
					uploads.forEach(upload -> upload.setResponse(queryResponse));
					queryCommand.saveAttachment(uploads);
				}
			}
			
			response.setStatus("Response updated successfully");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}
	
	
	@Override
	public ResponseApprovalResponseResource approveResponse(ResponseApprovalRequestResource request) {
		ResponseApprovalResponseResource response = new ResponseApprovalResponseResource();
		try{			
			User loggedInUser = userService.getUserDetailsFromSecurityContext();			
			boolean roleAdmin = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_ADMIN::equals);
			boolean roleKLN = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_KLN::equals);
			boolean roleFocalSup = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL_SUPERVISOR::equals);	
			
			Response existingResponse = responseCommand.findByResponseId(request.getResponseId());		
			String notificationMessage = null;
			ResponseApproval  approval = new ResponseApproval();
			
			//SET RESPONSE STATUS BASED ON ROLES
			if(roleKLN){
				if(request.isAccept()){
					existingResponse.setResponseStatus(MdcConstants.STATUS_APPROVE);
					existingResponse.setFinalReplyFlag(true);/*fix : setting the reply from admin approved by KLN as the final reply which has to go to FDM*/
					approval.setApprovalStatus(MdcConstants.STATUS_ACCEPT);
					notificationMessage = MdcConstants.NOTIFY_APPROVE_RESPONSE;
				}else{
					existingResponse.setResponseStatus(MdcConstants.STATUS_REJECT);
					approval.setApprovalStatus(MdcConstants.STATUS_REJECT);
					notificationMessage = MdcConstants.NOTIFY_REJECT_RESPONSE;
				}
			}else if(roleAdmin){
				if(request.isAccept()){
					existingResponse.setResponseStatus(MdcConstants.STATUS_APPROVE);
					approval.setApprovalStatus(MdcConstants.STATUS_ACCEPT);
					notificationMessage = MdcConstants.NOTIFY_APPROVE_RESPONSE;
				}else{
					existingResponse.setResponseStatus(MdcConstants.STATUS_REJECT);
					approval.setApprovalStatus(MdcConstants.STATUS_REJECT);
					notificationMessage = MdcConstants.NOTIFY_REJECT_RESPONSE;
				}
			}
			else if(roleFocalSup){
				if(request.isAccept()){
					existingResponse.setResponseStatus(MdcConstants.STATUS_PENDING_AT_ADMIN);
					approval.setApprovalStatus(MdcConstants.STATUS_ACCEPT);
					notificationMessage = MdcConstants.NOTIFY_APPROVE_RESPONSE;
				}else{
					existingResponse.setResponseStatus(MdcConstants.STATUS_REJECT);
					approval.setApprovalStatus(MdcConstants.STATUS_REJECT);
					notificationMessage = MdcConstants.NOTIFY_REJECT_RESPONSE;
				}
			}
			
			// SAVE APPROVAL
			approval.setApprovalComment(request.getReason());
			approval.setResponse(existingResponse);
			approval.setCreatedDate(new Date());
			approval.setApprovalBy(loggedInUser);
			
			ResponseApproval responseApproval = responseCommand.saveResponseApproval(approval);			
			log.info("Query acknowledgments added successfully!");
			
			//FILTER RECIPIENTS FOR SENDING NOTIFICATION
			List<Long> notifyUser = new ArrayList<>();
			notifyUser.add(existingResponse.getResponseBy().getUserId()); // notify sender of reply<focal point/admin>			
			
			/* fix - removing sending notification to embassy if KLN approves a reply from admin
			 * if(loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_KLN::equals) &&
					existingResponse.getResponseBy().getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_ADMIN::equals)){
				
				notifyUser.add(existingResponse.getQuery().getSentBy().getUserId()); //reply from admin - approved by KLN -> notify embassy.	
				
				// UPDATE QUERY ACTIVITY DATE AND READ STATUS(to mark as unread for sender and ordering of inbox)
				existingResponse.getQuery().setModifiedDate(new Date());
				existingResponse.getQuery().setSenderReadStatus(false);
				responseCommand.saveResponse(existingResponse);
			}*/
			
			//PUSH NOTIFICATION
			asyncService.pushNotificationToDevices(existingResponse.getQuery().getQueryId(),notifyUser,notificationMessage,MdcConstants.NOTIFY_TYPE_QUERY);
			asyncService.sendEmailNotification(existingResponse.getQuery().getQueryId(),notifyUser,notificationMessage);
			
			//UPDATE TRACKER
			queryTrackerService.approveResponseTracker(existingResponse, loggedInUser);
			
			response.setApprovalId(responseApproval.getApprovalId());
			response.setStatus("approval sent successfully");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}
	
	@Override
	public BaseResponseResource sendFinalResponse(Long responseId){
		BaseResponseResource response = new BaseResponseResource();
		try{
			Response queryResponse = responseCommand.findByResponseId(responseId);
			if(null == queryResponse){
				throw new RuntimeException("Query response doesnot exist");
			}
			User loggedInUser = userService.getUserDetailsFromSecurityContext();
			boolean roleAdmin = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_ADMIN::equals);
			if(roleAdmin){
				queryResponse.setSendFinalReplyFlag(true);
				queryResponse.setModifiedDate(new Date());
				responseCommand.saveResponse(queryResponse);
				
				//SEND NOTIFICATIONS TO RECIPIENTS AND SENDER-FDM & FOCAL
				List<Long> notifyUser = new ArrayList<>();	
				List<String> notifyUserRoles = new ArrayList<>();
				SearchRecipientRequestResource searchCriteria = new SearchRecipientRequestResource();
				
				notifyUserRoles.add(MdcConstants.ROLE_FOCAL);
				notifyUserRoles.add(MdcConstants.ROLE_FOCAL_SUPERVISOR);
				notifyUserRoles.add(MdcConstants.ROLE_EMBASSY);
				
				searchCriteria.setQueryId(queryResponse.getQuery().getQueryId());				
				searchCriteria.setRoles(notifyUserRoles);
				List<Recipient> existingRecipientList = queryCommand.searchQueryRecipient(searchCriteria);
				
				existingRecipientList.forEach(recipient -> notifyUser.add(recipient.getRecipient().getUserId())); //USER IDS OF FILTERED RECEPIENTS
				notifyUser.add(queryResponse.getQuery().getSentBy().getUserId()); //EMBASSY
				
				//UPDATE LAST ACTIVITY DATE AND READ STATUS FOR RECIPIENTS (to mark unread for recipients since new reply added, order in inbox on top)
				for(Recipient recipient : existingRecipientList){
					recipient.setLastActivityDate(new Date());
					if(recipient.getRecipient().getUserId() != loggedInUser.getUserId()){
						recipient.setReadStatus("false");
					}					
				}	
				queryCommand.addRecipients(existingRecipientList);				
				
				//PUSH NOTIFICATION
				asyncService.pushNotificationToDevices(queryResponse.getQuery().getQueryId(),notifyUser,MdcConstants.NOTIFY_REPLY,MdcConstants.NOTIFY_TYPE_QUERY);
				asyncService.sendEmailNotification(queryResponse.getQuery().getQueryId(),notifyUser,MdcConstants.NOTIFY_REPLY);
				
				
				//SEND NOTIFICATIONS TO RECIPIENTS AND ADMIN & KLN
				List<Long> notifyAdminAndKLN= new ArrayList<>();	
				notifyUserRoles = new ArrayList<>();
				searchCriteria = new SearchRecipientRequestResource();
				existingRecipientList.clear();
				
				notifyUserRoles.add(MdcConstants.ROLE_KLN);
				notifyUserRoles.add(MdcConstants.ROLE_ADMIN);
				
				searchCriteria.setQueryId(queryResponse.getQuery().getQueryId());				
				searchCriteria.setRoles(notifyUserRoles);
				existingRecipientList = queryCommand.searchQueryRecipient(searchCriteria);
				
				existingRecipientList.forEach(recipient -> notifyAdminAndKLN.add(recipient.getRecipient().getUserId())); //USER IDS OF FILTERED RECEPIENTS
				notifyAdminAndKLN.remove(loggedInUser.getUserId());
				
				//UPDATE LAST ACTIVITY DATE AND READ STATUS FOR RECIPIENTS (to mark unread for recipients since new reply added, order in inbox on top)
				for(Recipient recipient : existingRecipientList){
					recipient.setLastActivityDate(new Date());
					if(recipient.getRecipient().getUserId() != loggedInUser.getUserId()){
						recipient.setReadStatus("false");
					}					
				}	
				queryCommand.addRecipients(existingRecipientList);				
				
				//PUSH NOTIFICATION
				asyncService.pushNotificationToDevices(queryResponse.getQuery().getQueryId(),notifyAdminAndKLN,MdcConstants.NOTIFY_FINAL_REPLY,MdcConstants.NOTIFY_TYPE_QUERY);
				asyncService.sendEmailNotification(queryResponse.getQuery().getQueryId(),notifyAdminAndKLN,MdcConstants.NOTIFY_FINAL_REPLY);				
				
				//UPDATE TRACKER
				//queryTrackerService.sendResponseTracker(queryResponse.getQuery(), queryResponse, loggedInUser, false, false);
				
				response.setStatus("final reply sent successfully");
				response.setStatusCode("S0001");
				response.setStatusType(StatusType.SUCCESS);	
				
			}else{
				throw new RuntimeException("User must be an admin to send final reply");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;		
	}

}
