package my.mimos.mdc.resources.broadcast;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchBroadCastResponseRequest {
   
	private Long messageId;
	private Long userId;
	private String userRole;
	
}
