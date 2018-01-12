package my.mimos.mdc.resources.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.resources.BaseResponseResource;

@Data
@EqualsAndHashCode(callSuper=false)
public class UrgencyLevelResponseResource  extends BaseResponseResource{
	
	private UrgencyResource urgencyLevel;

}
