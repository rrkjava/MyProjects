package my.mimos.mdc.resources.device;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeviceResource {
	
	private Long deviceId;
	private String deviceType;
	private String deviceToken;
	private String createdDate;
	private String deviceStatus;
	private Long userId;

}
