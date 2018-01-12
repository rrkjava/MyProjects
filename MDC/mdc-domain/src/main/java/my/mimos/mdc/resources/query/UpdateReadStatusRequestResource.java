package my.mimos.mdc.resources.query;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateReadStatusRequestResource {
	
	@NotNull(message = "query id is required")
	private Long queryId;
	
	@NotNull(message = "recipient user id is required")
	private Long recipientId;

}
