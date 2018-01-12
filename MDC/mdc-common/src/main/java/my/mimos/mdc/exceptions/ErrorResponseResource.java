package my.mimos.mdc.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.enums.StatusType;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorResponseResource{
	
	protected StatusType statusType;

	protected String statusCode;

	protected String status;
}
