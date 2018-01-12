package my.mimos.mdc.service.impl;
/**
 * 
 * @author beaula.fernandez
 *
 */


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import my.mimos.mdc.command.BroadCastCommand;
import my.mimos.mdc.command.DeviceCommand;
import my.mimos.mdc.command.QueryCommand;
import my.mimos.mdc.command.ResponseCommand;
import my.mimos.mdc.command.UserManagementCommand;
import my.mimos.mdc.domain.entity.Acknowledgment;
import my.mimos.mdc.domain.entity.Attachment;
import my.mimos.mdc.domain.entity.BroadCastRecipient;
import my.mimos.mdc.domain.entity.Comment;
import my.mimos.mdc.domain.entity.Group;
import my.mimos.mdc.domain.entity.Query;
import my.mimos.mdc.domain.entity.Recipient;
import my.mimos.mdc.domain.entity.Response;
import my.mimos.mdc.domain.entity.Role;
import my.mimos.mdc.domain.entity.UrgencyLevel;
import my.mimos.mdc.domain.entity.User;
import my.mimos.mdc.domain.mapper.BroadCastMapper;
import my.mimos.mdc.domain.mapper.QueryMapper;
import my.mimos.mdc.domain.mapper.UserMapper;
import my.mimos.mdc.enums.StatusType;
import my.mimos.mdc.resources.BaseResponseResource;
import my.mimos.mdc.resources.broadcast.BroadCastInboxResource;
import my.mimos.mdc.resources.broadcast.SearchBroadCastResource;
import my.mimos.mdc.resources.query.AckRequestResource;
import my.mimos.mdc.resources.query.AckResource;
import my.mimos.mdc.resources.query.AckResponseResource;
import my.mimos.mdc.resources.query.AddUrgencyRequestResource;
import my.mimos.mdc.resources.query.AgencyRecipientResource;
import my.mimos.mdc.resources.query.AttachmentResource;
import my.mimos.mdc.resources.query.AttachmentResponseResource;
import my.mimos.mdc.resources.query.CommentResource;
import my.mimos.mdc.resources.query.DisplayUserResource;
import my.mimos.mdc.resources.query.GroupRecipientResource;
import my.mimos.mdc.resources.query.ListRecipientResponseResource;
import my.mimos.mdc.resources.query.MediaUploadResponseResource;
import my.mimos.mdc.resources.query.QueryDetailResource;
import my.mimos.mdc.resources.query.QueryInboxResource;
import my.mimos.mdc.resources.query.QueryInboxResponseResource;
import my.mimos.mdc.resources.query.QueryResource;
import my.mimos.mdc.resources.query.QueryResponseResource;
import my.mimos.mdc.resources.query.RecipientResource;
import my.mimos.mdc.resources.query.RecipientsRequestResource;
import my.mimos.mdc.resources.query.SearchAckRequestResource;
import my.mimos.mdc.resources.query.SearchAttachmentRequestResource;
import my.mimos.mdc.resources.query.SearchCommentRequestResource;
import my.mimos.mdc.resources.query.SearchQueryRequestResource;
import my.mimos.mdc.resources.query.SearchQueryResponseResource;
import my.mimos.mdc.resources.query.SearchRecipientRequestResource;
import my.mimos.mdc.resources.query.SearchRecipientResponseResource;
import my.mimos.mdc.resources.query.SearchResponsesRequestResource;
import my.mimos.mdc.resources.query.SendQueryRequestResource;
import my.mimos.mdc.resources.query.SendQueryResponseResource;
import my.mimos.mdc.resources.query.UpdateQueryRequestResource;
import my.mimos.mdc.resources.query.UpdateUrgencyRequestResource;
import my.mimos.mdc.resources.query.UrgencyFlagRequestResource;
import my.mimos.mdc.resources.query.UrgencyLevelResponseResource;
import my.mimos.mdc.resources.query.UrgencyLevelsResponseResource;
import my.mimos.mdc.resources.query.UrgencyResource;
import my.mimos.mdc.resources.query.UserRecipientResource;
import my.mimos.mdc.resources.user.SearchUsersRequestResource;
import my.mimos.mdc.service.AsyncService;
import my.mimos.mdc.service.EmailService;
import my.mimos.mdc.service.PushNotificationService;
import my.mimos.mdc.service.QueryService;
import my.mimos.mdc.service.QueryTrackerService;
import my.mimos.mdc.service.ResponseService;
import my.mimos.mdc.service.UserManagementService;
import my.mimos.mdc.utils.DateUtil;
import my.mimos.mdc.utils.MdcConstants;
/**
 * 
 * @author beaula.fernandez
 *
 */

@Log4j
@Component
public class QueryServiceImpl implements QueryService{

	@Autowired
	QueryCommand queryCommand;
	@Autowired
	ResponseCommand responseCommand;
	@Autowired
	UserManagementCommand userCommand;
	@Autowired
	DeviceCommand deviceCommand;
	@Autowired
	UserManagementService userService;
	@Autowired
	ResponseService responseService;
	@Autowired
	PushNotificationService pushService;
	@Autowired
	AsyncService asyncService;
	@Autowired
	EmailService emailService;	
	@Autowired
	QueryTrackerService queryTrackerService;
	@Autowired
	QueryMapper queryMapper;	
	@Autowired
	UserMapper userMapper;	
	@Autowired
	DateUtil dateUtil;
	@Autowired
	BroadCastCommand broadCastCommand;
	@Autowired
	BroadCastMapper broadCastMapper;
	
	@Override
	public SendQueryResponseResource sendQuery(SendQueryRequestResource query) {
		
		SendQueryResponseResource response = new SendQueryResponseResource();
		try{
			// create the new query
			Query queryToSend = queryMapper.getMapperFacade().map(query, Query.class);
			queryToSend.setCreatedDate(new Date());
			queryToSend.setModifiedDate(new Date());
			queryToSend.setQueryStatus(MdcConstants.STATUS_PENDING);
			queryToSend.setReadReciept(false);
			queryToSend.setSenderReadStatus(true);
			queryToSend.setSentBy(userService.getUserDetailsFromSecurityContext());
			
			// Set urgency flag to normal
			UrgencyLevel urgencyLevel = queryCommand.getUrgencyLevelByName(MdcConstants.QUERY_URGENCY_NORMAL);		
			queryToSend.setUrgencyLevel(urgencyLevel);
			Query sentQuery = queryCommand.saveQuery(queryToSend);
			log.info("Query saved to the database successfully!");
			
			QueryResource sentQueryResource = queryMapper.getMapperFacade().map(sentQuery, QueryResource.class);
			response.setQuery(sentQueryResource);
			
			// Add Admin as default recipients
			setDefaultRecipientsForQuery(sentQuery.getQueryId());
			
			// Link uploaded attachments to the query
			if(null!=query.getUploadIds()&& !query.getUploadIds().isEmpty()){
				SearchAttachmentRequestResource searchCriteria = new SearchAttachmentRequestResource();
				searchCriteria.setUploadIds(query.getUploadIds());				
				List<Attachment> uploads = queryCommand.searchQueryAttachments(searchCriteria);
				if(null!= uploads && !uploads.isEmpty()){
					uploads.forEach(upload -> upload.setQuery(sentQuery));
					queryCommand.saveAttachment(uploads);
				}
				log.info("Uploaded attachments linked to the query successfully!");
			}
			
			//UPDATE TRACKER
			queryTrackerService.sendQueryTracker(sentQuery);
			
			response.setStatus("Query sent successfully.");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);	
			log.info("Query sent successfully!");
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus("Query send failed.");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}
	
