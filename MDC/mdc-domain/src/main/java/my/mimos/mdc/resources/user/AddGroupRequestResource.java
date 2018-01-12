/**
 * 
 */
package my.mimos.mdc.resources.user;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class AddGroupRequestResource {

	@NotBlank(message = "group name is required")
	@NotNull(message = "group name is required")
	private String groupName;
	
	@NotNull(message = "department id is required")
	private Long deptId;

}
