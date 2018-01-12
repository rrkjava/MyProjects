package my.mimos.mdc.resources.device;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.resources.BaseResponseResource;

@Data
@EqualsAndHashCode(callSuper=false)
public class DevicesResponseResource extends BaseResponseResource{
	
	private List<DeviceResource> devices;

}