	/**
	 * Add all KDN Admin Users as default recipients for every new query
	 * @return
	 */
	public void setDefaultRecipientsForQuery(Long queryId){		
		try{
			SearchUsersRequestResource searchCriteria = new SearchUsersRequestResource();
			searchCriteria.setUserRole(MdcConstants.ROLE_ADMIN);
			List<User> userList = userCommand.searchUsers(searchCriteria);
			List<Long> userIdList = userList.stream().map(User::getUserId).collect(Collectors.toList());
			
			// set default recipients
			RecipientsRequestResource addRecipientResource = new RecipientsRequestResource();
			addRecipientResource.setQueryId(queryId);
			addRecipientResource.setRecipientIds(userIdList);
			BaseResponseResource response = addRecipients(addRecipientResource);
			log.info("Adding KDN Administrators as recipients of the query.");
			
			if(response.getStatusType().equals(StatusType.ERROR)){
				throw new RuntimeException("Adding admininstrators(KDN) as recipeint failed");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex.getMessage());
		}
	}
	
	@Override
	public BaseResponseResource updateQuery(UpdateQueryRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			Query query = queryCommand.findByQueryId(request.getQueryId());
			if(StringUtils.isNotBlank(request.getSubject())){
				query.setSubject(request.getSubject());
			}
			if(StringUtils.isNotBlank(request.getDescription())){
				query.setDescription(request.getDescription());
			}
			// Link uploaded attachments to the query
			if(null!=request.getUploadIds()&& !request.getUploadIds().isEmpty()){
				SearchAttachmentRequestResource searchCriteria = new SearchAttachmentRequestResource();
				searchCriteria.setUploadIds(request.getUploadIds());				
				List<Attachment> uploads = queryCommand.searchQueryAttachments(searchCriteria);
				if(null!= uploads && !uploads.isEmpty()){
					uploads.forEach(upload -> upload.setQuery(query));
					queryCommand.saveAttachment(uploads);
				}
			}
			// UPDATE QUERY ACTIVITY DATE - ACTION EDIT QUERY
			query.setModifiedDate(new Date());
			query.setSenderReadStatus(false);
			queryCommand.saveQuery(query);
			
			//PUSH NOTIFICATION
			List<Long> notifyUser = new ArrayList<>();
			notifyUser.add(query.getSentBy().getUserId());
			asyncService.pushNotificationToDevices(query.getQueryId(),notifyUser,MdcConstants.NOTIFY_EDIT_QUERY,MdcConstants.NOTIFY_TYPE_QUERY);
			asyncService.sendEmailNotification(query.getQueryId(),notifyUser,MdcConstants.NOTIFY_EDIT_QUERY);
			
			response.setStatus("Query details updated.");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus("Unable to update the query.");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}
	
	@Override
	public BaseResponseResource removeQuery(Long queryId) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			queryCommand.removeQuery(queryId);
			
