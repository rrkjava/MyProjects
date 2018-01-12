package my.mimos.mdc.resources.broadcast;

import java.util.List;

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
public class BroadCastMessageRequestResource {
	
	@NotNull(message = "subject is required")
	@NotBlank(message = "subject is required")
	private String subject;
	
	@NotNull(message = "message is required")
	@NotBlank(message = "message is required")
	private String message;
	
	@NotNull(message = "broadcaster is required")
	private Long broadcastBy;
	
	private List<Long> userIds;
	
	private List<Long> groupIds;
	
	private List<Long> uploadIds;
	
}
