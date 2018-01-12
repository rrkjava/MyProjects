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
public class SummaryReportRequestResource extends BaseResource {
	
	private String status;
	private String channelType;
	private String fromDate;
	private String toDate;
}
