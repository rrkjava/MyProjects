/**
 * 
 */
package my.mimos.mdc.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import my.mimos.mdc.command.BroadCastCommand;
import my.mimos.mdc.command.UserManagementCommand;
import my.mimos.mdc.domain.entity.Attachment;
import my.mimos.mdc.domain.entity.BroadCast;
import my.mimos.mdc.domain.entity.BroadCastRecipient;
import my.mimos.mdc.domain.entity.BroadCastResponse;
import my.mimos.mdc.domain.entity.Group;
import my.mimos.mdc.domain.entity.Role;
import my.mimos.mdc.domain.entity.User;
import my.mimos.mdc.domain.mapper.BroadCastMapper;
import my.mimos.mdc.enums.StatusType;
import my.mimos.mdc.resources.BaseResponseResource;
import my.mimos.mdc.resources.broadcast.BroadCastMessageUpdateRequestResource;
import my.mimos.mdc.resources.broadcast.BroadCastDetailResource;
import my.mimos.mdc.resources.broadcast.BroadCastMessageRequestResource;
import my.mimos.mdc.resources.broadcast.BroadCastMessageResponseResource;
import my.mimos.mdc.resources.broadcast.BroadCastResource;
import my.mimos.mdc.resources.broadcast.BroadCastResponseRequestResource;
import my.mimos.mdc.resources.broadcast.BroadCastResponseResource;
import my.mimos.mdc.resources.broadcast.SearchBroadCastResource;
import my.mimos.mdc.resources.broadcast.SearchBroadCastResponseRequest;
import my.mimos.mdc.resources.broadcast.SendBroadCastResponseResource;
import my.mimos.mdc.resources.query.AttachmentResource;
import my.mimos.mdc.resources.query.AttachmentResponseResource;
import my.mimos.mdc.resources.query.MediaUploadResponseResource;
import my.mimos.mdc.resources.query.SearchAttachmentRequestResource;
import my.mimos.mdc.resources.user.SearchUsersRequestResource;
import my.mimos.mdc.service.AsyncService;
import my.mimos.mdc.service.BroadCastService;
import my.mimos.mdc.service.UserManagementService;
import my.mimos.mdc.utils.MdcConstants;

/**
 * @author krishna.redabotu
 *
 */

@Component
@Log4j
public class BroadCastServiceImpl implements BroadCastService {

	@Autowired
	BroadCastCommand broadCastCommand; 
	
	@Autowired
	UserManagementCommand userManagementCommand;
	
	@Autowired
	BroadCastMapper broadCastMapper;
	
	@Autowired
	UserManagementService userService;
	
