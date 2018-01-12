package my.mimos.mdc.service;

import my.mimos.mdc.resources.BaseResponseResource;
import my.mimos.mdc.resources.device.AddDeviceRequestResource;
import my.mimos.mdc.resources.device.DeviceResponseResource;
import my.mimos.mdc.resources.device.DevicesResponseResource;
import my.mimos.mdc.resources.device.SearchDeviceRequestResource;
import my.mimos.mdc.resources.device.UpdateDeviceRequestResource;

public interface DeviceService {

	public DeviceResponseResource addDevice(AddDeviceRequestResource request);

	public DeviceResponseResource updateDevice(UpdateDeviceRequestResource request);

	public DeviceResponseResource getDevice(Long deviceId);

	public DevicesResponseResource listDevices();

	public DevicesResponseResource searchDevices(SearchDeviceRequestResource searchCriteria);

	public BaseResponseResource removeDevice(Long id);

}
