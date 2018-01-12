package my.mimos.mdc.resources.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.resources.BaseResponseResource;
/**
 * 
 * @author beaula.fernandez
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class MediaUploadResponseResource extends BaseResponseResource{
	
	private Long uploadId;

}
