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
public class PublicPortalRequestResource {
	
	@JsonProperty("Header")
	private PublicPortalHeaderRequest header;
	
	@JsonProperty("DisseminationResponse")
	private PublicPortalRequest disseminationResponse;
	
	@JsonProperty("Body")
	private PublicPortalBodyRequest body;
}
