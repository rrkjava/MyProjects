package my.mimos.mdc.resources.user;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.resources.BaseResponseResource;

@Data
@EqualsAndHashCode(callSuper=false)
public class GetRolesResponseResource extends BaseResponseResource{
	
	List<RoleResource> roles;

}
