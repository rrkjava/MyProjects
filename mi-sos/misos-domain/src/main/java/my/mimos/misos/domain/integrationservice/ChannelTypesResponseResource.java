package my.mimos.misos.domain.integrationservice;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class ChannelTypesResponseResource  extends BaseResponseResource{
	
	@JsonProperty("ListTargetChannelType")
	private List<ChannelTypeResource> channelTypes;

}

