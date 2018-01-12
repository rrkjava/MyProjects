package my.mimos.mdc.resources.query;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssignUrgencyRequestResource {
	
	@NotNull(message = "query id is required")
	private Long queryId;
	
	@NotNull(message = "urgency level id is required")
	private Long urgencyLevelId;

}
