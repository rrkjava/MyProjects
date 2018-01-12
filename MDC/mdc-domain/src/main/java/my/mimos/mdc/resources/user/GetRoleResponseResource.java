package my.mimos.mdc.resources.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.resources.BaseResponseResource;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetRoleResponseResource extends BaseResponseResource{

	private RoleResource role;
}
