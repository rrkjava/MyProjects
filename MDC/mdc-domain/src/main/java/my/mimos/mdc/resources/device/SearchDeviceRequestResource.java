package my.mimos.mdc.resources.device;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SearchDeviceRequestResource {
	
	private Long deviceId;
	private Long userId;	
	private String deviceType;
	private String deviceToken;
	private String deviceStatus;
	
	
	private List<Long> userIds;
}
