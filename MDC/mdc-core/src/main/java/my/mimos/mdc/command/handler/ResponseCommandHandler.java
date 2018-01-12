package my.mimos.mdc.command.handler;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.querydsl.core.BooleanBuilder;

import my.mimos.mdc.command.ResponseCommand;
import my.mimos.mdc.domain.entity.Comment;
import my.mimos.mdc.domain.entity.QComment;
import my.mimos.mdc.domain.entity.QResponse;
import my.mimos.mdc.domain.entity.Response;
import my.mimos.mdc.domain.entity.ResponseApproval;
import my.mimos.mdc.domain.repository.QueryCommentRepository;
import my.mimos.mdc.domain.repository.ResponseApprovalRepository;
import my.mimos.mdc.domain.repository.ResponseRepository;
import my.mimos.mdc.resources.query.SearchCommentRequestResource;
import my.mimos.mdc.resources.query.SearchResponsesRequestResource;
import my.mimos.mdc.utils.DateUtil;
import my.mimos.mdc.utils.MdcConstants;

/**
 * 
 * @author beaula.fernandez
 *
 */

@Component
public class ResponseCommandHandler implements ResponseCommand{
	
	@Autowired
	DateUtil dateUtil;	
	
	@Autowired
	ResponseRepository responseRepository;
	
	@Autowired
	QueryCommentRepository commentRepository;
	
	@Autowired
	ResponseApprovalRepository responseApprovalRepository;


