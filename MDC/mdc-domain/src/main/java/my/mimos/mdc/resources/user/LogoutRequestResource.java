/**
 * 
 */
package my.mimos.mdc.resources.user;

import javax.validation.constraints.NotNull;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class LogoutRequestResource {
	
	@NotNull(message = "userId is required")
	private Long userId;
	
	//@NotNull(message = "device token is required")
	private Long deviceId;

}
