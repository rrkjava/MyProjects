package my.mimos.mdc.resources.broadcast;

import java.util.List;

import javax.validation.constraints.NotNull;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BroadCastMessageUpdateRequestResource {
	
	@NotNull(message = "broadcast messageId required")
	private Long messageId;
	
    private List<Long> userIds;
	
	private List<Long> groupIds;
	
	private List<Long> uploadIds;
	
}
