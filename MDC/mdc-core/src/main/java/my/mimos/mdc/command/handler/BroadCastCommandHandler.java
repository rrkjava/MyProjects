/**
 * 
 */
package my.mimos.mdc.command.handler;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.querydsl.core.BooleanBuilder;

import my.mimos.mdc.command.BroadCastCommand;
import my.mimos.mdc.domain.entity.Attachment;
import my.mimos.mdc.domain.entity.BroadCast;
import my.mimos.mdc.domain.entity.BroadCastRecipient;
import my.mimos.mdc.domain.entity.BroadCastResponse;
import my.mimos.mdc.domain.entity.QAttachment;
import my.mimos.mdc.domain.entity.QBroadCastRecipient;
import my.mimos.mdc.domain.entity.QBroadCastResponse;
import my.mimos.mdc.domain.repository.AttachmentRepository;
import my.mimos.mdc.domain.repository.BroadCastRecipientRepository;
import my.mimos.mdc.domain.repository.BroadCastRepository;
import my.mimos.mdc.domain.repository.BroadCastResponseRepository;
import my.mimos.mdc.resources.broadcast.SearchBroadCastResource;
import my.mimos.mdc.resources.broadcast.SearchBroadCastResponseRequest;
import my.mimos.mdc.resources.query.SearchAttachmentRequestResource;

/**
 * @author krishna.redabotu
 *
 */

@Component
public class BroadCastCommandHandler implements BroadCastCommand {

	@Autowired
	BroadCastRepository broadCastRepository;
	
	@Autowired
	BroadCastRecipientRepository broadCastRecipientRepository;
	
	@Autowired
	AttachmentRepository attachmentRepository;
	
	@Autowired
	BroadCastResponseRepository broadCastResponseRepository;
	
	@Override
	public BroadCast saveBroadCastMessage(BroadCast broadCast) {
		BroadCast broadCastMsg = null;
		try {
			broadCastMsg = broadCastRepository.save(broadCast);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return broadCastMsg;
	}

	@Override
	public List<BroadCastRecipient> SearchBroadCastRecipients(SearchBroadCastResource broadCastRecipient) {

		List<BroadCastRecipient> resultRecipient = null;
		try {
			QBroadCastRecipient qBroadCastRecipient = QBroadCastRecipient.broadCastRecipient;
			BooleanBuilder where = new BooleanBuilder();
			
			if (broadCastRecipient.getMessageId() != null) {
				where.and(qBroadCastRecipient.broadCast.messageId.eq(Long.valueOf(broadCastRecipient.getMessageId())));
			}
			
			if (broadCastRecipient.getGroupId() != null) {
				where.and(qBroadCastRecipient.group.groupId.eq(Long.valueOf(broadCastRecipient.getGroupId())));
			}

			if ((broadCastRecipient.getGroupIds() != null) && (broadCastRecipient.getGroupIds().size() !=0)) {
				where.and(qBroadCastRecipient.group.groupId.in(broadCastRecipient.getGroupIds()));
			}
			
			if (broadCastRecipient.getUserId() != null) {
				where.and(qBroadCastRecipient.user.userId.eq(Long.valueOf(broadCastRecipient.getUserId())));
			}
			
			if ((broadCastRecipient.getUserIds() != null) && (broadCastRecipient.getUserIds().size() !=0)) {
				where.and(qBroadCastRecipient.user.userId.in(broadCastRecipient.getUserIds()));
			}

			if (where.hasValue()) {
				resultRecipient = (List<BroadCastRecipient>) broadCastRecipientRepository.findAll(where);
			}
			
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return resultRecipient;
	}

	@Override
	public void saveBroadCastRecipient(BroadCastRecipient broadCastRecipient) {
		
		try {
			broadCastRecipientRepository.save(broadCastRecipient);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public void saveBroadCastRecipient(List<BroadCastRecipient> broadCastRecipient) {
		
		try {
			broadCastRecipientRepository.save(broadCastRecipient);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public BroadCast finByMessageId(Long messageId) {

		BroadCast broadCast = null;
		try {
			broadCast = broadCastRepository.findOne(messageId);
			if (null == broadCast) {
				throw new IllegalStateException("message doesnot exist");
			}
		} catch (NumberFormatException numbeFormatEx) {
			throw new IllegalStateException("invalid datatype for id");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return broadCast;
	}
	
	@Override
	public Attachment saveAttachment(Attachment attachment) {
		Attachment savedItem = null;
		try{
			savedItem = attachmentRepository.save(attachment);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return savedItem;
	}
	
	@Override
	public void saveAttachment(List<Attachment> attachments) {
		try{
			attachmentRepository.save(attachments);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public Attachment getAttachment(Long uploadId) {
		Attachment attachment = null;
		try{
			attachment = attachmentRepository.findOne(uploadId);			
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
		return attachment;
	}
	
	@Override
	public List<Attachment> searchQueryAttachments(SearchAttachmentRequestResource searchCriteria) {
		List<Attachment> filteredResult = null;
		try{
			QAttachment queryAttachment = QAttachment.attachment;
			
			BooleanBuilder where = new BooleanBuilder();
			
			if(null != searchCriteria.getQueryId()){
				where.and(queryAttachment.broadCast.messageId.eq(Long.valueOf(searchCriteria.getMessageId())));
			}
			if(null != searchCriteria.getUploadIds()){
				where.and(queryAttachment.uploadId.in(searchCriteria.getUploadIds()));
			}
			
			if(where.hasValue()){
				filteredResult = (List<Attachment>) attachmentRepository.findAll(where);	
			}
		}
		catch(Exception ex){		
			throw new RuntimeException(ex);
		}
		return filteredResult;
	}

	@Override
	public BroadCastResponse saveBroadCastResponse(BroadCastResponse broadCastResponse) {
		BroadCastResponse broadCastResp = null;
		try {
			broadCastResp = broadCastResponseRepository.save(broadCastResponse);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return broadCastResp;
	}

	@Override
	public List<BroadCastResponse> finByBroadCastMessageId(Long messageId) {
		List<BroadCastResponse> BroadCastResponse = null;
		try {
			BroadCastResponse = broadCastResponseRepository.findByBroadCast_MessageId(messageId);
			if (null == BroadCastResponse) {
				throw new IllegalStateException("message doesnot exist");
			}
		} catch (NumberFormatException numbeFormatEx) {
			throw new IllegalStateException("invalid datatype for id");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return BroadCastResponse;
	}

	@Override
	public List<BroadCastResponse> searchBroadCastResponse(SearchBroadCastResponseRequest request) {

		List<BroadCastResponse> result = null;

		try {
			QBroadCastResponse broadCastResponse = QBroadCastResponse.broadCastResponse;
			BooleanBuilder where = new BooleanBuilder();
			if (request.getMessageId() != null) {
				where.and(broadCastResponse.broadCast.messageId.eq(Long.valueOf(request.getMessageId())));
			}
			
			if(request.getUserId() != null){
				where.and(broadCastResponse.responseBy.userId.eq(Long.valueOf(request.getUserId())));
			}

			if (StringUtils.isNotBlank(request.getUserRole())) {
				where.and(broadCastResponse.responseBy.role.any().roleName.eq(request.getUserRole()));
			}

			if (where.hasValue()) {
				result = (List<BroadCastResponse>) broadCastResponseRepository.findAll(where);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return result;
	}

}
