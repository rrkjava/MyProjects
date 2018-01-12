package my.mimos.misos.domain.integrationservice;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class PhoneMobileResource {
	
	@JsonProperty("PhoneMobile")
	private String phoneMobile;

}
