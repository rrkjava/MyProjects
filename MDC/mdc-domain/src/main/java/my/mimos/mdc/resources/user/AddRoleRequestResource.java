package my.mimos.mdc.resources.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddRoleRequestResource {
	
	@NotBlank(message = "role name is required")
	@NotNull(message = "role name is required")
	private String roleName;
	
	@NotBlank(message = "role description  is required")
	@NotNull(message = "role description is required")
	private String roleDesc;

}
