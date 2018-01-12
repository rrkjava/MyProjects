/**
 * 
 */
package my.mimos.misos.domain.resource;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import my.mimos.misos.common.enums.StatusType;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public abstract class BaseResponseResource extends BaseResource {

	protected String statusType;

	protected String statusCode;

	protected String status;
	
}
