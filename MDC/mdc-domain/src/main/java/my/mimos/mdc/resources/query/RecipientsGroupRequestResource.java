/**
 * 
 */
package my.mimos.mdc.resources.query;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class RecipientsGroupRequestResource {

	private Long queryId;
	private List<Long> groupIds;

}
