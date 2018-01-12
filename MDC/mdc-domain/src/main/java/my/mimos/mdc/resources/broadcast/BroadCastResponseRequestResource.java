package my.mimos.mdc.resources.broadcast;

import java.util.List;

/**
 * @author krishna.redabotu
 *
 */

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BroadCastResponseRequestResource {
	
	@NotNull(message = "broadcast messageId required")
	private Long messageId;
	
	@NotNull(message = "response message is required")
	@NotBlank(message = "response message is required")
	private String responseMessage;	
	
	private List<Long> uploadIds;
}
