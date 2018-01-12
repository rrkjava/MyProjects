package my.mimos.mdc.command;

import java.util.List;

import my.mimos.mdc.domain.entity.MobileDevice;
import my.mimos.mdc.resources.device.SearchDeviceRequestResource;

public interface DeviceCommand {
	
	public MobileDevice addDevice(MobileDevice device);

	public MobileDevice findDevice(Long deviceId);
	
	public List<MobileDevice> listDevices();

	public List<MobileDevice> searchDevices(SearchDeviceRequestResource searchCriteria);

	public void deleteDevice(Long id);

	public MobileDevice findByDeviceToken(String deviceToken);

}
