package my.mimos.mdc.resources.query;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateUrgencyRequestResource{
	
	@NotNull(message = "urgency id is required")
	private Long urgencyId;
	
	private String urgencyLevel;
	
	private String priority;

}