			response.setStatus("Query deleted.");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus("Unable to delete the query.");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}

	@Override
	public SearchQueryResponseResource searchQuery(SearchQueryRequestResource searchCriteria) {
		SearchQueryResponseResource response = new SearchQueryResponseResource();
		try{
			List<Query> queryList = queryCommand.searchQuery(searchCriteria);
			List<QueryResource> queryResource = queryMapper.getMapperFacade().mapAsList(queryList, QueryResource.class);
			
			response.setQuery(queryResource);
			response.setStatus("Query search successful.");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus("Query search failed.");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}
	
	@Override
	public BaseResponseResource forward(RecipientsRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		String statusMsg = "";
		try{			
			if(null != request.getRecipientIds() && !request.getRecipientIds().isEmpty()){				
				BaseResponseResource response1 = addRecipients(request); 
				if(response1.getStatusType().equals(StatusType.ERROR)){
					statusMsg.concat(" Adding individual recipients failed ");
				}
			}
			if(null != request.getDeptIds() && !request.getDeptIds().isEmpty()){
				BaseResponseResource response2 = addAgencyRecipients(request);
				if(response2.getStatusType().equals(StatusType.ERROR)){
					statusMsg.concat(" Adding recipients under the selected agency failed ");
				}
			}
			if(null != request.getGroupIds() && !request.getGroupIds().isEmpty()){
				BaseResponseResource response3 = addGroupRecipients(request);
				if(response3.getStatusType().equals(StatusType.ERROR)){
					statusMsg.concat(" Adding recipients under the selected groups failed ");
				}
			}

			//FORWARD FLAG
			Query existingQuery = queryCommand.findByQueryId(request.getQueryId());	
			if(!existingQuery.isForwardFlag()){
				existingQuery.setForwardFlag(true);
				queryCommand.saveQuery(existingQuery);
			}			
					
			response.setStatus("Query forwarded successfully");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(statusMsg);
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	/**
	 * Add Individual Recipients to a Query
	 * @param request
	 * @return
	 */
	public BaseResponseResource addRecipients(RecipientsRequestResource request) {
		
		BaseResponseResource response = new BaseResponseResource();
		StringBuilder statusMsg = new StringBuilder();
		
		try{	
			if(null == request.getRecipientIds() || request.getRecipientIds().size() == 0){
				throw new RuntimeException("recipient user ids required");
			}
			List<Recipient> recipientList = new ArrayList<>();
			Recipient recipient = null;
			
			//get all users from user ids
			List<Long> userIdList = request.getRecipientIds();				
			List<User> existingUsers = userCommand.findByIds(userIdList);
			List<Long> notifyUsers = new ArrayList<>();
			Set<Long> depts = new HashSet<>();
			
			//get query from query id
			Query existingQuery = queryCommand.findByQueryId(request.getQueryId());
			
			//save recipient if recipient does not exist for the query
			for(User user: existingUsers){
				
				SearchRecipientRequestResource searchCriteria = new SearchRecipientRequestResource();
				searchCriteria.setQueryId(existingQuery.getQueryId());
				searchCriteria.setUserId(user.getUserId()); 
				searchCriteria.setRecipientType(MdcConstants.RECIPIENT_TYPE_USER);
				
				List<Recipient> existingRecipientList = queryCommand.searchQueryRecipient(searchCriteria);
				if(null != existingRecipientList && existingRecipientList.size()!= 0){					
					continue;					
				}else{				
					recipient = new Recipient();
					recipient.setRecipient(user);
					recipient.setQuery(existingQuery);			
					recipient.setAssignedDate(new Date());
					recipient.setLastActivityDate(new Date());
					recipient.setReadStatus("false"); 
					recipient.setRecipientType(MdcConstants.RECIPIENT_TYPE_USER);
					recipientList.add(recipient);
					notifyUsers.add(user.getUserId());					
					if(user.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL::equals)){
						depts.add(user.getDepartment().getDeptId());
					}
				}
			}		
			
			if(null != recipientList && recipientList.size() >0){
				queryCommand.addRecipients(recipientList);	
				log.info("Query induvidual recipient(s) added!");
			}	
			
			// send notifications
			if(null != notifyUsers && notifyUsers.size() >0){
				log.info("Sending 'New Query' Push Notification...");
				asyncService.pushNotificationToDevices(request.getQueryId(),notifyUsers,MdcConstants.NOTIFY_NEW_QUERY,MdcConstants.NOTIFY_TYPE_QUERY);
				asyncService.sendEmailNotification(request.getQueryId(),notifyUsers,MdcConstants.NOTIFY_NEW_QUERY);
			}
			
			//UPDATE TRACKER
			queryTrackerService.forwardQueryTracker(existingQuery, depts);
			
			if(recipientList.isEmpty() || null == recipientList ){
				statusMsg.append("Recepients already exists.");
			}else{
				statusMsg.append("Recipients added successfully.");
			}
			response.setStatus(statusMsg.toString());
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
	
	/**
	 * Add Group Recipients - Add all users under a group to a query
	 * @param request
	 * @return
	 */
	public BaseResponseResource addGroupRecipients(RecipientsRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		StringBuilder statusMsg = new StringBuilder();
		try {
			List<Recipient> recipientList = new ArrayList<>();
			Recipient recipient = null;
			List<String> groupIds = new ArrayList<>();

			// get all users from group
			List<Long> groupIdList = request.getGroupIds();
			List<Group> existingGroup = userCommand.findByGroupIds(groupIdList);
			List<Long> notifyUsers = new ArrayList<>();
			Set<Long> depts = new HashSet<>();
			
			// get query from query id
			Query existingQuery = queryCommand.findByQueryId(request.getQueryId());

			if (!existingGroup.isEmpty() && existingGroup != null) {
				for (Group group : existingGroup) {
					for (User user : group.getUser()) {
						
						SearchRecipientRequestResource searchCriteria = new SearchRecipientRequestResource();
						searchCriteria.setQueryId(existingQuery.getQueryId());
						searchCriteria.setUserId(user.getUserId());
						searchCriteria.setGroupId(group.getGroupId());
						searchCriteria.setRecipientType(MdcConstants.RECIPIENT_TYPE_GROUP);
						
						List<Recipient> existingRecipientList = queryCommand.searchQueryRecipient(searchCriteria);
						if (null != existingRecipientList && existingRecipientList.size() != 0) {
							groupIds.add(String.valueOf(searchCriteria.getGroupId()));
							groupIds.add(String.valueOf(searchCriteria.getUserId()));
							continue;
						} else {
							recipient = new Recipient();
							recipient.setRecipient(user);
							recipient.setGroup(group);
							recipient.setQuery(existingQuery);
							recipient.setAssignedDate(new Date());
							recipient.setLastActivityDate(new Date());
							recipient.setReadStatus("false");
							recipient.setRecipientType(MdcConstants.RECIPIENT_TYPE_GROUP);
							recipientList.add(recipient);
							notifyUsers.add(user.getUserId());
							if(user.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL::equals)){
								depts.add(user.getDepartment().getDeptId());
							}
						}
					}
				}
			}

			if(null != recipientList && recipientList.size() >0){
				queryCommand.addRecipients(recipientList);
				log.info("Group Recipients added to Query.");				
			}				
			
			// send notifications
			if(null != notifyUsers && notifyUsers.size() >0){
				log.info("Sending 'New query' push notifications to group recipients");
				asyncService.pushNotificationToDevices(request.getQueryId(),notifyUsers,MdcConstants.NOTIFY_NEW_QUERY,MdcConstants.NOTIFY_TYPE_QUERY);
				asyncService.sendEmailNotification(request.getQueryId(),notifyUsers,MdcConstants.NOTIFY_NEW_QUERY);
			}	

			if (recipientList.isEmpty() || null == recipientList) {
				statusMsg.append("Recepients already exists.");
			} else {
				statusMsg.append("Recipients added successfully.");
			}
			if (groupIds != null && groupIds.size() != 0) {
				statusMsg.append("Existing recepient ids:" + StringUtils.join(groupIds, ","));
			}
			
			//UPDATE TRACKER
			queryTrackerService.forwardQueryTracker(existingQuery, depts);
			
			response.setStatus(statusMsg.toString());
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	
	/**
	 * Add Department/Agency members as recipients to a query
	 * @param request
	 * @return
	 */
	public BaseResponseResource addAgencyRecipients(RecipientsRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		try{			
			if(null == request.getDeptIds()){
				throw new RuntimeException("agency/department ids required");
			}
			
			//get all the users under agencies
			SearchUsersRequestResource searchCriteria = new SearchUsersRequestResource();
			searchCriteria.setDeptIds(request.getDeptIds());
			List<User> agencyMembers = userCommand.searchUsers(searchCriteria);
			if(null == agencyMembers || agencyMembers.size() < 1){
				throw new RuntimeException("no users exist under the agencies");
			}
			
			//get query details
			Query existingQuery = queryCommand.findByQueryId(request.getQueryId());
			Set<Long> depts = new HashSet<>();
			
			//add users as query recipients
			List<Recipient> recipientList = new ArrayList<>();
			List<Long> notifyUsers = new ArrayList<>();
			Recipient recipient = null;
			for(User user : agencyMembers){
				
				SearchRecipientRequestResource searchRecipient = new SearchRecipientRequestResource();
				searchRecipient.setQueryId(existingQuery.getQueryId());
				searchRecipient.setUserId(user.getUserId());
				searchRecipient.setDeptId(user.getDepartment().getDeptId());
				searchRecipient.setRecipientType(MdcConstants.RECIPIENT_TYPE_AGENCY);
				
				// check if the department-user is already a recipient
				List<Recipient> existingRecipientList = queryCommand.searchQueryRecipient(searchRecipient);
				if(null != existingRecipientList && existingRecipientList.size()!= 0){	
					continue;					
				}else{			
					recipient = new Recipient();
					recipient.setRecipient(user);
					recipient.setQuery(existingQuery);	
					recipient.setDept(user.getDepartment());
					recipient.setAssignedDate(new Date());
					recipient.setLastActivityDate(new Date());
					recipient.setReadStatus("false"); 
					recipient.setRecipientType(MdcConstants.RECIPIENT_TYPE_AGENCY);
					recipientList.add(recipient);
					notifyUsers.add(user.getUserId());
					if(user.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL::equals)){
						depts.add(user.getDepartment().getDeptId());
					}
				}	
			} 
			if(null != recipientList && recipientList.size() >0){
				queryCommand.addRecipients(recipientList);
			}				
			
			// send notifications
			if(null != notifyUsers && notifyUsers.size() >0){
				log.info("Sending 'New Query' push notification to agency(s)... ");
				asyncService.pushNotificationToDevices(request.getQueryId(),notifyUsers,MdcConstants.NOTIFY_NEW_QUERY,MdcConstants.NOTIFY_TYPE_QUERY);
				asyncService.sendEmailNotification(request.getQueryId(),notifyUsers,MdcConstants.NOTIFY_NEW_QUERY);
			}		
			
			//UPDATE TRACKER
			queryTrackerService.forwardQueryTracker(existingQuery, depts);
			
			//set the response
			response.setStatus("Forwarded to Agency/Department(s)");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);	
			log.info("Query Forwarded to Agency successfully!");
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}
	

	@Override
	public BaseResponseResource removeQueryRecipients(RecipientsRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		String statusMsg = "";		
		try{			
			if(null != request.getRecipientIds() && !request.getRecipientIds().isEmpty()){				
				BaseResponseResource response1 = removeRecipients(request); 
				if(response1.getStatusType().equals(StatusType.ERROR)){
					statusMsg.concat(" Removing individual recipients failed ");
				}
			}
			if(null != request.getDeptIds() && !request.getDeptIds().isEmpty()){
				BaseResponseResource response2 = removeAgencyRecipients(request);
				if(response2.getStatusType().equals(StatusType.ERROR)){
					statusMsg.concat(" Removing recipients under the selected agency failed ");
				}
			}
			if(null != request.getGroupIds() && !request.getGroupIds().isEmpty()){
				BaseResponseResource response3 = removeGroupRecipients(request);
				if(response3.getStatusType().equals(StatusType.ERROR)){
					statusMsg.concat(" Removing recipients under the selected groups failed ");
				}
			}		
			response.setStatus("recipients removed successfully");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(statusMsg);
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}
	

	public BaseResponseResource removeRecipients(RecipientsRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		StringBuilder statusMsg = new StringBuilder();
		try{	
			List<Long> userIds = request.getRecipientIds();
			Long queryId = request.getQueryId();
			
			SearchRecipientRequestResource searchCriteria = new SearchRecipientRequestResource();
			searchCriteria.setQueryId(queryId);
			searchCriteria.setUserIds(userIds);
			searchCriteria.setRecipientType(MdcConstants.RECIPIENT_TYPE_USER);
			List<Recipient> existingRecipientList = queryCommand.searchQueryRecipient(searchCriteria);
			if(null != existingRecipientList && existingRecipientList.size()!= 0){					
				queryCommand.removeRecipients(existingRecipientList);
				statusMsg.append("Recepients removed successfully");
			}else{
				statusMsg.append("Recepients doesnot exist.");
			}		
			
			response.setStatus(statusMsg.toString());
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);	
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}
	

	
	public BaseResponseResource removeGroupRecipients(RecipientsRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		StringBuilder statusMsg = new StringBuilder();
		try {
			List<Long> groupIds = request.getGroupIds();
			Long queryId = request.getQueryId();

			SearchRecipientRequestResource searchCriteria = new SearchRecipientRequestResource();
			searchCriteria.setQueryId(queryId);
			searchCriteria.setGroupIds(groupIds);
			searchCriteria.setRecipientType(MdcConstants.RECIPIENT_TYPE_GROUP);
			List<Recipient> existingRecipientList = queryCommand.searchQueryRecipient(searchCriteria);
			if (null != existingRecipientList && existingRecipientList.size() != 0) {
				queryCommand.removeRecipients(existingRecipientList);
				statusMsg.append("Recepient groups removed successfully");
			} else {
				statusMsg.append("Recepients groups doesnot exist.");
			}
			response.setStatus(statusMsg.toString());
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	public BaseResponseResource removeAgencyRecipients(RecipientsRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			if(null == request.getDeptIds()){
				throw new RuntimeException("agency ids are required");
			}			
			//search recipient entries using criteria query id and dept ids of the users
			SearchRecipientRequestResource searchCriteria = new SearchRecipientRequestResource();
			searchCriteria.setQueryId(request.getQueryId());
			searchCriteria.setDeptIds(request.getDeptIds());
			searchCriteria.setRecipientType(MdcConstants.RECIPIENT_TYPE_AGENCY);
			List<Recipient> filteredRecipients = queryCommand.searchQueryRecipient(searchCriteria);	
			
			if(null == filteredRecipients || filteredRecipients.size() < 1){
				throw new RuntimeException("no recipients exist under the agency(s)");
			}			
			//remove recipients
			queryCommand.removeRecipients(filteredRecipients);
			
			response.setStatus("Agency recipient(s) removed from query");
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
	public ListRecipientResponseResource listQueryRecipients(Long queryId) {
		
		ListRecipientResponseResource response = new ListRecipientResponseResource();
		
		Set<UserRecipientResource> users = new HashSet<>();
		UserRecipientResource userRecipientResource= null;
		
		Set<GroupRecipientResource> groups = new HashSet<>();
		GroupRecipientResource groupRecipientResource= null;
		
		Set<AgencyRecipientResource> agencies = new HashSet<>();
		AgencyRecipientResource agencyRecipientResource= null;
		
		try{			
			SearchRecipientRequestResource searchCriteria = new SearchRecipientRequestResource();
			searchCriteria.setQueryId(queryId);
			List<Recipient> filteredRecipients = queryCommand.searchQueryRecipient(searchCriteria);
			if(null == filteredRecipients){
				throw new RuntimeException("no recipients");
			}
			
			for(Recipient recipient :filteredRecipients){				
				if(StringUtils.isNotBlank(recipient.getRecipientType()) && 
						recipient.getRecipientType().equalsIgnoreCase(MdcConstants.RECIPIENT_TYPE_USER)){
					
					userRecipientResource = new UserRecipientResource();
					userRecipientResource.setUserId(recipient.getRecipient().getUserId());
					userRecipientResource.setUsername(recipient.getRecipient().getUsername());
					userRecipientResource.setFirstName(recipient.getRecipient().getFirstName());
					users.add(userRecipientResource);
										
				}else if(StringUtils.isNotBlank(recipient.getRecipientType()) && 
						recipient.getRecipientType().equalsIgnoreCase(MdcConstants.RECIPIENT_TYPE_GROUP)){
					
					groupRecipientResource = new GroupRecipientResource();
					groupRecipientResource.setGroupId(recipient.getGroup().getGroupId());
					groupRecipientResource.setGroupName(recipient.getGroup().getGroupName());
					groups.add(groupRecipientResource);
					
				}else if(StringUtils.isNotBlank(recipient.getRecipientType()) && 
						recipient.getRecipientType().equalsIgnoreCase(MdcConstants.RECIPIENT_TYPE_AGENCY)){
					
					agencyRecipientResource  = new AgencyRecipientResource();
					agencyRecipientResource.setAgencyId(recipient.getDept().getDeptId());
					agencyRecipientResource.setAgencyName(recipient.getDept().getDeptName());
					agencies.add(agencyRecipientResource);
				}
			}
			log.info("Query recipients retrieved successfully!");
			
			response.setUsers(users);
			response.setAgencies(agencies);
			response.setGroups(groups);
			
			response.setStatus("Recipient retrieved successfully");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus("unable to get recipients");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}

	@Override
	public SearchRecipientResponseResource searchRecipients(SearchRecipientRequestResource request) {
		SearchRecipientResponseResource response = new SearchRecipientResponseResource();
		try{
			List<Recipient> recipientList = queryCommand.searchQueryRecipient(request);
			List<RecipientResource> recipientResource = queryMapper.getMapperFacade().mapAsList(recipientList, RecipientResource.class);
			response.setRecipients(recipientResource);
			
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
	public QueryInboxResponseResource inbox() {
		QueryInboxResponseResource response = new QueryInboxResponseResource();
		try {
			log.info("Loding inbox items...");
			Set<QueryInboxResource> queryInboxList = new HashSet<>();
			QueryInboxResource queryInbox = null;
			Query query = null;

			// get queries where sent by user id matches user id in the request
			User loggedInUser = userService.getUserDetailsFromSecurityContext();
			SearchQueryRequestResource searchCriteriaQuery = new SearchQueryRequestResource();
			searchCriteriaQuery.setSentBy(String.valueOf(loggedInUser.getUserId()));
			List<Query> queryList = queryCommand.searchQuery(searchCriteriaQuery);

			for (Query eachQuery : queryList) {
				queryInbox = new QueryInboxResource();
				queryInbox.setQueryId(eachQuery.getQueryId());
				queryInbox.setSubject(eachQuery.getSubject());
				queryInbox.setCreatedDate(dateUtil.dateToString(eachQuery.getCreatedDate()));
				queryInbox.setLastActivityDate(dateUtil.dateToString(eachQuery.getModifiedDate()));//last activity date
				if(eachQuery.isSenderReadStatus()){
					queryInbox.setReadStatus("true");// TODO : to be changed to boolean	
				}else{
					queryInbox.setReadStatus("false");
				}
				queryInbox.setForwardFlag(eachQuery.isForwardFlag());
				queryInbox.setUrgencyFlag(eachQuery.getUrgencyLevel().getUrgencyLevel());
				queryInbox.setStatus(eachQuery.getQueryStatus());		
				queryInbox.setQueryType("outgoing");
				DisplayUserResource createdBy = userMapper.getMapperFacade().map(eachQuery.getSentBy(), DisplayUserResource.class);
				queryInbox.setCreatedBy(createdBy);
				queryInboxList.add(queryInbox);
			}
			
			//get recipients where recipients user id matches user id in the request
			SearchRecipientRequestResource searchCriteriaRecipient = new SearchRecipientRequestResource();
			searchCriteriaRecipient.setUserId(loggedInUser.getUserId());				
			List<Recipient> existingRecipientList = queryCommand.searchQueryRecipient(searchCriteriaRecipient);
			
			for(Recipient recipient : existingRecipientList){
				query = recipient.getQuery();
				queryInbox = new QueryInboxResource();  
				queryInbox.setQueryId(query.getQueryId());
				queryInbox.setSubject(query.getSubject());				
				if(null != query.getCreatedDate()){
					queryInbox.setCreatedDate(dateUtil.dateToString(query.getCreatedDate()));
				}
				if(null != recipient.getAssignedDate()){
					queryInbox.setReceivedDate(dateUtil.dateToString(recipient.getAssignedDate()));
				}
				if(null != recipient.getLastActivityDate()){
					queryInbox.setLastActivityDate(dateUtil.dateToString(recipient.getLastActivityDate()));//last activity date
				}
				if(null != recipient.getReadStatus() && StringUtils.isNotBlank(recipient.getReadStatus())){
					queryInbox.setReadStatus(recipient.getReadStatus());
				}
				queryInbox.setForwardFlag(query.isForwardFlag());
				queryInbox.setUrgencyFlag(query.getUrgencyLevel().getUrgencyLevel());				
				DisplayUserResource createdBy = userMapper.getMapperFacade().map(query.getSentBy(), DisplayUserResource.class);
				queryInbox.setCreatedBy(createdBy);
				queryInbox.setQueryType("incoming");
				queryInbox.setStatus(query.getQueryStatus());
				queryInboxList.add(queryInbox);
			}

			// broadcast messages based on the last activity
			SearchBroadCastResource searchBroadCastResource = new SearchBroadCastResource();
			searchBroadCastResource.setUserId(loggedInUser.getUserId());
			List<BroadCastRecipient> broadCastRecipientList = broadCastCommand.SearchBroadCastRecipients(searchBroadCastResource);
			List<BroadCastInboxResource> broadcCastInboxList = broadCastMapper.getMapperFacade().mapAsList(broadCastRecipientList, BroadCastInboxResource.class);
			List<BroadCastInboxResource> broadcCastSortedList = broadcCastInboxList.stream()
					.sorted(Comparator.comparing(BroadCastInboxResource::getLastActivityDate).reversed())
					.collect(Collectors.toList());

			// ordering the inbox items based on the last activity
			List<QueryInboxResource> sortedQueryInboxList = new ArrayList<>();
			queryInboxList.stream().sorted(Comparator.comparing(QueryInboxResource::getLastActivityDate).reversed())
			  .forEach(e -> sortedQueryInboxList.add(e));

			response.setInbox(sortedQueryInboxList);
			response.setBroadCastInbox(broadcCastSortedList);
			response.setStatus("Inbox retrieved.");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			log.info("inbox items successfully retrieved!");
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus("Unable to retrieve inbox to the query.");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}
	
	@Override
	public QueryDetailResource getQuery(Long queryId) {
		log.info("Loading inbox item conversation thread...");
		QueryDetailResource response = new QueryDetailResource();
		try{				
			//USER DETAILS
			User loggedInUser = userService.getUserDetailsFromSecurityContext();
			boolean roleEmbassy = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_EMBASSY::equals);
			boolean roleFocal = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL::equals);
			boolean roleFocalSup = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL_SUPERVISOR::equals);	
			boolean roleKLN = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_KLN::equals);	
			boolean roleAdmin = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_ADMIN::equals);
			log.info("User : " +loggedInUser.getUsername()+ "---> Loding query conversation thread...");
			
			//QUERY
			Query query = queryCommand.findByQueryId(queryId);
			QueryResource queryResource = queryMapper.getMapperFacade().map(query, QueryResource.class);
			updateReadRecieptForQuery(query,loggedInUser);
			
			//ACKNOWLEGMENTS
			SearchAckRequestResource searchCriteriaAck = new SearchAckRequestResource();
			searchCriteriaAck.setQueryId(Long.valueOf(queryId));
			if(roleEmbassy || roleKLN){
				searchCriteriaAck.setUserRole(MdcConstants.ROLE_ADMIN);
			}else if (roleFocal||roleFocalSup){
				searchCriteriaAck.setUserDept(loggedInUser.getDepartment().getDeptId());
			}
			List<Acknowledgment> ack = queryCommand.searchQueryAcks(searchCriteriaAck);
			List<AckResource> ackResource = queryMapper.getMapperFacade().mapAsList(ack, AckResource.class);
			updateReadRecieptForAck(ack,loggedInUser);
			log.info("Query acknowledgments retrieved successfully!");
			
			//REPLY 
			SearchResponsesRequestResource searchCriteriaReply = new SearchResponsesRequestResource();
			searchCriteriaReply.setQueryId(Long.valueOf(queryId));	
			if(roleKLN){
				List<String> userRoles = new ArrayList<>();
				userRoles.add(MdcConstants.ROLE_ADMIN);
				userRoles.add(MdcConstants.ROLE_KLN);              
				searchCriteriaReply.setUserRoles(userRoles);
			}else if (roleFocal || roleFocalSup){
				searchCriteriaReply.setReplyToFocal(loggedInUser.getDepartment().getDeptId());
				/*fix : show final reply and reply from his dept*/
				//searchCriteriaReply.setUserDept(loggedInUser.getDepartment().getDeptId());
			}else if(roleEmbassy){
				searchCriteriaReply.setReplyToEmbassy(true);
			}
			List<Response> responses = responseCommand.searchQueryResponse(searchCriteriaReply);
			if(roleEmbassy){ // restrict all approval comments if embassy and show only reply
				responses.forEach(eachResponse -> eachResponse.setApprovals(null));
			}
			List<QueryResponseResource> responseResource = queryMapper.getMapperFacade().mapAsList(responses, QueryResponseResource.class);			
			//RESOLVE APPROVER OF REPLY
			if(roleFocalSup || roleAdmin || roleKLN){
				for(QueryResponseResource reply : responseResource){					
					if(reply.getResponseStatus().equalsIgnoreCase(MdcConstants.STATUS_PENDING_AT_SUPERVISOR) && roleFocalSup){
						if(loggedInUser.getDepartment().getDeptName().equals(reply.getCreatedBy().getDepartment())){
							reply.setApproverOfResponse(true);
						}
					}else if(reply.getResponseStatus().equalsIgnoreCase(MdcConstants.STATUS_PENDING_AT_ADMIN) && roleAdmin){
						reply.setApproverOfResponse(true);
					}else if(reply.getResponseStatus().equalsIgnoreCase(MdcConstants.STATUS_PENDING_AT_KLN) && roleKLN){
						reply.setApproverOfResponse(true);
					}					
				}					
			}
			updateReadRecieptForResponse(responses,loggedInUser);
			log.info("Query responses retrieved successfully!");
			
			//COMMENTS			
			if(roleFocal || roleFocalSup || roleAdmin || roleKLN ){ /*fix : including KLN*/
				SearchCommentRequestResource searchCriteriaComment = new SearchCommentRequestResource();
				searchCriteriaComment.setQueryId(Long.valueOf(queryId));	
				List<Comment> comments = responseCommand.searchQueryComments(searchCriteriaComment);
				List<CommentResource> commentResource = null;
				
				/* Adding only ADMINACTION comments for KLN view*/
				if(roleKLN){
					List<Comment> filteredComments = new ArrayList<>();
					for(Comment comment:comments){
						if(StringUtils.isNotBlank(comment.getCommentDesc()) 
								&& StringUtils.startsWithIgnoreCase(comment.getCommentDesc(),MdcConstants.ADMINACTION)){
							filteredComments.add(comment);
						}
					}
					if(null !=filteredComments && !filteredComments.isEmpty()){
						commentResource = queryMapper.getMapperFacade().mapAsList(filteredComments, CommentResource.class);
					}
				}else{
					commentResource = queryMapper.getMapperFacade().mapAsList(comments, CommentResource.class);
				}
				
				updateReadRecieptForComment(comments,loggedInUser);
				response.setComments(commentResource);				
				log.info("Query comments retrieved successfully!");
			}
			 
			updateQueryReadStatus(query,loggedInUser);
			log.info("updating read status successful.");
			
			response.setQuery(queryResource);
			response.setAcknowledgments(ackResource);
			response.setResponses(responseResource);
			
			response.setStatus("Query details retrieved.");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			log.info("Query Converstion thread loaded completely!");
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus("Unable to retrieve the query.");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}

	@Override
	public MediaUploadResponseResource uploadMedia(MultipartFile multipartFile, String title, Long queryId,Long responseId) {
		MediaUploadResponseResource response = new MediaUploadResponseResource();
		try{			
			long mediaSize = multipartFile.getSize();
			String contentType = multipartFile.getContentType();			
			byte[] byteArray = multipartFile.getBytes();
			
			Attachment attachment = new Attachment();
			attachment.setContentType(contentType);
			attachment.setUploadTitle(title);
			attachment.setUploadDate(new Date());
			attachment.setContent(byteArray);
			attachment.setUploadSize(mediaSize);
			if(null != queryId){
				Query existingQuery = queryCommand.findByQueryId(queryId);
				attachment.setQuery(existingQuery);
			}
			if(null != responseId){
				Response existingResponse = responseCommand.findByResponseId(responseId);
				attachment.setResponse(existingResponse);
			}
				
			User loggedInUser = userService.getUserDetailsFromSecurityContext();
			attachment.setUploadBy(loggedInUser);
			Attachment savedAttachment =queryCommand.saveAttachment(attachment);
			
			response.setUploadId(savedAttachment.getUploadId());
			response.setStatus("attachment upload successful");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus("Unable to upload attachment");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}

	@Override
	public AttachmentResponseResource getAttachment(Long uploadId) {
		AttachmentResponseResource response = new AttachmentResponseResource();
		try{
			Attachment uploadedAttachment = queryCommand.getAttachment(uploadId);
			AttachmentResource attachment = queryMapper.getMapperFacade().map(uploadedAttachment, AttachmentResource.class);
			response.setAttachment(attachment);
			
			response.setStatus("attachment retrieved successful");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus("Unable to retrieve attachment");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
	}
	return response;
	}

	@Override
	public BaseResponseResource removeAttachment(Long uploadId) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			queryCommand.removeAttachment(uploadId);			
			response.setStatus("attachment removed.");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus("Unable to remove the attachment level.");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}
	
	@Override
	public AckResponseResource acknowledgeQuery(AckRequestResource request, boolean accept) {
		AckResponseResource response = new AckResponseResource();
		try{			
			Query existingQuery = queryCommand.findByQueryId(request.getQueryId());		
			
			//USER DETAILS
			User loggedInUser = userService.getUserDetailsFromSecurityContext();		
			boolean roleAdmin = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_ADMIN::equals);
			boolean roleFocal = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL::equals);
			boolean roleFocalSup = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL_SUPERVISOR::equals);	
			
			if(!(roleAdmin||roleFocal||roleFocalSup)){
				throw new RuntimeException("Acknowledments can be added by Admininstrator or Focal Point");
			}
			
			//CHECK IF RECIPIENT
			SearchRecipientRequestResource searchRecipient = new SearchRecipientRequestResource();
			searchRecipient.setUserId(loggedInUser.getUserId());
			searchRecipient.setQueryId(request.getQueryId());
			List<Recipient> recipients = queryCommand.searchQueryRecipient(searchRecipient);
			if(null == recipients || recipients.isEmpty()){
				throw new RuntimeException("user is not a recipient of the query");				
			}
			
			//ADD ACK
			Acknowledgment  ack = new Acknowledgment();
			if(accept){
				ack.setAckStatus(MdcConstants.STATUS_ACCEPT);
			}else{
				ack.setAckStatus(MdcConstants.STATUS_REJECT);
			}			
			ack.setAckComment(request.getComment());
			ack.setQuery(existingQuery);
			ack.setCreatedDate(new Date());		
			ack.setAckBy(loggedInUser);			
			Acknowledgment savedAck = queryCommand.saveAck(ack);
			log.info("Query acknowledgments added successfully!");
			
			if(roleAdmin){
				//UPDATE QUERY STATUS
				log.info("updating query status...");
				if(accept){
					existingQuery.setQueryStatus(MdcConstants.STATUS_APPROVE);	
				}else{
					existingQuery.setQueryStatus(MdcConstants.STATUS_REJECT);	
				}
				existingQuery.setApprovedDate(new Date());
				existingQuery.setApprovedBy(loggedInUser);				
				
				// UPDATE QUERY ACTIVITY DATE - ACTION ACK QUERY
				log.info("updating query activity date...");
				existingQuery.setModifiedDate(new Date());		
				existingQuery.setSenderReadStatus(false);
				queryCommand.saveQuery(existingQuery);
			}			
			
			//PUSH NOTIFICATION AND EMAIL | UPDATE LAST ACTIVITY DATES
			List<Long> notifyUser = new ArrayList<>();
			SearchRecipientRequestResource searchCriteria = new SearchRecipientRequestResource();
			List<Recipient> existingRecipientList =  null;
			searchCriteria.setQueryId(existingQuery.getQueryId());
			
			//ACK FROM FOCAL TO ADMIN
			if(roleFocal || roleFocalSup){	
				searchCriteria.setFocalDeptId(loggedInUser.getDepartment().getDeptId());
				searchCriteria.setFilterAdminAndfocalOfDept(true);	// ack will be sent to all admins and focal in his dept	
			}
			//ACK FROM ADMIN TO FDM
			else if(roleAdmin){
				notifyUser.add(existingQuery.getSentBy().getUserId()); // ack sent to fdm and other admins	
				List<String> roles = new ArrayList<>();
				roles.add(MdcConstants.ROLE_ADMIN);
				roles.add(MdcConstants.ROLE_KLN);	// fix : sending notification to KLN recipients			
				searchCriteria.setRoles(roles);
			}	
			
			//UPDATE LAST ACTIVITY DATE FOR RECIPIENTS RECEIVING ACK
			existingRecipientList = queryCommand.searchQueryRecipient(searchCriteria);
			existingRecipientList.forEach(recipient -> notifyUser.add(recipient.getRecipient().getUserId()));			
			for(Recipient recipient : existingRecipientList){
				recipient.setLastActivityDate(new Date());
				if(recipient.getRecipient().getUserId() != loggedInUser.getUserId()){
					recipient.setReadStatus("false");
				}					
			}	
			
			// updating recipients' last activity date
			queryCommand.addRecipients(existingRecipientList);
			// remove the sender's user id from receiving notification
			notifyUser.remove(loggedInUser.getUserId()); 
			
			asyncService.pushNotificationToDevices(existingQuery.getQueryId(),notifyUser,MdcConstants.NOTIFY_ACK_QUERY,MdcConstants.NOTIFY_TYPE_QUERY);
			asyncService.sendEmailNotification(existingQuery.getQueryId(),notifyUser,MdcConstants.NOTIFY_ACK_QUERY);
			
			//UPDATE TRACKER
			queryTrackerService.acknowledgeQueryTracker(existingQuery, loggedInUser, accept);
			
			response.setAckId(savedAck.getAckId());
			response.setStatus("acknowledgment sent successfully");
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
	
	/**
	 * Updated the Read status of a message  (query appears in bold in inbox listing)
	 * indicate if a recipient has any unread item in the conversation thread
	 * @param query
	 * @param loggedInUser
	 * @return
	 */
	public BaseResponseResource updateQueryReadStatus(Query query,User loggedInUser) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			
			//find the recipient entity record 
			SearchRecipientRequestResource searchCriteria = new SearchRecipientRequestResource();
			searchCriteria.setQueryId(query.getQueryId());
			searchCriteria.setUserId(loggedInUser.getUserId());
			List<Recipient> existingRecipientList = queryCommand.searchQueryRecipient(searchCriteria);
			if(null == existingRecipientList){
				throw new RuntimeException("Recipients doesnot exist");
			}else{
				existingRecipientList.stream().forEach( recipient -> recipient.setReadStatus("true"));
			}
			queryCommand.addRecipients(existingRecipientList);
			
			// updating the read status of sender of the query ( kln and embassy - new query permission)
			if(loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_EMBASSY::equals) ||
					loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_KLN::equals)){
				query.setSenderReadStatus(true);
				queryCommand.saveQuery(query);
			}
			
			response.setStatus("Query read status updated");
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
	
	/**
	 * Updated the read receipt of a query 
	 * Read receipt is shown as tick on the sender's message bubble
	 * It indicates if the query is seen by Admin
	 * @param query
	 * @param loggedInUser
	 */
	public void updateReadRecieptForQuery(Query query, User loggedInUser){
		try{
			boolean roleAdmin = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_ADMIN::equals);
			if(!query.isReadReciept()){
				if(roleAdmin){
					query.setReadReciept(true);
					queryCommand.saveQuery(query);
					log.info("Read receipt of query" +query.getQueryId()+ "updated as true!");
				}
			}						
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Updated the read receipt of an acknowledgment 
	 * Read receipt is shown as tick on the sender's message bubble
	 * It indicates if the acknowledgment by focal is seen by Admin and
	 * acknowledgment by focal is seen by FDM
	 * @param query
	 * @param loggedInUser
	 */
	public void updateReadRecieptForAck(List<Acknowledgment> acks,User loggedInUser){
		try{			
			List<Acknowledgment> filteredResult = new ArrayList<>();
			boolean roleAdmin = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_ADMIN::equals);
			boolean roleEmbassy = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_EMBASSY::equals);
			
			for(Acknowledgment ack:acks){				
				if(!ack.isReadReciept()){
					// if ack by focal/focal supervisor to admin read receipts are updated only when seen by admin.
					if((ack.getAckBy().getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL::equals) ||
							ack.getAckBy().getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL_SUPERVISOR::equals))
							&& roleAdmin ){
						ack.setReadReciept(true);
					// if ack by admin to fdm read receipts are updated only when seen by the fdm.
					}else if(ack.getAckBy().getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_ADMIN::equals)&& roleEmbassy){
						ack.setReadReciept(true);
					}
					filteredResult.add(ack);
				}
			}
			
			if(null != filteredResult && !filteredResult.isEmpty()){
				queryCommand.saveAcks(filteredResult);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * Updated the read receipt of a query 
	 * Read receipt is shown as tick on the sender's message bubble
	 * It indicates if the response from focal point is seen by Sup/Admin and
	 * response from admin/kln is seen by embassy
	 * @param query
	 * @param loggedInUser
	 */
	public void updateReadRecieptForResponse(List<Response> responses, User loggedInUser){
		try{
			boolean roleAdmin = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_ADMIN::equals);
			boolean roleFocalSup = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL_SUPERVISOR::equals);	
			boolean roleKLN = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_KLN::equals);
			boolean roleEmbassy = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_EMBASSY::equals);
			
			List<Response> filteredResult = new ArrayList<>();
			for(Response response:responses){				
				if(!response.isReadReciept()){
					// Read receipt of a reply from a focal person is updated when a focal supervisor of his dept or admin views it
					if(response.getResponseBy().getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL::equals)){ 
						if((roleFocalSup && response.getResponseBy().getDepartment().getDeptId().equals(loggedInUser.getDepartment().getDeptId())
							||	roleAdmin)){
							response.setReadReciept(true);
						}						
					}// Read receipt of a reply from a focal supervisor is set true when admin views it
					else if(response.getResponseBy().getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL_SUPERVISOR::equals)){ 
						if(roleAdmin){
							response.setReadReciept(true);
							filteredResult.add(response);
						}						
					}// Read receipt of a reply from an admin is set true when KLN or embassy(direct reply) views it
					else if(response.getResponseBy().getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_ADMIN::equals)){
						if(roleKLN || roleEmbassy){
							response.setReadReciept(true);
							filteredResult.add(response);
						}						
					}//Read receipt for a kln is updated only when a embassy views it
					else if(response.getResponseBy().getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_KLN::equals)){
						if(roleEmbassy){
							response.setReadReciept(true);
							filteredResult.add(response);
						}
					}
					//filteredResult.add(response);
				}
			}
			
			if(null != filteredResult && !filteredResult.isEmpty()){
				responseCommand.saveResponse(filteredResult);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void updateReadRecieptForComment(List<Comment> comments, User loggedInUser){
		try{
			boolean roleFocalSup = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL_SUPERVISOR::equals);	
			boolean roleFocal = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_FOCAL::equals);			
			for(Comment comment:comments){	
				if(comment.getCommentBy().getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_ADMIN::equals) && (roleFocal || roleFocalSup)){
					comment.setReadReciept(true);	//update the read receipts of comments by admin when seen by focal point
				}							
			}
			if(null != comments || !comments.isEmpty()){
				responseCommand.addComment(comments);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void updateLastActivityDate(List<Recipient> recipients){
		try{
			if(null != recipients && !recipients.isEmpty()){
				log.info("updating last activity date of a recipient...");
				recipients.forEach(recipient ->recipient.setLastActivityDate(new Date()));
				queryCommand.addRecipients(recipients);
			}	
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	

	@Override
	public UrgencyLevelResponseResource addUrgencyLevel(AddUrgencyRequestResource request) {
		UrgencyLevelResponseResource response = new UrgencyLevelResponseResource();
		try{
			UrgencyLevel urgencyLevel= new UrgencyLevel();
			urgencyLevel.setUrgencyLevel(StringUtils.capitalize(request.getUrgencyLevel()));
			urgencyLevel.setPriority(request.getPriority());
			urgencyLevel.setCreatedDate(new Date());
			
			UrgencyLevel addedItem = queryCommand.addUrgencyLevel(urgencyLevel);
			UrgencyResource resource = queryMapper.getMapperFacade().map(addedItem, UrgencyResource.class);
			
			response.setUrgencyLevel(resource);
			response.setStatus("Urgency level added.");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus("Unable to add urgency level.");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}

	@Override
	public UrgencyLevelResponseResource getUrgencyLevel(Long id) {
		UrgencyLevelResponseResource response = new UrgencyLevelResponseResource();
		try{
			UrgencyLevel item = queryCommand.getUrgencyLevelById(id);
			UrgencyResource resource = queryMapper.getMapperFacade().map(item, UrgencyResource.class);
			response.setUrgencyLevel(resource);
			
			response.setStatus("Urgency level retrieved.");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus("Unable to get urgency level.");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}	

	@Override
	public UrgencyLevelsResponseResource listAllUrgencyLevel() {
		UrgencyLevelsResponseResource response = new UrgencyLevelsResponseResource();
		try{
			List<UrgencyLevel> items = queryCommand.listAllUrgencyLevel();
			List<UrgencyResource> resources = queryMapper.getMapperFacade().mapAsList(items, UrgencyResource.class);
			response.setUrgencyLevels(resources);
			
			response.setStatus("Urgency levels retrieved.");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		}catch(Exception ex){      
			ex.printStackTrace();
			response.setStatus("Unable to get urgency levels.");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}
	
	@Override
	public BaseResponseResource updateUrgencyLevel(UpdateUrgencyRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			UrgencyLevel urgencyLevel = queryCommand.getUrgencyLevelById(request.getUrgencyId());
			urgencyLevel.setModifiedDate(new Date());
			if(StringUtils.isNotBlank(request.getUrgencyLevel())){
				urgencyLevel.setUrgencyLevel(StringUtils.capitalize(request.getUrgencyLevel()));
			}
			if(StringUtils.isNotBlank(request.getPriority())){
				urgencyLevel.setPriority(request.getPriority());
			}
			queryCommand.addUrgencyLevel(urgencyLevel);
			
			response.setStatus("urgency level details updated.");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus("Unable to update the urgency level.");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}
	
	@Override
	public BaseResponseResource removeUrgencyLevel(String id) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			queryCommand.removeUrgencyLevel(Long.valueOf(id));			
			response.setStatus("Urgency level removed.");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus("Unable to remove the urgency level.");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}
	
	@Override
	public BaseResponseResource updateUrgencyFlag(UrgencyFlagRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			Query query = queryCommand.findByQueryId(request.getQueryId());		
			UrgencyLevel urgencyLevel = null;
			if(request.isUrgencyFlag()){
				urgencyLevel = queryCommand.getUrgencyLevelByName(MdcConstants.QUERY_URGENCY_URGENT);
			}else{
				urgencyLevel = queryCommand.getUrgencyLevelByName(MdcConstants.QUERY_URGENCY_NORMAL);
			}
			query.setUrgencyLevel(urgencyLevel);	
			queryCommand.saveQuery(query);
			
			response.setStatus("Urgency level updated.");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			log.info("Urgency level updated.");
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus("Unable to set the urgency level.");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}

	

}
