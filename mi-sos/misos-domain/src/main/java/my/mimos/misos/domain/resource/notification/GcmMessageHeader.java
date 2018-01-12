/**
 * 
 */
package my.mimos.misos.domain.resource.notification;

import lombok.Data;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@Data
public class GcmMessageHeader {

	private String to;
	
	private GcmNotificationMessage notification;
	
}
