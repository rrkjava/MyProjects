/**
 * 
 */
package my.mimos.mdc.resources.user;



import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.resources.BaseResponseResource;

/**
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class GetGroupProfileResponseResource extends BaseResponseResource {

	private GroupResource group;
	private DepartmentResource Department;
	private List<UserResource> users;
	
}
