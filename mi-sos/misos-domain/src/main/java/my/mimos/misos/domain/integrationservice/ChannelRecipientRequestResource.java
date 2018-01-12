package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class ChannelRecipientRequestResource extends BaseRequestResource{
	
	@JsonProperty("Group")
	private String targetUserGroup;
	
	@JsonProperty("Poi")
	private String poi;
	
	@JsonProperty("Type")
	private String userType;

}
