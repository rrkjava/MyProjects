package my.mimos.mdc.resources.device;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddDeviceRequestResource {
	
	@NotNull(message = "device type is required")
	@NotBlank(message = "device type is required")
	private String deviceType;
	
	@NotNull(message = "device token is required")
	@NotBlank(message = "device token is required")
	private String deviceToken;

}
