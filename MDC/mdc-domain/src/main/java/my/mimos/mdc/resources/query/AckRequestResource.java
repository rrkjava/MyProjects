package my.mimos.mdc.resources.query;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AckRequestResource {
	
	@NotNull(message = "query id is required")
	private Long queryId;
	
	/*@NotNull(message = "comment is required")
	@NotBlank(message = "comment is required")*/
	private String comment;	
	
	
	

}
