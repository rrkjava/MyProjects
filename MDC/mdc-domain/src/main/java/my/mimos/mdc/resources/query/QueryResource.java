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
public class QueryResource {
	
	private Long queryId;
	private String subject;	
	private String description;
	private List<UploadMetadataResource> attachment;	
	
	private String urgencyLevel;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String createdDate;
	private DisplayUserResource createdBy;	
	
	
	private String approvalStatus;
	@JsonIgnore
	private String approvalComment;	
	@JsonIgnore
	private String approvalDate;//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")	
	@JsonIgnore
	private DisplayUserResource approvedBy;	 
	
	// Indicates if the recipients of the query has read it.
	private boolean readReciept;
	//Indicates if the query has been forwarded.
	private boolean forwardFlag;
	

}
