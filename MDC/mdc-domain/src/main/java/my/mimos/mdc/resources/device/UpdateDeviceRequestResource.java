package my.mimos.mdc.resources.device;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateDeviceRequestResource {
	
	@NotNull(message = "device id is required")
	@NotBlank(message = "device id is required")
	private Long deviceId;
	
	private String deviceType;
	private String deviceToken;
	private String deviceStatus;
	
	private boolean updateUser;

}
