/**
 * 
 */
package my.mimos.mdc.service;

import org.springframework.web.multipart.MultipartFile;

import my.mimos.mdc.resources.BaseResponseResource;
import my.mimos.mdc.resources.broadcast.BroadCastMessageUpdateRequestResource;
import my.mimos.mdc.resources.broadcast.BroadCastResponseRequestResource;
import my.mimos.mdc.resources.broadcast.SendBroadCastResponseResource;
import my.mimos.mdc.resources.broadcast.BroadCastDetailResource;
import my.mimos.mdc.resources.broadcast.BroadCastMessageRequestResource;
import my.mimos.mdc.resources.broadcast.BroadCastMessageResponseResource;
import my.mimos.mdc.resources.query.AttachmentResponseResource;
import my.mimos.mdc.resources.query.MediaUploadResponseResource;

/**
 * @author krishna.redabotu
 *
 */


public interface BroadCastService {

	public SendBroadCastResponseResource broadCastMessage(BroadCastMessageRequestResource request);
	public BaseResponseResource broadCastMessageUpdate(BroadCastMessageUpdateRequestResource request);
	public BroadCastDetailResource getMessage(Long messageId);
	public MediaUploadResponseResource uploadMedia(MultipartFile multipartFile, String title);
	public AttachmentResponseResource getAttachment(Long uploadId);
	public BroadCastMessageResponseResource broadCastMessageResponse(BroadCastResponseRequestResource request);
}
