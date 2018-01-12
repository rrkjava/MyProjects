package my.mimos.misos.domain.channel;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.misos.domain.resource.ChannelResponseResource;

/**
 * 
 */

/**
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class PublicPortalChannelResponseResource extends ChannelResponseResource {
	
	@JsonProperty("Code")
	private String code;
	
	@JsonProperty("Desc")
	private String desc;
}
