package my.mimos.mdc.resources.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class QueryInboxRequestResource {
	
	private Long userId;

}
