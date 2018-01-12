/**
 * 
 */
package my.mimos.misos.domain.resource;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class SearchChannelRequestResource extends BaseResource {
	
	private String iocMessageId;
	private String channelType;
	private String severityLevel;
	private String fromDate;
	private String toDate;
}
