package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class LocationTypeResource {
	
	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("LocationType")
	private String locationType;
	
	@JsonProperty("LocationTypeCode")
	private String locationTypeCode;

}
