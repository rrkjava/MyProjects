/**
 * 
 */
package my.mimos.mdc.resources.user;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class AddGroupUserRequestResource {

	@NotNull(message = "userId is required")
	private Long groupId;
	
	@NotNull(message = "userId is required")
	@NotEmpty(message = "userId is required")
	private List<Long> userIds;
	
}
