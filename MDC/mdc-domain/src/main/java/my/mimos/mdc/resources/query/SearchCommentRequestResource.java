package my.mimos.mdc.resources.query;


import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchCommentRequestResource {
	
	private Long queryId;
	private Long userId;
	
	private List<Long> queryIds;
	private List<Long> userIds;
}
