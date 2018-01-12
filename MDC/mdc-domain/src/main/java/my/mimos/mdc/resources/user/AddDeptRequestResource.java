package my.mimos.mdc.resources.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddDeptRequestResource {
	
	@NotBlank(message = "department name is required")
	@NotNull(message = "department name is required")
	private String deptName;
	
	@NotBlank(message = "department description  is required")
	@NotNull(message = "department description is required")
	private String deptDesc;

}
