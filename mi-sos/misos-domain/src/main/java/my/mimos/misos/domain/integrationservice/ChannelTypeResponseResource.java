package my.mimos.misos.domain.integrationservice;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import my.mimos.misos.domain.integrationservice.ChannelTypeResource;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class ChannelTypeResponseResource extends BaseResponseResource{
	
	@JsonProperty("TargetChannelType")
	private ChannelTypeResource channelType;

}
