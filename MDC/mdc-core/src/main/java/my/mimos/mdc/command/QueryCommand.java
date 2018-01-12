package my.mimos.mdc.command;

import java.util.List;

import org.springframework.stereotype.Component;

import my.mimos.mdc.domain.entity.Acknowledgment;
import my.mimos.mdc.domain.entity.Attachment;
import my.mimos.mdc.domain.entity.Query;
import my.mimos.mdc.domain.entity.Recipient;
import my.mimos.mdc.domain.entity.UrgencyLevel;
import my.mimos.mdc.resources.query.SearchAckRequestResource;
import my.mimos.mdc.resources.query.SearchAttachmentRequestResource;
import my.mimos.mdc.resources.query.SearchQueryRequestResource;
import my.mimos.mdc.resources.query.SearchRecipientRequestResource;

@Component
public interface QueryCommand {
	
	public Query saveQuery(Query query);

	public List<Query> searchQuery(SearchQueryRequestResource searchCriteria);
	
	public Query findByQueryId(Long queryId);
	
	public void removeQuery(Long queryId);

	public void addRecipients(List<Recipient>  recipients);

	public List<Recipient> searchQueryRecipient(SearchRecipientRequestResource searchCriteria);

	public Recipient getRecipient(String recipientId);

	public void removeRecipients(List<Recipient> existingRecipientList);

	public UrgencyLevel addUrgencyLevel(UrgencyLevel urgencyLevel);

	public List<UrgencyLevel> listAllUrgencyLevel();

	public UrgencyLevel getUrgencyLevelById(Long id);
	
	public UrgencyLevel getUrgencyLevelByName(String name);

	public void removeUrgencyLevel(Long id);

	public Attachment saveAttachment(Attachment attachment);
	
	public void saveAttachment(List<Attachment> attachments);
	
	public List<Attachment> searchQueryAttachments(SearchAttachmentRequestResource searchCriteria);

	public Attachment getAttachment(Long uploadId);

	public void removeAttachment(Long uploadId);

	public Acknowledgment saveAck(Acknowledgment ack);
	
	public List<Acknowledgment> searchQueryAcks(SearchAckRequestResource searchCriteria);

	public void saveAcks(List<Acknowledgment> ack);
	
	
}
