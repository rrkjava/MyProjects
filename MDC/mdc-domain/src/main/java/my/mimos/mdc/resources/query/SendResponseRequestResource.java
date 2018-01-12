package my.mimos.mdc.resources.query;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 
 * @author beaula.fernandez
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class SendResponseRequestResource {
	
	@NotNull(message = "response description is required")
	@NotBlank(message = "response description is required")
	private String description;	
	
	@NotNull(message = "query id is required")
	private Long queryId;
	
	@NotNull(message = "direct reply flag is required")
	private boolean directReply;
	
	private List<Long> uploadIds;
	
	private boolean KLNReply;

}
