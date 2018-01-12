package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class SeverityLevelResource {

	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("SeverityLevel")
	private String severityLevel;
	
	@JsonProperty("SeverityLevelCode")
	private String severityLevelCode;
}
