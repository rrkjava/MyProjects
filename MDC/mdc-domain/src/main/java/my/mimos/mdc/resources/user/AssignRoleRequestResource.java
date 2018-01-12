package my.mimos.mdc.resources.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssignRoleRequestResource {
	
	private Long userId;
	private Long roleId;

}
