/**
 * 
 */
package my.mimos.misos.domain.channel;

import java.util.List;

import lombok.Data;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@Data
public class MobileGatewayRequestResource {

	private String impactedArea;
	
	private String message;
	
	private String messageType;
	
	private String channelType;
	
	private String iocMessageId;
	
	private String targetUserGroups;
	
	private List<MobileGatewayDeviceResource> deviceList;
}
