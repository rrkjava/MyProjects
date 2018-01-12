package my.mimos.mdc.resources.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserActivationRequestResource {
	
	@NotNull(message = "userId is required")
	@NotBlank(message = "userId is required")
	private String userId;
	
	@NotNull(message = "approval status is required")
	private boolean approve;
	
	@NotNull(message = "comment is required")
	@NotBlank(message = "comment is required")
	private String comment;

}
