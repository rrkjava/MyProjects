package my.mimos.mdc.resources.user;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.resources.BaseResponseResource;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetDeptsResponseResource extends BaseResponseResource{

	private List<DepartmentResource> departments;
	
}
