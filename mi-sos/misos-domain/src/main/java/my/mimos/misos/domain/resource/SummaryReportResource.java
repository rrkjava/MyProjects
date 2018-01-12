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
public class SummaryReportResource extends BaseResource {
	
	private Long numOfMessages;
	private Long numOfRecipients;
	private String channelType;
	private boolean publishStatus;
	
}
