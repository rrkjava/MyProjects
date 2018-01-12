package my.mimos.mdc.resources.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode()
public class ForgotPasswordRequestResource {

	@NotNull(message = "username is required")
	@NotEmpty(message = "username is required")
	private String username;
	
}
