package my.mimos.mdc.resources.device;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.resources.BaseResponseResource;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeviceResponseResource extends BaseResponseResource{

	private DeviceResource device;
}
