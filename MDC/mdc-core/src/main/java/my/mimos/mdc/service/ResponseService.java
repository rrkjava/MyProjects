package my.mimos.mdc.service;

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
/**
 * 
 * @author beaula.fernandez
 *
 */

public interface ResponseService {
	
	public SendResponseResource sendResponse(SendResponseRequestResource request);

	public SendResponseResource getResponse(String responseId);

	public BaseResponseResource removeResponseFromQuery(String responseId);

	public SearchResponsesResource searchResponses(SearchResponsesRequestResource request);
	
	public QueryCommentResponseResource addCommentToQuery(QueryCommentRequestResource request);

	public QueryCommentResponseResource getComment(String commentId);

	public BaseResponseResource removeComment(String commentId);

	public SearchCommentResponseResource searchComments(SearchCommentRequestResource request);

	public BaseResponseResource updateResponse(UpdateResponseRequestResource request);

	public ResponseApprovalResponseResource approveResponse(ResponseApprovalRequestResource request);

	public BaseResponseResource sendFinalResponse(Long responseId);

}
