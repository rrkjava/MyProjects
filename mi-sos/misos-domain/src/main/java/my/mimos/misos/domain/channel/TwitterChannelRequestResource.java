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
public class TwitterChannelRequestResource extends ChannelBaseResource {

	private String twitterMessage;
	
	private String iocMessageId;
}
