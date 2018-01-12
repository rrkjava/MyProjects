package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import my.mimos.misos.domain.integrationservice.BaseResponseResource;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class UserGroupResponseResource extends BaseResponseResource{
	
	@JsonProperty("UserGroup")
	private UserGroupResource userGroup;
	
}
