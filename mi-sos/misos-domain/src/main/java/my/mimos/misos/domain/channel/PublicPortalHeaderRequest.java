package my.mimos.misos.domain.channel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 */

/**
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class PublicPortalHeaderRequest {
	
	@JsonProperty("DateTime")
	private String dateTime;
	
	@JsonProperty("RequestId")
	private String requestId;
	
	@JsonProperty("TransCode")
	private String transCode;
}
