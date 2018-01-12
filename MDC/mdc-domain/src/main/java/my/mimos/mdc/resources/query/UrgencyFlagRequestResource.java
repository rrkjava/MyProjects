package my.mimos.mdc.resources.query;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UrgencyFlagRequestResource {
	
	@NotNull(message = "query id is required")
	private Long queryId;
	
	@NotNull(message = "urgency flag is required")
	private boolean urgencyFlag;

}
