package my.mimos.mdc.resources.query;


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
public class RecipientRequestResource {
	
	@NotNull(message = "queryId is required")
	private Long queryId;
	
	@NotNull(message = "recipientId is required")
	@NotBlank(message = "recipientId is required")
	private String recipientId;
}
