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
public class SirenChannelRequestResource extends ChannelBaseResource {

	private String sirenMessage;
	
	private String channelType;

	private String iocMessageId;
	
	private String sirenMobileNo;
}
