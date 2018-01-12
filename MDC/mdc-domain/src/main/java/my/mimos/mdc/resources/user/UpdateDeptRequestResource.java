package my.mimos.mdc.resources.user;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateDeptRequestResource {
	
	@NotNull(message = "department id is required")
	private Long deptId;
	
	private String deptName;

}
