/**
 * 
 */
package my.mimos.misos.domain.channel;

import lombok.Data;
import my.mimos.misos.common.enums.StatusType;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
@Data
public class MobileGatewayResponseResource {
	
	protected StatusType statusType;

	protected String statusCode;

	protected String status;
	
}
