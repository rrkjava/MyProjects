package my.mimos.mdc.resources.query;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AckResource {

	private Long ackId;
	private String ackStatus;
	private String ackComment;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String createdDate;
	private DisplayUserResource createdBy;
	
	private boolean readReciept;
	
}
