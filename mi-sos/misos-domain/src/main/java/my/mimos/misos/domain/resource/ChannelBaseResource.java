/**
 * 
 */
package my.mimos.misos.domain.resource;


import lombok.Data;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@Data
public abstract class ChannelBaseResource {

	protected String channelType;
	
	protected String targetUserGroups;
	
	protected String messageFormat;
	
	protected String recipientCount = "0";
	
}
