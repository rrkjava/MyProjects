package my.mimos.mdc.resources.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserRecipientResource {	
	private Long userId;
	private String username;
	private String firstName;
}