	@Override
	public Response saveResponse(Response responseToSend) {
		Response response = null;
		try{
			response =  responseRepository.save(responseToSend);			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return response;
	}
	
	@Override
	public void saveResponse(List<Response> responses){
		try{
			responses =  responseRepository.save(responses);			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Response findByResponseId(Long responseId) {
		Response response = null;
		try{
			response =  responseRepository.findOne(responseId);		
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return response;
	}

	@Override
	public void removeResponse(Long responseId) {
		try{
			responseRepository.delete(responseId);			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		
	}

	@Override
	public List<Response> searchQueryResponse(SearchResponsesRequestResource searchCriteria) {
		List<Response> filteredResult = null;
		try{
			QResponse queryResponse = QResponse.response;
			
			BooleanBuilder where = new BooleanBuilder();
			
			if(null != searchCriteria.getQueryId()){
				where.and(queryResponse.query.queryId.eq(Long.valueOf(searchCriteria.getQueryId())));
			}
			if(null != searchCriteria.getSenderId()){
				where.and(queryResponse.responseBy.userId.eq(searchCriteria.getSenderId()));
			}
			if(null != searchCriteria.getResponseId()){
				where.and(queryResponse.responseId.eq(searchCriteria.getResponseId()));
			}
			if(null != searchCriteria.getQueryIds() && !searchCriteria.getQueryIds().isEmpty()){
				where.and(queryResponse.query.queryId.in(searchCriteria.getQueryIds()));
			}
			if(null != searchCriteria.getResponseIds() && !searchCriteria.getResponseIds().isEmpty()){
				where.and(queryResponse.responseId.in(searchCriteria.getResponseIds()));
			}
			if(null != searchCriteria.getUserIds() && !searchCriteria.getUserIds().isEmpty()){
				where.and(queryResponse.responseBy.userId.in(searchCriteria.getUserIds()));
			}
			if(StringUtils.isNotBlank(searchCriteria.getUserRole())){
				where.and(queryResponse.responseBy.role.any().roleName.like(searchCriteria.getUserRole()));
			}
			if(null != searchCriteria.getUserRoles() && !searchCriteria.getUserRoles().isEmpty()){
				where.and(queryResponse.responseBy.role.any().roleName.in(searchCriteria.getUserRoles()));
			}
			if(null != searchCriteria.getUserDept()){
				where.and(queryResponse.responseBy.department.deptId.eq(Long.valueOf(searchCriteria.getUserDept())));
			}
			if(null != searchCriteria.getUserDepts() && !searchCriteria.getUserDepts().isEmpty()){
				where.and(queryResponse.responseBy.department.deptId.in(searchCriteria.getUserDepts()));
			}
			if(StringUtils.isNotBlank(searchCriteria.getDeptName())){
				where.and(queryResponse.responseBy.department.deptName.like(searchCriteria.getDeptName()));
			}
			if(null != searchCriteria.getDeptNames() && !searchCriteria.getDeptNames().isEmpty()){
				where.and(queryResponse.responseBy.department.deptName.in(searchCriteria.getDeptNames()));
			}
			if(null != searchCriteria.getRoleId()){
				where.and(queryResponse.responseBy.role.any().roleId.eq(Long.valueOf(searchCriteria.getRoleId())));
			}
			if(null != searchCriteria.getRoleIds() && !searchCriteria.getRoleIds().isEmpty()){
				where.and(queryResponse.responseBy.role.any().roleId.in(searchCriteria.getRoleIds()));
			}
			
			//ROLE SPECIFIC RESPONSE VIEW
			if(searchCriteria.isReplyToEmbassy()){
				where.and(queryResponse.responseBy.role.any().roleName.like(MdcConstants.ROLE_ADMIN).and(queryResponse.directReplyFlag.eq(true))
						.or(queryResponse.sendFinalReplyFlag.eq(true))						
						 /* fix : Removing FDM from viewing replies from KLN or replies from admin approved by KLN*/
						/*queryResponse.responseBy.role.any().roleName.like(MdcConstants.ROLE_KLN).or
						(queryResponse.responseBy.role.any().roleName.like(MdcConstants.ROLE_ADMIN).and(queryResponse.directReplyFlag.eq(true)))
						.or(queryResponse.responseBy.role.any().roleName.like(MdcConstants.ROLE_ADMIN).and(queryResponse.responseStatus.eq(MdcConstants.STATUS_APPROVE)))*/
						);
			}	
			if(null != searchCriteria.getReplyToFocal()){
				where.and(queryResponse.responseBy.department.deptId.eq(Long.valueOf(searchCriteria.getReplyToFocal()))
						.or(queryResponse.sendFinalReplyFlag.eq(true)));
			}
			if(where.hasValue()){
				filteredResult = (List<Response>) responseRepository.findAll(where);	
			}/*else{
				filteredResult = recipientRepository.findAll();
			}*/ // this else condition displays all queries if there is no search criteria
		}
		catch(Exception ex){
			ex.printStackTrace();		
			throw new RuntimeException(ex);
		}
		return filteredResult;

	}

	@Override
	public Comment addComment(Comment comment) {
		Comment savedComment=null;
		try{
			savedComment = commentRepository.save(comment);			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return savedComment;
	}

	@Override
	public Comment getComment(String commentId) {
		Comment comment = null;
		try{
			comment = commentRepository.findOne(Long.valueOf(commentId));	
			if(null == comment){
				throw new IllegalStateException("comment doesnot exist");
			}
		}catch(NumberFormatException numbeFormatEx){
			throw new IllegalStateException("invalid datatype for id");
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return comment;
	}

	@Override
	public void removeComment(Long commentId) {
		try{
			commentRepository.delete(commentId);			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		
	}

	@Override
	public List<Comment> searchQueryComments(SearchCommentRequestResource searchCriteria) {
		List<Comment> filteredResult = null;
		try{
			QComment comment = QComment.comment;
			
			BooleanBuilder where = new BooleanBuilder();
			
			if(null != searchCriteria.getQueryId()){
				where.and(comment.query.queryId.eq(Long.valueOf(searchCriteria.getQueryId())));
			}
			if(null != searchCriteria.getUserId()){
				where.and(comment.commentBy.userId.eq(searchCriteria.getUserId()));
			}
			if(null != searchCriteria.getQueryIds() && searchCriteria.getQueryIds().size()!= 0){
				where.and(comment.query.queryId.in(searchCriteria.getQueryIds()));
			}
			if(null != searchCriteria.getUserIds() && searchCriteria.getUserIds().size()!= 0){
				where.and(comment.commentBy.userId.in(searchCriteria.getUserIds()));
			}
			if(where.hasValue()){
				filteredResult = (List<Comment>) commentRepository.findAll(where);	
			}/*else{
				filteredResult = recipientRepository.findAll();
			}*/ // this else condition displays all queries if there is no search criteria
		}
		catch(Exception ex){
			ex.printStackTrace();		
			throw new RuntimeException(ex);
		}
		return filteredResult;
	}
	
	@Override
	public ResponseApproval saveResponseApproval(ResponseApproval responseApproval){
		ResponseApproval approval = null;
		try{
			approval = responseApprovalRepository.save(responseApproval);			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return approval;
	}

	@Override
	public void addComment(List<Comment> comment) {
		try{
			commentRepository.save(comment);			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		
	}
}
