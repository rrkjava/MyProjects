/**
 * 
 */
package my.mimos.mdc.resources.broadcast;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.resources.BaseResponseResource;

/**
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class BroadCastDetailResource extends BaseResponseResource {
	private BroadCastResource broadCastResource;
	private List<BroadCastResponseResource> response;
}
