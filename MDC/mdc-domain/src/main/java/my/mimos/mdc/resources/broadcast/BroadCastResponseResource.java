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
public class BroadCastResponseResource {
	
	private Long responseId;
	private String responseMessage;
	private String broadCast;
    private String responseDate;
	private List<UploadMetadataResource> attachment;
	private BroadCastByResource responseBy;

}
