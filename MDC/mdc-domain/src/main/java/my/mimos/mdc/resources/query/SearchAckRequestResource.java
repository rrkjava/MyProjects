package my.mimos.mdc.resources.query;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchAckRequestResource {
	
	private List<Long> ackIds;
	
	private Long queryId;
	private Long userId;
	private String userRole;
	private Long userDept;
} 
