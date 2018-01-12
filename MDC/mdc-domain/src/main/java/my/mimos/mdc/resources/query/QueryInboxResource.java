package my.mimos.mdc.resources.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class QueryInboxResource {

	private Long queryId;
	private String subject;	
	private String status;
	private String queryType;
	private String lastActivityDate;//(forward/comment/reply/approval)
	
	private String receivedDate;	
	// Indicates if the sender has unread content in the conversation thread of a query.
	private String readStatus;
	//Indicates if the query has been forwarded.
	public boolean forwardFlag;
	//Indicates if the query has been maked normal or urgent.
	public String urgencyFlag;
	
	private String createdDate;
	private DisplayUserResource createdBy;
	
	
}
