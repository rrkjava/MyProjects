/**
 * 
 */
package my.mimos.mdc.resources.broadcast;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.resources.query.UploadMetadataResource;

/**
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class BroadCastResource {
	
	private Long messageId;
	private String subject;	
	private String message;
	private String broadcastDate;
	private List<UploadMetadataResource> attachment;
	private BroadCastByResource broadcastBy;

}
