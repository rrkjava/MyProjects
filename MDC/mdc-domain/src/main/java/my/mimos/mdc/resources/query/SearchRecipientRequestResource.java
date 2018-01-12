package my.mimos.mdc.resources.query;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchRecipientRequestResource {
	
	private Long queryId;
	private Long userId;
	private Long groupId;
	private Long deptId;	
	private List<Long> queryIds;
	private List<Long> userIds;
	private List<Long> groupIds;
	private List<Long> deptIds;
	private String recipientType;
	private String role;
	private List<String> roles;
	
	public boolean filterAdminAndfocalOfDept;
	public Long focalDeptId;
	
	

}
