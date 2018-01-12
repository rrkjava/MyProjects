package my.mimos.mdc.resources.query;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 
 * @author beaula.fernandez
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class QueryResponseResource {

	private Long responseId;
	private String description;	
	private String query;	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String createdDate;
	private DisplayUserResource createdBy;	
	private List<UploadMetadataResource> attachment;
	
	private boolean directReply;
	private boolean finalReplyFlag;
	private boolean sendFinalReplyFlag;
	
	private String responseStatus;
	@JsonIgnore
	private String approvalComment;
	@JsonIgnore
	private String approvalDate; //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")	
	@JsonIgnore
	private DisplayUserResource approvedBy;
	
	private boolean readReciept;
	private boolean approverOfResponse;
	private List<ResponseApprovalResource> approvals;
	
	
}
