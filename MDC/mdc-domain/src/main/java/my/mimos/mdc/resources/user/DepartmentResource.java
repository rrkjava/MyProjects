package my.mimos.mdc.resources.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DepartmentResource {
	
	@NotNull(message = "department is required")
	@NotBlank(message = "department is required")
	private Long deptId;
	
	private String deptName;
	
	private DepartmentTypeResource deptType;
	
}
