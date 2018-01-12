package my.mimos.mdc.resources.query;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ResponseApprovalRequestResource {
	
	@NotNull(message = "response id is required")
	private Long responseId;
	
	/*@NotNull(message = "reason is required")
	@NotBlank(message = "reason is required")*/
	private String reason;
	
	@NotNull(message = "please specify accept paramter : true or false") 
	private boolean accept;

}
