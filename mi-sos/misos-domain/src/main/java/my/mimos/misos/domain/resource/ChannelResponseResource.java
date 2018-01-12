/**
 * 
 */
package my.mimos.misos.domain.resource;


import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.misos.common.enums.StatusType;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class ChannelResponseResource extends ChannelBaseResource {

	private String statusType = StatusType.ERROR;
	
	private String statusCode = "E0021";
	
	private String status = "Dissemination failed.";
	
}
