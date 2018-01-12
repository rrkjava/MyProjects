package my.mimos.mdc.resources.query;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RecipientResource {

	private String id;
	private String query;
	private String recipientUserId;	
	private String groupId;	
	private String deptId;
	private String recipientType;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String assignedDate;
	
}
