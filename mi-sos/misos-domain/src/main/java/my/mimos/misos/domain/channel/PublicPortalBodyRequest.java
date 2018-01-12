package my.mimos.misos.domain.channel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.misos.domain.resource.ChannelBaseResource;

/**
 * 
 */

/**
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class PublicPortalBodyRequest {
	
	@JsonProperty("Id")
	private String id;
	
	@JsonProperty("Indicator")
	private String indicator;
}
