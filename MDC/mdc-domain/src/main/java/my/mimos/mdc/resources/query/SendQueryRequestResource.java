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
public class SendQueryRequestResource {
	
	@NotNull(message = "subject is required")
	@NotBlank(message = "subject is required")
	private String subject;
	
	@NotNull(message = "query description is required")
	@NotBlank(message = "query description is required")
	private String description;	
	
	private List<Long> uploadIds;

}
