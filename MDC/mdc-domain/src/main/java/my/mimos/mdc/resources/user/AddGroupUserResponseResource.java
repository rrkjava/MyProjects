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
public class AddGroupUserResponseResource extends BaseResponseResource {

	private List<Long> rejectedUsers;
	private List<Long> addedUsers;
	
}
