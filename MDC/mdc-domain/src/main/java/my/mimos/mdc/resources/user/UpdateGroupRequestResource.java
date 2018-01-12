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
public class UpdateGroupRequestResource {
	
	@NotNull(message = "groupId is required")
	private Long groupId;
	
	@NotBlank(message = "group name is required")
	@NotNull(message = "group name is required")
	private String groupName;

	private String groupStatus;
}
