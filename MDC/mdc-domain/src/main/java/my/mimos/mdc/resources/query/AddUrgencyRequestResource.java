package my.mimos.mdc.resources.query;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddUrgencyRequestResource {
	
	@NotNull(message = "urgency level is required")
	@NotBlank(message = "urgency level is required")
	private String urgencyLevel;
	
	@NotNull(message = "priority is required")
	@NotBlank(message = "priority is required")
	private String priority;

}
