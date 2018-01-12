package my.mimos.mdc.service;

import org.springframework.web.multipart.MultipartFile;

import my.mimos.mdc.resources.BaseResponseResource;
import my.mimos.mdc.resources.query.AckRequestResource;
import my.mimos.mdc.resources.query.AckResponseResource;
import my.mimos.mdc.resources.query.AddUrgencyRequestResource;
import my.mimos.mdc.resources.query.AttachmentResponseResource;
import my.mimos.mdc.resources.query.ListRecipientResponseResource;
import my.mimos.mdc.resources.query.MediaUploadResponseResource;
import my.mimos.mdc.resources.query.QueryDetailResource;
import my.mimos.mdc.resources.query.QueryInboxResponseResource;
import my.mimos.mdc.resources.query.RecipientsRequestResource;
import my.mimos.mdc.resources.query.SearchQueryRequestResource;
import my.mimos.mdc.resources.query.SearchQueryResponseResource;
import my.mimos.mdc.resources.query.SearchRecipientRequestResource;
import my.mimos.mdc.resources.query.SearchRecipientResponseResource;
import my.mimos.mdc.resources.query.SendQueryRequestResource;
import my.mimos.mdc.resources.query.SendQueryResponseResource;
import my.mimos.mdc.resources.query.UpdateQueryRequestResource;
import my.mimos.mdc.resources.query.UpdateUrgencyRequestResource;
import my.mimos.mdc.resources.query.UrgencyFlagRequestResource;
import my.mimos.mdc.resources.query.UrgencyLevelResponseResource;
import my.mimos.mdc.resources.query.UrgencyLevelsResponseResource;
/**
 * 
 * @author beaula.fernandez
 *
 */

public interface QueryService {
	
	public SendQueryResponseResource sendQuery(SendQueryRequestResource query);

	public SearchQueryResponseResource searchQuery(SearchQueryRequestResource searchCriteria);
	
	public BaseResponseResource forward(RecipientsRequestResource request);

	public ListRecipientResponseResource listQueryRecipients(Long queryId);

	public BaseResponseResource removeQueryRecipients(RecipientsRequestResource request);

	public SearchRecipientResponseResource searchRecipients(SearchRecipientRequestResource request);

	public QueryInboxResponseResource inbox();

	public QueryDetailResource getQuery(Long queryId);

	public BaseResponseResource updateQuery(UpdateQueryRequestResource request);

	public BaseResponseResource updateUrgencyFlag(UrgencyFlagRequestResource request);

	public UrgencyLevelResponseResource addUrgencyLevel(AddUrgencyRequestResource request);

	public UrgencyLevelResponseResource getUrgencyLevel(Long id);

	public BaseResponseResource removeUrgencyLevel(String id);

	public UrgencyLevelsResponseResource listAllUrgencyLevel();

	public BaseResponseResource updateUrgencyLevel(UpdateUrgencyRequestResource request);

	public MediaUploadResponseResource uploadMedia(MultipartFile multipartFile, String title, Long queryId,Long responseId);

	public BaseResponseResource removeQuery(Long queryId);

	public AttachmentResponseResource getAttachment(Long uploadId);

	public BaseResponseResource removeAttachment(Long uploadId);

	public AckResponseResource acknowledgeQuery(AckRequestResource request, boolean accept);

	
}
