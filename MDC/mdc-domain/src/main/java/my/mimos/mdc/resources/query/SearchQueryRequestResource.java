package my.mimos.mdc.resources.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchQueryRequestResource {
	
	private String queryId;
	private String subject;		
	private String approvalStatus;
	private String fromDate;
	private String toDate;
	private String deptId;
	private String sentBy;
	
}
