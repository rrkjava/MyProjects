package my.mimos.mdc.resources.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 
 * @author beaula.fernandez
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class AttachmentResource {
	
	private Long uploadId;	
	private String uploadTitle;
	private byte[] content;
	
}
