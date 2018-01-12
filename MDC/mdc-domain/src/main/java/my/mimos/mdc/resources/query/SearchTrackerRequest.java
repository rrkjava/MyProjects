package my.mimos.mdc.resources.query;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchTrackerRequest {
	
	private Long queryId;
	private Long responseId;
	private Long trackerId;
	private String role;
	private Long deptId;
	private Date startDate;
	private Date endDate;
	private boolean valid;
	private boolean status;
	private boolean deadLine;
	private String queryAction;
}
