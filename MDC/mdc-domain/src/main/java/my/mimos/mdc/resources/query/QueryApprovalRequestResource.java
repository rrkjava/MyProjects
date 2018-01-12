package my.mimos.mdc.resources.query;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class QueryApprovalRequestResource {
	
	@NotNull(message = "queryId is required")
	private Long queryId;
	
	@NotNull(message = "reason is required")
	@NotBlank(message = "reason is required")
	private String reason;
}
