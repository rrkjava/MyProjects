package my.mimos.mdc.resources.query;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UploadMetadataResource {
	
	private Long uploadId;	
	private String uploadTitle;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String uploadDate;
	private DisplayUserResource uploadBy;
	
	

}
