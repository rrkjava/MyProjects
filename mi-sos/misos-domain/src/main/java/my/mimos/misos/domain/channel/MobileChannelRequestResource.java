/**
 * 
 */
package my.mimos.misos.domain.channel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.misos.domain.resource.ChannelBaseResource;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class MobileChannelRequestResource extends ChannelBaseResource {

	private String notificationMessage;
	
	private String wktStringData;
	
	private String messageType;
	
	private String iocMessageId;
	
	private String poi;
}
