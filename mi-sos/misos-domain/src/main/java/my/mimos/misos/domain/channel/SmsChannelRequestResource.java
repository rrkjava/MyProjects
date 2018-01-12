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
public class SmsChannelRequestResource extends ChannelBaseResource {

	private String smsMessage;
	
	private String channelType;

	private String iocMessageId;
	
	private String poi;
	
	private String 	targetUserGroups;
	
}
