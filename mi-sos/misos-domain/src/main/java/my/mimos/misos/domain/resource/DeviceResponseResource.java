/**
 * 
 */
package my.mimos.misos.domain.resource;

import lombok.Getter;
import lombok.Setter;
import my.mimos.misos.common.enums.StatusType;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
@Getter
@Setter
public class DeviceResponseResource extends LocationBaseResource {
	private String deviceToken;
	
	private StatusType statusType;

	private String statusCode;

	private String status;
}
