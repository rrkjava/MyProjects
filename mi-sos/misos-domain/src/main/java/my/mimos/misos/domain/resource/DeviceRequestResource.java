/**
 * 
 */
package my.mimos.misos.domain.resource;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class DeviceRequestResource extends LocationBaseResource {
	// The device token id from Android, iOS or Windows Phone
	private String deviceToken;
	
	private Integer userId;
}
