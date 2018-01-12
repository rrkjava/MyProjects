package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class ChannelRecipientResource {
	
	@JsonProperty("UserId")
	private String userId;
	
	@JsonProperty("FirstName")
	private String name;
	
	@JsonProperty("DeleteFlag")
	private String isDeleted;
	
	@JsonProperty("PhoneMobile")
	private String sms;
	
	@JsonProperty("Email")
	private String email;
	
	@JsonProperty("PoiId")
	private String poiId;
	
	@JsonProperty("UserType")
	private String userType;
	
	@JsonProperty("UserGroupCode")
	private String userGroup;
	
	@JsonProperty("DistrictId")
	private String districtId;
	
	@JsonProperty("StateId")
	private String stateId;
	
	

}
