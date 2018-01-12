/**
 * 
 */
package my.mimos.mdc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import my.mimos.mdc.resources.BaseResponseResource;
import my.mimos.mdc.resources.broadcast.BroadCastMessageUpdateRequestResource;
import my.mimos.mdc.resources.broadcast.BroadCastDetailResource;
import my.mimos.mdc.resources.broadcast.BroadCastMessageRequestResource;
import my.mimos.mdc.resources.broadcast.BroadCastMessageResponseResource;
import my.mimos.mdc.resources.broadcast.BroadCastResponseRequestResource;
import my.mimos.mdc.resources.broadcast.SendBroadCastResponseResource;
import my.mimos.mdc.resources.query.AttachmentResponseResource;
import my.mimos.mdc.resources.query.MediaUploadResponseResource;
import my.mimos.mdc.service.BroadCastService;

/**
 * @author krishna.redabotu
 *
 */

@RestController
@RequestMapping(value="/")
public class BroadCastController {

	@Autowired
	BroadCastService broadCastService;

	@RequestMapping(value = "admin/broadcast/message/send", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<SendBroadCastResponseResource> broadCastMessage(@Valid @RequestBody BroadCastMessageRequestResource request) {
		SendBroadCastResponseResource response = broadCastService.broadCastMessage(request);
		return new ResponseEntity<SendBroadCastResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "admin/broadcast/message/update", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<BaseResponseResource> addRecipient(@Valid @RequestBody BroadCastMessageUpdateRequestResource request) {
		BaseResponseResource response = broadCastService.broadCastMessageUpdate(request);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "broadcast/message/response", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BroadCastMessageResponseResource> broadCastMessageReply(@Valid @RequestBody BroadCastResponseRequestResource request) {
		BroadCastMessageResponseResource response = broadCastService.broadCastMessageResponse(request);
		return new ResponseEntity<BroadCastMessageResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/broadcast/message/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<BroadCastDetailResource> getMessage(@Valid @PathVariable("id") Long messageId) {
		BroadCastDetailResource response = broadCastService.getMessage(messageId);
		return new ResponseEntity<BroadCastDetailResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/broadcast/attachment/upload",
			produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	public ResponseEntity<MediaUploadResponseResource> uploadMedia(
			@RequestParam("file") MultipartFile multipartFile,
			@RequestParam("title") String title) throws MultipartException {
		MediaUploadResponseResource response = broadCastService.uploadMedia(multipartFile, title);
		return new ResponseEntity<MediaUploadResponseResource>(response, HttpStatus.OK);
	} 
	
	@RequestMapping(value = "/broadcast/attachment/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<AttachmentResponseResource> getAttachment(@Valid @PathVariable("id") Long uploadId){
		AttachmentResponseResource response= broadCastService.getAttachment(uploadId);
		return new ResponseEntity<AttachmentResponseResource>(response, HttpStatus.OK);	
	}

}
