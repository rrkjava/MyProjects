package my.mimos.mdc.resources.user;

import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class LoginResource {

	private String userAuthToken;
	private String issuedOn;
	private String expiresOn;
	private Set<String> userRoles;
	private Long userId;
	private Long deviceId;
	
}
