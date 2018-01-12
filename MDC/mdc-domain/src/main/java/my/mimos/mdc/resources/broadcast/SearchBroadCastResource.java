package my.mimos.mdc.resources.broadcast;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchBroadCastResource {
   
	private Long messageId;
	private Long groupId;
	private List<Long> groupIds;
	private Long userId;
	private List<Long> userIds;
	private String recipientType;
	
}
