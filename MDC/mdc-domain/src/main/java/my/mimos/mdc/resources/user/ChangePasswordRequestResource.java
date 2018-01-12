package my.mimos.mdc.resources.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode()
public class ChangePasswordRequestResource {

	private Long userId;
	private String newPassword;	
	private String oldPassword;	
	
}
