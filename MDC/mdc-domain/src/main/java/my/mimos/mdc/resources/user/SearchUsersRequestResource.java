package my.mimos.mdc.resources.user;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchUsersRequestResource {
	
	private List<Long> userIds; 	
	private String userStatus;	
	private String userRole;
	
	private Long deptId;
	private List<Long> deptIds;
	private String deptName;
}
