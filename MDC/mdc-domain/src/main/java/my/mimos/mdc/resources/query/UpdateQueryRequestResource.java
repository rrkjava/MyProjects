package my.mimos.mdc.resources.query;

import java.util.List;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateQueryRequestResource {
	
	@NotNull(message = "query id is required")
	private Long queryId;
	
	private String subject;
	
	private String description;	
	
	private List<Long> uploadIds;
	
}
