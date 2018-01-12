package my.mimos.mdc.resources.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DepartmentTypeResource {
	
	private Long deptTypeId;
	private String deptTypeName;
	private String deptTypeDesc;
	
}
