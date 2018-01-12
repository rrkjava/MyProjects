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
public class EmailChannelRequestResource extends ChannelBaseResource {
	
	private String emailMessage;
	
	private String channelType;

	private String iocMessageId;
	
	private String poi;
	
	private String 	targetUserGroups;
}
