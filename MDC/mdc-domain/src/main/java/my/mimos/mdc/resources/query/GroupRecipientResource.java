package my.mimos.mdc.resources.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GroupRecipientResource {
	
	private Long groupId;
	private String groupName;	

}
