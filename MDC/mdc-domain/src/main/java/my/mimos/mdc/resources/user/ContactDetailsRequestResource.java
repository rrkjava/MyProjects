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
public class ContactDetailsRequestResource {

	@NotNull(message = "userId is required")
	private Long userId;

}
