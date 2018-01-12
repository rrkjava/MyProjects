/**
 * 
 */
package my.mimos.misos.domain.channel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.misos.domain.resource.ChannelBaseResource;

/**
 * @author krishna.redabotu
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class FaxChannelRequestResource extends ChannelBaseResource {
	
	private String faxMessage;
	
	private String channelType;

	private String iocMessageId;
	
	private String faxEmailAddress;
}
