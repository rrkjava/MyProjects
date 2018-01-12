package my.mimos.mdc.resources.query;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.resources.BaseResponseResource;
/**
 * 
 * @author beaula.fernandez
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class RecipientsRequestResource extends BaseResponseResource{
	
	
	@NotNull(message = "queryId is required")	
	private Long queryId;
	
	private List<Long> recipientIds; // multiple users
	private List<Long> deptIds; // agencies
	private List<Long> groupIds; // user groups

}
