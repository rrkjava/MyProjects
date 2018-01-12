/**
 * 
 */
package my.mimos.misos.domain.channel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.misos.common.enums.StatusType;
import my.mimos.misos.domain.resource.ChannelBaseResource;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ChannelResponseBaseResource extends ChannelBaseResource {
	
	private StatusType statusType;

	private String statusCode;

	private String status;
	
	private String channelType;
}
