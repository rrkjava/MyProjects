package my.mimos.mdc.resources.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode()
public class UpdateProfileRequestResource {
	
	private Long userId;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private String address;

}
