package my.mimos.mdc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import my.mimos.mdc.resources.query.AckRequestResource;
import my.mimos.mdc.resources.query.AttachmentResponseResource;
import my.mimos.mdc.resources.query.ListRecipientResponseResource;
import my.mimos.mdc.resources.query.MediaUploadResponseResource;
import my.mimos.mdc.resources.query.QueryDetailResource;
import my.mimos.mdc.resources.query.QueryInboxRequestResource;
import my.mimos.mdc.resources.query.QueryInboxResponseResource;
import my.mimos.mdc.resources.query.RecipientsRequestResource;
import my.mimos.mdc.resources.query.SearchQueryRequestResource;
import my.mimos.mdc.resources.query.SearchQueryResponseResource;
import my.mimos.mdc.resources.query.SearchRecipientRequestResource;
import my.mimos.mdc.resources.query.SearchRecipientResponseResource;
import my.mimos.mdc.resources.query.SendQueryRequestResource;
import my.mimos.mdc.resources.query.SendQueryResponseResource;
import my.mimos.mdc.resources.query.UpdateQueryRequestResource;
import my.mimos.mdc.resources.query.UrgencyFlagRequestResource;
import my.mimos.mdc.service.QueryService;
/**
 * 
 * @author beaula.fernandez
 *
 */

@RestController
@RequestMapping(value="/")
public class QueryController {
	
	@Autowired
	QueryService queryService;
	
	@RequestMapping(value="/query/send",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<SendQueryResponseResource> sendQuery(@Valid @RequestBody SendQueryRequestResource query, HttpServletRequest request){		
		System.out.println("Caller IP : " +  request.getRemoteAddr());
		SendQueryResponseResource response = queryService.sendQuery(query);
		return new ResponseEntity<SendQueryResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/query/update",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResponseResource> updateQuery(@Valid @RequestBody UpdateQueryRequestResource request){		
		BaseResponseResource response = queryService.updateQuery(request);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/query/search",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<SearchQueryResponseResource> searchQuery(@Valid @RequestBody SearchQueryRequestResource searchCriteria){		
		SearchQueryResponseResource response = queryService.searchQuery(searchCriteria);
		return new ResponseEntity<SearchQueryResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/query/remove/{id}",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResponseResource> removeQuery(@Valid @PathVariable("id") Long queryId){		
		BaseResponseResource response = queryService.removeQuery(queryId);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/query/attachment/upload",
			produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
    public ResponseEntity<MediaUploadResponseResource> uploadMedia(
    		@RequestParam("file") MultipartFile multipartFile,
    		@RequestParam("title") String title)throws MultipartException{
		MediaUploadResponseResource response = queryService.uploadMedia(multipartFile,title,null,null);
		return new ResponseEntity<MediaUploadResponseResource>(response, HttpStatus.OK);
	} 
	
	@RequestMapping(value = "/query/attachment/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<AttachmentResponseResource> getAttachment(@Valid @PathVariable("id") Long uploadId){
		AttachmentResponseResource response= queryService.getAttachment(uploadId);
		return new ResponseEntity<AttachmentResponseResource>(response, HttpStatus.OK);	
	}
	
	@RequestMapping(value = "/query/attachment/remove/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<BaseResponseResource> removeAttachment(@Valid @PathVariable("id") Long uploadId){
		BaseResponseResource response= queryService.removeAttachment(uploadId);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);	
	}
	
	@RequestMapping(value="/query/inbox",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<QueryInboxResponseResource> inbox(@Valid @RequestBody QueryInboxRequestResource request){		
		QueryInboxResponseResource response = queryService.inbox();
		return new ResponseEntity<QueryInboxResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/query/inbox/{id}",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<?> query(@Valid @PathVariable("id") Long queryId){		
		QueryDetailResource response = queryService.getQuery(queryId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/query/forward",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResponseResource> forward(@Valid @RequestBody RecipientsRequestResource request){		
		BaseResponseResource response = queryService.forward(request);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);
	}	
	
	@RequestMapping(value="/query/recipients/search",			 
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<SearchRecipientResponseResource> searchQueryRecipients(@Valid @RequestBody SearchRecipientRequestResource request){		
		SearchRecipientResponseResource response = queryService.searchRecipients(request);
		return new ResponseEntity<SearchRecipientResponseResource>(response, HttpStatus.OK);
	}	
	
	@RequestMapping(value="/query/recipients/list/{id}",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<ListRecipientResponseResource> listQueryRecipients(@Valid @PathVariable("id") Long queryId){		
		ListRecipientResponseResource response = queryService.listQueryRecipients(queryId);
		return new ResponseEntity<ListRecipientResponseResource>(response, HttpStatus.OK);
	}	
	
	@RequestMapping(value="/query/recipients/remove",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResponseResource> removeRecipients(@Valid @RequestBody RecipientsRequestResource request){		
		BaseResponseResource response = queryService.removeQueryRecipients(request);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/query/urgencyflag",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResponseResource> setUrgencyFlag(@Valid @RequestBody UrgencyFlagRequestResource request){		
		BaseResponseResource response = queryService.updateUrgencyFlag(request);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/query/acknowledge/accept",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResponseResource> acceptAck(@Valid @RequestBody AckRequestResource request){		
		BaseResponseResource response = queryService.acknowledgeQuery(request,true);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/query/acknowledge/reject",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResponseResource> rejectAck(@Valid @RequestBody AckRequestResource request){		
		BaseResponseResource response = queryService.acknowledgeQuery(request,false);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);
	}
	
}
