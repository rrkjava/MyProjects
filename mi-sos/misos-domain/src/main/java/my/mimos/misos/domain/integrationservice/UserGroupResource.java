package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class UserGroupResource {

	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("UserGroup")
	private String userGroup;
	
	@JsonProperty("UserGroupCode")
	private String userGroupCode;

}
