package my.mimos.mdc.resources.query;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateResponseRequestResource {
	
	@NotNull(message = "reponse id is required")
	private Long responseId;
	
	private String description;	
	
	private List<Long> uploadIds;

}
