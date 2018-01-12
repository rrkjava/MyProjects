package my.mimos.mdc.command;

import java.util.List;

import my.mimos.mdc.domain.entity.Comment;
import my.mimos.mdc.domain.entity.Response;
import my.mimos.mdc.domain.entity.ResponseApproval;
import my.mimos.mdc.resources.query.SearchCommentRequestResource;
import my.mimos.mdc.resources.query.SearchResponsesRequestResource;

/**
 * 
 * @author beaula.fernandez
 *
 */

public interface ResponseCommand {
	
	public Response saveResponse(Response responseToSend);

	public Response findByResponseId(Long responseId);

	public void removeResponse(Long responseId);
	
	public List<Response> searchQueryResponse(SearchResponsesRequestResource request);

	public Comment addComment(Comment comment);
	
	public void addComment(List<Comment> comment);

	public Comment getComment(String commentId);
	
	public void removeComment(Long commentId);

	public List<Comment> searchQueryComments(SearchCommentRequestResource searchCriteria);

	public void saveResponse(List<Response> responses);

	public ResponseApproval saveResponseApproval(ResponseApproval responseApproval);

}
