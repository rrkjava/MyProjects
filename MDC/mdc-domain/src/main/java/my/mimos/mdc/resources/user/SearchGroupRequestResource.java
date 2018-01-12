package my.mimos.mdc.resources.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchGroupRequestResource {
	
	private Long deptId;
	private Long userId;	
	
}
