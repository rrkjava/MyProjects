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
public class DisseminationChannel {
	
	@JsonProperty("EmailChannel")
	private List<EmailChannelResource> emailChannel;
	
	@JsonProperty("SmsChannel")
	private List<PhoneMobileResource> smsChannel;
	
	@JsonProperty("MobileChannel")
	private List<MobileChannelResource> mobileChannel;
	
	@JsonProperty("FaxChannel")
	private List<FaxChannelResource> faxChannel;
	
	@JsonProperty("SirenChannel")
	private List<SirenChannelResource> sirenChannel;

}
