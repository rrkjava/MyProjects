package my.mimos.mdc.command.handler;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.querydsl.core.BooleanBuilder;

/*import my.mimos.mdc.domain.entity.QQuery;*/

import my.mimos.mdc.command.QueryCommand;
import my.mimos.mdc.domain.entity.Acknowledgment;
import my.mimos.mdc.domain.entity.Attachment;
import my.mimos.mdc.domain.entity.QAcknowledgment;
import my.mimos.mdc.domain.entity.QAttachment;
import my.mimos.mdc.domain.entity.QQuery;
import my.mimos.mdc.domain.entity.QRecipient;
import my.mimos.mdc.domain.entity.Query;
import my.mimos.mdc.domain.entity.Recipient;
import my.mimos.mdc.domain.entity.UrgencyLevel;
import my.mimos.mdc.domain.repository.AckRepository;
import my.mimos.mdc.domain.repository.AttachmentRepository;
import my.mimos.mdc.domain.repository.QueryCommentRepository;
import my.mimos.mdc.domain.repository.QueryRepository;
import my.mimos.mdc.domain.repository.RecipientRepository;
import my.mimos.mdc.domain.repository.ResponseRepository;
import my.mimos.mdc.domain.repository.UrgencyLevelRepository;
import my.mimos.mdc.resources.query.SearchAckRequestResource;
import my.mimos.mdc.resources.query.SearchAttachmentRequestResource;
import my.mimos.mdc.resources.query.SearchQueryRequestResource;
import my.mimos.mdc.resources.query.SearchRecipientRequestResource;
import my.mimos.mdc.utils.DateUtil;
import my.mimos.mdc.utils.MdcConstants;

@Component
public class QueryCommandHandler implements QueryCommand{
	
	@Autowired
	QueryRepository queryRepository;
	
	@Autowired
	DateUtil dateUtil;
	
	@Autowired
	RecipientRepository recipientRepository;
	
	@Autowired
	ResponseRepository responseRepository;
	
	@Autowired
	QueryCommentRepository commentRepository;
	
	@Autowired
	UrgencyLevelRepository urgencyLevelRepository;
	
	@Autowired
	AttachmentRepository attachmentRepository;
	
	@Autowired
	AckRepository ackRepository;

