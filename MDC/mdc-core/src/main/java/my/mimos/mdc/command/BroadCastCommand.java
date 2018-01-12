/**
 * 
 */
package my.mimos.mdc.command;

import java.util.List;

import my.mimos.mdc.domain.entity.Attachment;
import my.mimos.mdc.domain.entity.BroadCast;
import my.mimos.mdc.domain.entity.BroadCastRecipient;
import my.mimos.mdc.domain.entity.BroadCastResponse;
import my.mimos.mdc.resources.broadcast.SearchBroadCastResource;
import my.mimos.mdc.resources.broadcast.SearchBroadCastResponseRequest;
import my.mimos.mdc.resources.query.SearchAttachmentRequestResource;

/**
 * @author krishna.redabotu
 *
 */

public interface BroadCastCommand {

	public BroadCast saveBroadCastMessage(BroadCast broadCast);
	public List<BroadCastRecipient> SearchBroadCastRecipients(SearchBroadCastResource broadCast);
	public void saveBroadCastRecipient(BroadCastRecipient broadCastRecipient);
	public void saveBroadCastRecipient(List<BroadCastRecipient> broadCastRecipient);
	public BroadCast finByMessageId(Long messageId);
	public Attachment saveAttachment(Attachment attachment);
	public void saveAttachment(List<Attachment> attachments);
	public Attachment getAttachment(Long uploadId);
	public List<Attachment> searchQueryAttachments(SearchAttachmentRequestResource searchCriteria);
	public BroadCastResponse saveBroadCastResponse(BroadCastResponse broadCastResponse);
	public List<BroadCastResponse> finByBroadCastMessageId(Long messageId);
	public List<BroadCastResponse> searchBroadCastResponse(SearchBroadCastResponseRequest SearchBroadCastResponseRequest);
}
