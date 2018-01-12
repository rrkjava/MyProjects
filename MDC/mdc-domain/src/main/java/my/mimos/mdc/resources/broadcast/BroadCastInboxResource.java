package my.mimos.mdc.resources.broadcast;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BroadCastInboxResource {

	private Long messageId;
	private String subject;	
	private String broadcastDate;
	
	private String receivedDate;
	private String readStatus;
	private String readDate;
	private String lastActivityDate;
	
	private BroadCastByResource broadcastBy;
	
}