	@Override
	public Query saveQuery(Query query) {
		Query savedQuery  = null;
		try{
			savedQuery  = queryRepository.save(query);			
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
		return savedQuery;
	}
	
	@Override
	public List<Query> searchQuery(SearchQueryRequestResource searchCriteria) {
		List<Query> filteredQueries = null;
		try{
			QQuery query = QQuery.query;
			
			BooleanBuilder where = new BooleanBuilder();
			
			if(StringUtils.isNotBlank(searchCriteria.getQueryId())){
				where.and(query.queryId.eq(Long.valueOf(searchCriteria.getQueryId())));
			}
			if(StringUtils.isNotBlank(searchCriteria.getSubject())){
				where.and(query.subject.like(searchCriteria.getSubject()));
			}
			if(StringUtils.isNotBlank(searchCriteria.getApprovalStatus())){
				where.and(query.queryStatus.eq(searchCriteria.getApprovalStatus()));
			}
			if(StringUtils.isNotBlank(searchCriteria.getFromDate())){
				Date fromDate = dateUtil.stringToDate(searchCriteria.getFromDate());
				where.and(query.createdDate.after(fromDate));
			}
			if(StringUtils.isNotBlank(searchCriteria.getToDate())){
				Date toDate = dateUtil.stringToDate(searchCriteria.getToDate());
				where.and(query.createdDate.before(toDate));
			}
			if(StringUtils.isNotBlank(searchCriteria.getDeptId())){
				where.and(query.sentBy.department.deptId.eq(Long.valueOf(searchCriteria.getDeptId())));
			}
			if(StringUtils.isNotBlank(searchCriteria.getSentBy())){
				where.and(query.sentBy.userId.eq(Long.valueOf(searchCriteria.getSentBy())));
			}
			if(where.hasValue()){
				filteredQueries = (List<Query>) queryRepository.findAll(where);	
			}/*else{
				filteredQueries = queryRepository.findAll();
			}*/ // this else condition displays all queries if there is no search criteria
		}
		catch(Exception ex){		
			throw new RuntimeException(ex);
		}
		return filteredQueries;
	}
	
	public Query findByQueryId(Long queryId){
		Query query = null;
		try{
			query = queryRepository.findOne(Long.valueOf(queryId));
			if(null == query){
				throw new IllegalStateException("query doesnot exist");
			}
		}
		catch(NumberFormatException numbeFormatEx){
			throw new IllegalStateException("invalid datatype for id");
		}		
		catch(Exception ex){
			throw new RuntimeException(ex);
		}
		return query;
	}

	@Override
	public void addRecipients(List<Recipient> recipients) {
		try{
			recipientRepository.save(recipients);			
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}		
	}
	
	@Override
	public List<Recipient> searchQueryRecipient(SearchRecipientRequestResource searchCriteria) {
		List<Recipient> filteredResult = null;
		try{
			QRecipient queryRecipient = QRecipient.recipient1;
			
			BooleanBuilder where = new BooleanBuilder();
			
			if(null != searchCriteria.getQueryId()){
				where.and(queryRecipient.query.queryId.eq(Long.valueOf(searchCriteria.getQueryId())));
			}
			if(null != searchCriteria.getUserId()){
				where.and(queryRecipient.recipient.userId.eq(Long.valueOf(searchCriteria.getUserId())));
			}
			if(null != searchCriteria.getDeptId()){
				where.and(queryRecipient.recipient.department.deptId.eq(Long.valueOf(searchCriteria.getDeptId())));
			}
			if(null != searchCriteria.getGroupId()){
				where.and(queryRecipient.group.groupId.eq(Long.valueOf(searchCriteria.getGroupId())));
			}
			if(null != searchCriteria.getQueryIds() && searchCriteria.getQueryIds().size()!= 0){
				where.and(queryRecipient.query.queryId.in(searchCriteria.getQueryIds()));
			}
			if(null != searchCriteria.getUserIds() && searchCriteria.getUserIds().size()!= 0){
				where.and(queryRecipient.recipient.userId.in(searchCriteria.getUserIds()));
			}
			if(null != searchCriteria.getGroupIds() && searchCriteria.getGroupIds().size()!= 0){
				where.and(queryRecipient.group.groupId.in(searchCriteria.getGroupIds()));
			}
			if(null != searchCriteria.getDeptIds() && searchCriteria.getDeptIds().size()!= 0){
				where.and(queryRecipient.recipient.department.deptId.in(searchCriteria.getDeptIds()));
			}
			if(StringUtils.isNotBlank(searchCriteria.getRecipientType())){
				where.and(queryRecipient.recipientType.equalsIgnoreCase(searchCriteria.getRecipientType()));
			}
			if(StringUtils.isNotBlank(searchCriteria.getRole())){
				where.and(queryRecipient.recipient.role.any().roleName.like(searchCriteria.getRole()));
			}
			if(null != searchCriteria.getRoles() && !searchCriteria.getRoles().isEmpty() ){
				where.and(queryRecipient.recipient.role.any().roleName.in(searchCriteria.getRoles()));
			}
			if(searchCriteria.isFilterAdminAndfocalOfDept()){
				where.and(queryRecipient.recipient.role.any().roleName.like(MdcConstants.ROLE_ADMIN)
						.or((queryRecipient.recipient.role.any().roleName.like(MdcConstants.ROLE_FOCAL_SUPERVISOR)
								.or(queryRecipient.recipient.role.any().roleName.like(MdcConstants.ROLE_FOCAL)))
									.and(queryRecipient.recipient.department.deptId.in(searchCriteria.getFocalDeptId())) 
									));
			}
			
			if(where.hasValue()){
				filteredResult = (List<Recipient>) recipientRepository.findAll(where);	
			}/*else{
				filteredResult = recipientRepository.findAll();
			}*/ // this else condition displays all queries if there is no search criteria
		}
		catch(Exception ex){		
			throw new RuntimeException(ex);
		}
		return filteredResult;
	}

	@Override
	public Recipient getRecipient(String recipientId) {
		Recipient recipient;
		try{
			recipient = recipientRepository.findOne(Long.valueOf(recipientId));
			if(null == recipient){
				throw new IllegalStateException("recipient doesnot exist");
			}
		}
		catch(NumberFormatException numbeFormatEx){
			throw new IllegalStateException("invalid datatype for id");
		}		
		catch(Exception ex){
			throw new RuntimeException(ex);
		}
		return recipient;
	}

	@Override
	public void removeRecipients(List<Recipient> existingRecipientList) {
		try{
			recipientRepository.delete(existingRecipientList);			
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
		
	}

	@Override
	public UrgencyLevel addUrgencyLevel(UrgencyLevel urgencyLevel) {
		UrgencyLevel addedItem = null;
		try{
			addedItem = urgencyLevelRepository.save(urgencyLevel);			
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
		return addedItem;
	}
	
	@Override
	public List<UrgencyLevel> listAllUrgencyLevel() {
		List<UrgencyLevel> allItems = null;
		try{
			allItems = urgencyLevelRepository.findAll();			
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
		return allItems;
	}
	
	@Override
	public UrgencyLevel getUrgencyLevelById(Long id) {
		UrgencyLevel item = null;
		try{
			item = urgencyLevelRepository.findOne(id);			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return item;
	}
	
	@Override
	public void removeUrgencyLevel(Long id) {
		try{
			urgencyLevelRepository.delete(id);			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
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
	public void removeQuery(Long queryId) {
		try{
			queryRepository.delete(queryId);			
		}catch(Exception ex){
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
	public void removeAttachment(Long uploadId) {
		try{
			attachmentRepository.delete(uploadId);			
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}				
	}

	@Override
	public UrgencyLevel getUrgencyLevelByName(String name) {
		UrgencyLevel item = null;
		try{
			item = urgencyLevelRepository.findByUrgencyLevel(name);			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return item;
	}

	@Override
	public Acknowledgment saveAck(Acknowledgment ack) {
		Acknowledgment saved = new Acknowledgment();
		try{
			saved = ackRepository.save(ack);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}		
		return saved;
	}
	
	@Override
	public void saveAcks(List<Acknowledgment> ack) {
		try{
			ackRepository.save(ack);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}		
	}

	public List<Acknowledgment> searchQueryAcks(SearchAckRequestResource searchCriteria) {
		List<Acknowledgment> filteredResult = null;
		try{
			QAcknowledgment queryAck = QAcknowledgment.acknowledgment;
			
			BooleanBuilder where = new BooleanBuilder();
			
			if(null != searchCriteria.getQueryId()){
				where.and(queryAck.query.queryId.eq(Long.valueOf(searchCriteria.getQueryId())));
			}
			if(null != searchCriteria.getUserId()){
				where.and(queryAck.ackBy.userId.eq(Long.valueOf(searchCriteria.getUserId())));
			}
			if(StringUtils.isNotBlank(searchCriteria.getUserRole())){
				where.and(queryAck.ackBy.role.any().roleName.like(searchCriteria.getUserRole()));
			}
			if(null != searchCriteria.getUserDept()){
				where.and(queryAck.ackBy.department.deptId.eq(Long.valueOf(searchCriteria.getUserDept())));
			}
			if(null != searchCriteria.getAckIds() && searchCriteria.getAckIds().size()!= 0){
				where.and(queryAck.ackId.in(searchCriteria.getAckIds()));
			}
			
			if(where.hasValue()){
				filteredResult = (List<Acknowledgment>) ackRepository.findAll(where);	
			}
		}
		catch(Exception ex){		
			throw new RuntimeException(ex);
		}
		return filteredResult;
	}
	
	public List<Attachment> searchQueryAttachments(SearchAttachmentRequestResource searchCriteria) {
		List<Attachment> filteredResult = null;
		try{
			QAttachment queryAttachment = QAttachment.attachment;
			
			BooleanBuilder where = new BooleanBuilder();
			
			if(null != searchCriteria.getQueryId()){
				where.and(queryAttachment.query.queryId.eq(Long.valueOf(searchCriteria.getQueryId())));
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

	
}
