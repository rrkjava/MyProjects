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
public class RecipientRequestResource extends BaseRequestResource{
	
	@JsonProperty("TargetChannelTypeCode")
	private String targetChannelTypeCode;
	
	@JsonProperty("UserGroupCode")
	private String userGroupCode;
	
	@JsonProperty("NotificationTypeCode")
	private String notificationTypeCode;
	
	@JsonProperty("SeverityLevelCode")
	private String severityLevelCode;
	
	@JsonProperty("ImpactedArea")
	private String impactedArea;
	
	@JsonProperty("PoiList")
	private List<Poi> poiList;
	
	

}