	@Autowired
	AsyncService asyncService;
	
	
	@Override
	public SendBroadCastResponseResource broadCastMessage(BroadCastMessageRequestResource request) {

		SendBroadCastResponseResource response = new SendBroadCastResponseResource();
		BroadCast broadCast = new BroadCast();
		String statusMessage = "";
		
		try {
			// create broadcast message
			User existingUser = userManagementCommand.findById(request.getBroadcastBy());
			broadCast.setBroadcastBy(existingUser);
			broadCast.setSubject(request.getSubject());
			broadCast.setMessage(request.getMessage());
			broadCast.setBroadcastDate(new Date());
			
			List<Long> userIdList=request.getUserIds();
			if(null == userIdList && userIdList.isEmpty()){
				throw new RuntimeException("At least a userId Required");
			}
			BroadCast savedMessage = broadCastCommand.saveBroadCastMessage(broadCast);
			response.setResponseId(savedMessage.getMessageId());
			
			
			if(null != userIdList && !userIdList.isEmpty()){				
				BaseResponseResource baseResponse = addUserRecipients(userIdList,savedMessage);; 
				if(baseResponse.getStatusType().equals(StatusType.ERROR)){
					statusMessage.concat(" Adding individual recipients failed ");
				}
			}
			
			List<Long> groupIdList = request.getGroupIds();
			if(null != groupIdList && !groupIdList.isEmpty()){				
				BaseResponseResource baseResponse = addGroupRecipients(groupIdList,savedMessage); 
				if(baseResponse.getStatusType().equals(StatusType.ERROR)){
					statusMessage.concat(" Adding recipients under the selected groups failed");
				}
			}
			
			List<Long> uploadIds=request.getUploadIds();
			if(null != uploadIds && !uploadIds.isEmpty()){				
				BaseResponseResource baseResponse = addUploadedAttachment(uploadIds, savedMessage); 
				if(baseResponse.getStatusType().equals(StatusType.ERROR)){
					statusMessage.concat(" Adding uploaded documents filed");
				}
			}
			
			broadcastDefaultRecipients(savedMessage);

			response.setStatus("BroadCaste Message Successful");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			log.debug("broad cast message succesful debug");
		} catch (Exception ex) {
			response.setStatus(statusMessage);
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public BaseResponseResource broadCastMessageUpdate(BroadCastMessageUpdateRequestResource request) {

		BaseResponseResource response = new BaseResponseResource();
		String statusMessage = "";
		try {
			BroadCast existingMesage = broadCastCommand.finByMessageId(request.getMessageId());
			if (existingMesage == null) {
				throw new RuntimeException("broadcast message not existing");
			}
			
			List<Long> userIdList = request.getUserIds();
			if(null != userIdList && !userIdList.isEmpty()){				
				BaseResponseResource baseResponse = addUserRecipients(userIdList,existingMesage);; 
				if(baseResponse.getStatusType().equals(StatusType.ERROR)){
					statusMessage.concat(" Adding individual recipients failed ");
				}
			}
			
			List<Long> groupIdList = request.getGroupIds();
			if(null != groupIdList && !groupIdList.isEmpty()){				
				BaseResponseResource baseResponse = addGroupRecipients(groupIdList,existingMesage); 
				if(baseResponse.getStatusType().equals(StatusType.ERROR)){
					statusMessage.concat(" Adding recipients under the selected groups failed");
				}
			}
			
			List<Long> uploadIds=request.getUploadIds();
			if(null != uploadIds && !uploadIds.isEmpty()){				
				BaseResponseResource baseResponse = addUploadedAttachment(uploadIds, existingMesage); 
				if(baseResponse.getStatusType().equals(StatusType.ERROR)){
					statusMessage.concat(" Adding uploaded documents filed");
				}
			}
			log.info("broad cast message succesful");
			response.setStatus("BroadCaste recipients added successful");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		} catch (Exception ex) {
			response.setStatus(statusMessage);
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}
	
	public BaseResponseResource addUserRecipients(List<Long> userIds,BroadCast savedMessage) {
		BaseResponseResource response = new BaseResponseResource();
		StringBuilder statusMsg = new StringBuilder();

		try {
			if(null == userIds || userIds.size() == 0){
				throw new RuntimeException("recipient user ids required");
			}
			List<BroadCastRecipient> recipientList = new ArrayList<BroadCastRecipient>();
			List<User> existingUsers = userManagementCommand.findByIds(userIds);
			List<Long> notifyUsers = new ArrayList<>();
			if(!existingUsers.isEmpty() && existingUsers !=null){
				for(User user:existingUsers){
					SearchBroadCastResource searchBroadCastResource = new SearchBroadCastResource();
					searchBroadCastResource.setMessageId(savedMessage.getMessageId());
					searchBroadCastResource.setUserId(user.getUserId());
					searchBroadCastResource.setRecipientType(MdcConstants.RECIPIENT_TYPE_USER);
					List<BroadCastRecipient> broadCastRecipientList=broadCastCommand.SearchBroadCastRecipients(searchBroadCastResource);
					if(broadCastRecipientList != null && broadCastRecipientList.size() != 0){
						continue;
					} else {
						BroadCastRecipient broadCastRecipient = new BroadCastRecipient();
						broadCastRecipient.setBroadCast(savedMessage);
						broadCastRecipient.setUser(user);
						broadCastRecipient.setReadStatus("false");
						broadCastRecipient.setRecipientType(MdcConstants.RECIPIENT_TYPE_USER);
						broadCastRecipient.setReceivedDate(new Date());
						broadCastRecipient.setLastActivityDate(new Date());
						notifyUsers.add(user.getUserId());
						recipientList.add(broadCastRecipient);
					}
				}
			}
			
			if(null != recipientList && recipientList.size() >0){
				broadCastCommand.saveBroadCastRecipient(recipientList);
			}
			
			if(recipientList.isEmpty() || null == recipientList ){
				statusMsg.append("Recepients already exists.");
			}else{
				statusMsg.append("Recipients added successfully.");
			}
			
			// push notifications
			if(null != notifyUsers && notifyUsers.size() >0){
				log.info("Sending broadcast Push Notification...");
				asyncService.pushNotificationToDevices(savedMessage.getMessageId(),notifyUsers,MdcConstants.NOTIFY_NEW_BROADCAST,MdcConstants.NOTIFY_TYPE_BROADCAST);
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
	
	public BaseResponseResource addGroupRecipients(List<Long> groupIds,BroadCast savedMessage) {
		BaseResponseResource response = new BaseResponseResource();
		StringBuilder statusMsg = new StringBuilder();

		try {
			List<BroadCastRecipient> recipientList = new ArrayList<BroadCastRecipient>();
			List<Group> existingGroups = userManagementCommand.findByGroupIds(groupIds);
			List<String> groupIdList = new ArrayList<>();
			// add group recipients broadcast message
			if (!existingGroups.isEmpty() && existingGroups != null) {
				for (Group group : existingGroups) {
					for (User user : group.getUser()) {
						SearchBroadCastResource searchBroadCastResource = new SearchBroadCastResource();
						searchBroadCastResource.setMessageId(savedMessage.getMessageId());
						searchBroadCastResource.setGroupId(group.getGroupId());
						searchBroadCastResource.setRecipientType(MdcConstants.RECIPIENT_TYPE_GROUP);
						List<BroadCastRecipient> broadCastRecipientList = broadCastCommand
								.SearchBroadCastRecipients(searchBroadCastResource);
						if (broadCastRecipientList != null && broadCastRecipientList.size() != 0) {
							groupIdList.add(String.valueOf(searchBroadCastResource.getGroupId()));
							groupIdList.add(String.valueOf(searchBroadCastResource.getUserId()));
							continue;
						} else {
							BroadCastRecipient broadCastRecipient = new BroadCastRecipient();
							broadCastRecipient.setBroadCast(savedMessage);
							broadCastRecipient.setGroup(group);
							broadCastRecipient.setUser(user);
							broadCastRecipient.setReadStatus("false");
							broadCastRecipient.setRecipientType(MdcConstants.RECIPIENT_TYPE_GROUP);
							broadCastRecipient.setReceivedDate(new Date());
							broadCastRecipient.setLastActivityDate(new Date());
							recipientList.add(broadCastRecipient);
						}
					}
				}
			}
			
			if(null != recipientList && recipientList.size() >0){
				broadCastCommand.saveBroadCastRecipient(recipientList);
			}
			
			if(recipientList.isEmpty() || null == recipientList ){
				statusMsg.append("Recepients already exists.");
			}else{
				statusMsg.append("Recipients added successfully.");
			}
			
			if (groupIdList != null && groupIdList.size() != 0) {
				statusMsg.append("Existing recepient ids:" + StringUtils.join(groupIdList, ","));
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
	
	public BaseResponseResource addUploadedAttachment(List<Long> uploadIds, BroadCast savedMessage) {
		BaseResponseResource response = new BaseResponseResource();

		try {
			// Link uploaded attachments to the broadcast
			if (null != uploadIds && !uploadIds.isEmpty()) {
				SearchAttachmentRequestResource searchCriteria = new SearchAttachmentRequestResource();
				searchCriteria.setUploadIds(uploadIds);
				List<Attachment> uploads = broadCastCommand.searchQueryAttachments(searchCriteria);
				if (null != uploads && !uploads.isEmpty()) {
					uploads.forEach(upload -> upload.setBroadCast(savedMessage));
					broadCastCommand.saveAttachment(uploads);
				}
			}
			
			//updating last activity
			updateLastActivityDate(savedMessage.getMessageId());
			
			response.setStatus("Image attached to broadcast message");
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
	
	public void broadcastDefaultRecipients(BroadCast broadcast){		
		try{
			SearchUsersRequestResource searchCriteria = new SearchUsersRequestResource();
			searchCriteria.setUserRole(MdcConstants.ROLE_ADMIN);
			List<User> userList = userManagementCommand.searchUsers(searchCriteria);
			List<Long> userIdList = userList.stream().map(User::getUserId).collect(Collectors.toList());
			
			// set default recipients
			BaseResponseResource response = addUserRecipients(userIdList,broadcast);
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
	public BroadCastDetailResource getMessage(Long messageId) {
		BroadCastDetailResource response = new BroadCastDetailResource();
		try {
			BroadCast broadCast = broadCastCommand.finByMessageId(messageId);
			BroadCastResource broadCastResource = broadCastMapper.getMapperFacade().map(broadCast,
					BroadCastResource.class);

			// Update read status
			User loggedInUser = userService.getUserDetailsFromSecurityContext();
			boolean roleEmbassy = loggedInUser.getRole().stream().map(Role::getRoleName).anyMatch(MdcConstants.ROLE_EMBASSY::equals);
			SearchBroadCastResource searchBroadCastResource = new SearchBroadCastResource();
			searchBroadCastResource.setMessageId(broadCast.getMessageId());
			searchBroadCastResource.setUserId(loggedInUser.getUserId());
			updateReadStatus(searchBroadCastResource);
			
			//Response restricted to logged in user
			List<BroadCastResponse> broadCastResponse=new ArrayList<BroadCastResponse>();
			if (roleEmbassy) {
				SearchBroadCastResponseRequest searchRequest=new SearchBroadCastResponseRequest();
				searchRequest.setMessageId(broadCast.getMessageId());
				searchRequest.setUserId(loggedInUser.getUserId());
				broadCastResponse = broadCastCommand.searchBroadCastResponse(searchRequest);
			} else {
				broadCastResponse = broadCastCommand.finByBroadCastMessageId(messageId);
			}
			
			List<BroadCastResponseResource> broadCastRespoResource = broadCastMapper.getMapperFacade()
					.mapAsList(broadCastResponse, BroadCastResponseResource.class);
			response.setBroadCastResource(broadCastResource);
			response.setResponse(broadCastRespoResource);
			response.setStatus("BroadCast message retrived successful");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		} catch (Exception ex) {
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}
	
	public void updateReadStatus(SearchBroadCastResource searchBroadCastResource) {
		List<BroadCastRecipient> broadCastRecipientList = broadCastCommand
				.SearchBroadCastRecipients(searchBroadCastResource);
		if (null == broadCastRecipientList) {
			throw new RuntimeException("Recipients doesnot exist");
		} else {
			for(BroadCastRecipient broadCastRecipient: broadCastRecipientList){
				if(broadCastRecipient.getReadStatus().equalsIgnoreCase("false")){
					broadCastRecipient.setReadStatus("true");
					broadCastRecipient.setReadDate(new Date());
					broadCastCommand.saveBroadCastRecipient(broadCastRecipient);
				}
			}
		}
	}

	@Override
	public MediaUploadResponseResource uploadMedia(MultipartFile multipartFile, String title) {

		MediaUploadResponseResource response = new MediaUploadResponseResource();
		try {
			long mediaSize = multipartFile.getSize();
			String contentType = multipartFile.getContentType();
			byte[] byteArray = multipartFile.getBytes();
			
			InputStream in = new ByteArrayInputStream(byteArray);
			BufferedImage image = ImageIO.read(in);
			//check uploaded file image/pdf
			if ( image == null && !contentType.equalsIgnoreCase("application/pdf")) {
				throw new RuntimeException("file should be image/pdf format");
			}

			Attachment attachment = new Attachment();
			attachment.setContentType(contentType);
			attachment.setUploadTitle(title);
			attachment.setUploadDate(new Date());
			attachment.setContent(byteArray);
			attachment.setUploadSize(mediaSize);
			User loggedInUser = userService.getUserDetailsFromSecurityContext();
			attachment.setUploadBy(loggedInUser);
			Attachment savedAttachment = broadCastCommand.saveAttachment(attachment);
			response.setUploadId(savedAttachment.getUploadId());
			response.setStatus("attachment upload successful");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus("Unable to upload attachment" + ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public AttachmentResponseResource getAttachment(Long uploadId) {
		AttachmentResponseResource response = new AttachmentResponseResource();
		try {
			Attachment uploadedAttachment = broadCastCommand.getAttachment(uploadId);
			AttachmentResource attachment = broadCastMapper.getMapperFacade().map(uploadedAttachment,AttachmentResource.class);
			response.setAttachment(attachment);
			response.setStatus("attachment retrieved successful");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus("Unable to retrieve attachment");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public BroadCastMessageResponseResource broadCastMessageResponse(BroadCastResponseRequestResource request) {

		BroadCastMessageResponseResource response = new BroadCastMessageResponseResource();
		try {
			BroadCastResponse broadCastResponse = new BroadCastResponse();

			broadCastResponse.setResponseMessage(request.getResponseMessage());
			broadCastResponse.setResponseDate(new Date());
			// logged in user
			User loggedInUser = userService.getUserDetailsFromSecurityContext();
			broadCastResponse.setResponseBy(loggedInUser);
			
			// existing broadcast
			BroadCast existingMesage = broadCastCommand.finByMessageId(request.getMessageId());
			broadCastResponse.setBroadCast(existingMesage);
			
			BroadCastResponse broadCastResp = broadCastCommand.saveBroadCastResponse(broadCastResponse);
			// Link uploaded attachments to broadcast response
			List<Long> uploadIds = request.getUploadIds();
			if (null != uploadIds && !uploadIds.isEmpty()) {
				SearchAttachmentRequestResource searchCriteria = new SearchAttachmentRequestResource();
				searchCriteria.setUploadIds(uploadIds);
				List<Attachment> uploads = broadCastCommand.searchQueryAttachments(searchCriteria);
				if (null != uploads && !uploads.isEmpty()) {
					uploads.forEach(upload -> upload.setBroadCastResponse(broadCastResp));
					broadCastCommand.saveAttachment(uploads);
					broadCastResp.setAttachment(uploads);
				}
			}
			
			//updating last activity date
			updateLastActivityDate(existingMesage.getMessageId());
			
			List<Long> notifyUsers = new ArrayList<>();
			SearchBroadCastResource searchBroadCastResource = new SearchBroadCastResource();
			searchBroadCastResource.setMessageId(existingMesage.getMessageId());
			searchBroadCastResource.setRecipientType(MdcConstants.ROLE_ADMIN);
			List<BroadCastRecipient> broadCastRecipientList=broadCastCommand.SearchBroadCastRecipients(searchBroadCastResource);
			broadCastRecipientList.forEach(broadCastRecipient->notifyUsers.add(broadCastRecipient.getUser().getUserId()));
			
			//push notification
			log.debug("....sending broadcast response push notification.... ");
			asyncService.pushNotificationToDevices(existingMesage.getMessageId(),notifyUsers,MdcConstants.NOTIFY_BROADCAST_REPLY,MdcConstants.NOTIFY_TYPE_BROADCAST);
			BroadCastResponseResource resp = broadCastMapper.getMapperFacade().map(broadCastResp,BroadCastResponseResource.class);
			response.setResponse(resp);
			response.setStatus("Response sent.");
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
	
	public void updateLastActivityDate(Long messeageId) {
		try {
			SearchBroadCastResource searchBroadCastResource = new SearchBroadCastResource();
			searchBroadCastResource.setMessageId(messeageId);
			List<BroadCastRecipient> recipients = broadCastCommand.SearchBroadCastRecipients(searchBroadCastResource);
			if (null != recipients && !recipients.isEmpty()) {
				log.info("updating last activity date of a recipient...");
				recipients.forEach(recipient -> recipient.setLastActivityDate(new Date()));
				broadCastCommand.saveBroadCastRecipient(recipients);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
