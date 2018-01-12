/**
 * 
 */
package my.mimos.mdc.resources.broadcast;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.resources.user.RoleResource;

/**
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class BroadCastByResource {
	
	private String firstName;
	private String department;
	private String username;
	private String userId;
	
	@JsonIgnore
	private Set<RoleResource> role;

}
