package my.mimos.mdc.resources.query;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchResponsesRequestResource {
	
	private String status;
	
	private Long queryId;	
	private Long senderId;
	private Long responseId;
	private List<Long> queryIds;
	private List<Long> userIds;
	private List<Long> responseIds;
	
	private String userRole;
	private List<String> userRoles;
	private Long userDept;
	private List<Long> userDepts;
	
	private String deptName;
	private List<String> deptNames;
	private Long roleId;
	private List<Long> roleIds;
	
	//special cases
	private boolean replyToEmbassy;
	private Long replyToFocal;

}
