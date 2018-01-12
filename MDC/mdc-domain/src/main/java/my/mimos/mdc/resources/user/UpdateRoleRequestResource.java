package my.mimos.mdc.resources.user;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateRoleRequestResource {
	
	@NotNull(message = "role id is required")
	private Long roleId;
	
	private String roleName;
	private String roleDesc;

}
