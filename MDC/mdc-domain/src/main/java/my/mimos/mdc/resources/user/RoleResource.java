package my.mimos.mdc.resources.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RoleResource {

	@NotBlank(message = "role is required")
	@NotNull(message = "role is required")
	private Long roleId;
	
	private String roleName;
	private String roleDesc;

}
