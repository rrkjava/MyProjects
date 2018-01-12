package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class RecipientResource {

	@JsonProperty("DeviceId")
	private String deviceId;
	
	@JsonProperty("Email")
	private String email;
	
	@JsonProperty("PhoneMobile")
	private String phoneMobile;
	
	@JsonProperty("PoiId")
	private String poiId;
	
	@JsonProperty("PublicId")
	private String publicID;
	
	@JsonProperty("UserGroupCode")
	private String userGroup;
	
	@JsonProperty("UserId")
	private String userId;
	
	@JsonProperty("DeviceType")
	private String deviceType;
	
	@JsonProperty("TokenId")
	private String tokenId;
}
