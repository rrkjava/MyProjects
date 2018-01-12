package my.mimos.mdc.resources.query;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CommentResource {
	
	private Long commentId;	
	private String commentDesc;
	private String commentBy ;
	
	private boolean readReciept;
	private String query;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String createdDate;
	private DisplayUserResource createdBy;
}
