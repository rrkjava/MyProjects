/**
 * 
 */
package my.mimos.misos.domain.resource;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.misos.common.enums.StatusType;

/**
 * @author nandika.liyanage
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class DisseminationResponseResource extends DisseminationBaseResource {
	//private String channelType;

	private String statusType;

	private String statusCode;

	private String status;
	
	private List<ChannelResponseResource> channels;

}
