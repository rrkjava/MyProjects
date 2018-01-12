package my.mimos.mdc.resources.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class LoginRequestResource {

	@NotNull(message = "username is required")
	@NotBlank(message = "username is required")
	private String username;
	
	@NotNull(message = "password is required")
	@NotBlank(message = "password is required")
	private String password;
	
	/*@NotNull(message = "device token is required")
	private String deviceToken;*/
	
}
