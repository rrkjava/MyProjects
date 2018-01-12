package my.mimos.mdc.resources;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.enums.StatusType;

@Data
@EqualsAndHashCode(callSuper=false)
public class BaseResponseResource {
	
	protected StatusType statusType;

	protected String statusCode;

	protected String status;
	
	/*@JsonInclude(JsonInclude.Include.NON_NULL)
	protected String info;*/

}
