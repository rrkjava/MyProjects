package my.mimos.mdc.resources.query;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class QueryCommentRequestResource {

	@NotNull(message = "queryId is required")
	private Long queryId;
	
	@NotNull(message = "comment description is required")
	@NotBlank(message = "comment description is required")
	private String commentDesc;
}
