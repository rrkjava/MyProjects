package my.mimos.mdc.resources.query;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchAttachmentRequestResource {
	
	private Long queryId;
	private List<Long> uploadIds;
	private Long messageId;
	
}
