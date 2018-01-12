package my.mimos.mdc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import my.mimos.mdc.resources.BaseResponseResource;
import my.mimos.mdc.resources.query.QueryCommentRequestResource;
import my.mimos.mdc.resources.query.QueryCommentResponseResource;
import my.mimos.mdc.resources.query.ResponseApprovalRequestResource;
import my.mimos.mdc.resources.query.ResponseApprovalResponseResource;
import my.mimos.mdc.resources.query.SearchCommentRequestResource;
import my.mimos.mdc.resources.query.SearchCommentResponseResource;
import my.mimos.mdc.resources.query.SearchResponsesRequestResource;
import my.mimos.mdc.resources.query.SearchResponsesResource;
import my.mimos.mdc.resources.query.SendResponseRequestResource;
import my.mimos.mdc.resources.query.SendResponseResource;
import my.mimos.mdc.resources.query.UpdateResponseRequestResource;
import my.mimos.mdc.service.ResponseService;

/**
 * 
 * @author beaula.fernandez
 *
 */

@RestController
@RequestMapping(value="/")
public class ResponseController {
	
	@Autowired
	ResponseService responseService;
	
	@RequestMapping(value="/query/response/send",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<SendResponseResource> sendResponseToQuery(@Valid @RequestBody SendResponseRequestResource request){		
		SendResponseResource response = responseService.sendResponse(request);
		return new ResponseEntity<SendResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/query/response/{id}",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<SendResponseResource> getQueryResponse(@Valid @PathVariable("id") String responseId){		
		SendResponseResource response = responseService.getResponse(responseId);
		return new ResponseEntity<SendResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/query/response/search",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<SearchResponsesResource> searchQueryResponses(@Valid @RequestBody SearchResponsesRequestResource request){		
		SearchResponsesResource response = responseService.searchResponses(request);
		return new ResponseEntity<SearchResponsesResource>(response, HttpStatus.OK);
	}	
	
	@RequestMapping(value="/query/response/update",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResponseResource> editResponse(@Valid @RequestBody UpdateResponseRequestResource request){		
		BaseResponseResource response = responseService.updateResponse(request);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);
	}	
	
	@RequestMapping(value="/query/comment/add",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<QueryCommentResponseResource> comment(@Valid @RequestBody QueryCommentRequestResource request){		
		QueryCommentResponseResource response = responseService.addCommentToQuery(request);
		return new ResponseEntity<QueryCommentResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/query/comment/{id}",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<QueryCommentResponseResource> getComment(@Valid @PathVariable("id") String commentId){		
		QueryCommentResponseResource response = responseService.getComment(commentId);
		return new ResponseEntity<QueryCommentResponseResource>(response, HttpStatus.OK);
	}	
	
	@RequestMapping(value="/query/comment/remove/{id}",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<BaseResponseResource> removeComment(@Valid @PathVariable("id") String commentId){		
		BaseResponseResource response = responseService.removeComment(commentId);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);
	}	
	
	@RequestMapping(value="/query/comment/search",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<SearchCommentResponseResource> searchComments(@Valid @RequestBody SearchCommentRequestResource request){		
		SearchCommentResponseResource response = responseService.searchComments(request);
		return new ResponseEntity<SearchCommentResponseResource>(response, HttpStatus.OK);
	}		

	@RequestMapping(value="/query/response/approve",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<ResponseApprovalResponseResource> approveResponse(@Valid @RequestBody ResponseApprovalRequestResource request){
		ResponseApprovalResponseResource response = responseService.approveResponse(request);
		return new ResponseEntity<ResponseApprovalResponseResource>(response, HttpStatus.OK);
	}	
	
	@RequestMapping(value="/query/sendFinalResponse/{id}",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.GET})	
	@ResponseBody
	public ResponseEntity<BaseResponseResource> sendFinalResponse(@Valid @PathVariable("id") Long responseId){		
		BaseResponseResource response = responseService.sendFinalResponse(responseId);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);
	}	
	

}
