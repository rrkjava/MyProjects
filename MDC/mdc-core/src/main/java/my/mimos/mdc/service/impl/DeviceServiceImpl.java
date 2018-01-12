package my.mimos.mdc.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import my.mimos.mdc.command.DeviceCommand;
import my.mimos.mdc.command.UserManagementCommand;
import my.mimos.mdc.domain.entity.MobileDevice;
import my.mimos.mdc.domain.entity.User;
import my.mimos.mdc.domain.mapper.DeviceMapper;
import my.mimos.mdc.enums.StatusType;
import my.mimos.mdc.resources.BaseResponseResource;
import my.mimos.mdc.resources.device.AddDeviceRequestResource;
import my.mimos.mdc.resources.device.DeviceResource;
import my.mimos.mdc.resources.device.DeviceResponseResource;
import my.mimos.mdc.resources.device.DevicesResponseResource;
import my.mimos.mdc.resources.device.SearchDeviceRequestResource;
import my.mimos.mdc.resources.device.UpdateDeviceRequestResource;
import my.mimos.mdc.service.DeviceService;
import my.mimos.mdc.service.UserManagementService;
import my.mimos.mdc.utils.MdcConstants;

@Component
public class DeviceServiceImpl implements DeviceService{
	
	@Autowired
	DeviceCommand deviceCommand;
	
	@Autowired
	DeviceMapper deviceMapper;	

	@Autowired
	UserManagementCommand userManagementCommand;
	
	@Autowired
	UserManagementService userService;

	@Override
	public DeviceResponseResource addDevice(AddDeviceRequestResource request) {
		DeviceResponseResource response = new DeviceResponseResource();
		try{
			MobileDevice device = new MobileDevice();			
			//search if device token already exists
			SearchDeviceRequestResource searchCriteria = new SearchDeviceRequestResource();
			searchCriteria.setDeviceToken(request.getDeviceToken());
			List<MobileDevice> existingDevices = deviceCommand.searchDevices(searchCriteria);
			if(null != existingDevices && !existingDevices.isEmpty()){
				device = existingDevices.get(0);
				device.setUser(null);
			}else{
				device.setDeviceToken(request.getDeviceToken());
			}
			
			if(request.getDeviceType().contains(MdcConstants.DEVICE_TYPE_ANDROID)){
				device.setDeviceType(MdcConstants.DEVICE_TYPE_ANDROID);
			}else if(request.getDeviceType().contains(MdcConstants.DEVICE_TYPE_IOS)){
				device.setDeviceType(MdcConstants.DEVICE_TYPE_IOS);				
			}else{
				throw new RuntimeException("invalid device type.please specify ANDROID/IOS");
			}		
			device.setDeviceStatus(MdcConstants.STATUS_ACTIVE);
			device.setCreatedDate(new Date());		
			
			MobileDevice addedDevice = deviceCommand.addDevice(device);
			DeviceResource deviceResource = deviceMapper.getMapperFacade().map(addedDevice,DeviceResource.class);
			response.setDevice(deviceResource);
			
			response.setStatus("device added");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);				
		}
		catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}

	@Override
	public DeviceResponseResource updateDevice(UpdateDeviceRequestResource request) {
		DeviceResponseResource response = new DeviceResponseResource();
		try{
			MobileDevice device =  deviceCommand.findDevice(request.getDeviceId());
			
			if(StringUtils.isNotBlank(request.getDeviceType()))
				device.setDeviceType(request.getDeviceType());
			
			if(StringUtils.isNotBlank(request.getDeviceToken())){				
				device.setDeviceToken(request.getDeviceToken());
			}
			
			if(StringUtils.isNotBlank(request.getDeviceStatus()))
				device.setDeviceToken(request.getDeviceStatus());
			
			if(request.isUpdateUser()){
				User loggedInUser = userService.getUserDetailsFromSecurityContext();
				device.setUser(loggedInUser);
			}
			
			MobileDevice updatedDevice = deviceCommand.addDevice(device);
			DeviceResource deviceResource = deviceMapper.getMapperFacade().map(updatedDevice,DeviceResource.class);
			response.setDevice(deviceResource);
					
			response.setStatus("device updated");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);				
		}
		catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}

	@Override
	public DeviceResponseResource getDevice(Long deviceId) {
		DeviceResponseResource response = new DeviceResponseResource();
		try{
			MobileDevice device =  deviceCommand.findDevice(deviceId);
			DeviceResource deviceResource = deviceMapper.getMapperFacade().map(device,DeviceResource.class);
			response.setDevice(deviceResource);
			
			response.setStatus("device listed");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}

	@Override
	public DevicesResponseResource listDevices() {
		DevicesResponseResource response = new DevicesResponseResource();
		try{
			List<MobileDevice> devices =  deviceCommand.listDevices();
			List<DeviceResource> deviceList = deviceMapper.getMapperFacade().mapAsList(devices, DeviceResource.class);
			response.setDevices(deviceList);
			
			response.setStatus("device listed");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);				
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}

	@Override
	public DevicesResponseResource searchDevices(SearchDeviceRequestResource searchCriteria) {
		DevicesResponseResource response = new DevicesResponseResource();
		try{
			List<MobileDevice> devices =  deviceCommand.searchDevices(searchCriteria);
			List<DeviceResource> deviceList = deviceMapper.getMapperFacade().mapAsList(devices, DeviceResource.class);
			response.setDevices(deviceList);
			
			response.setStatus("device listed");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}

	@Override
	public BaseResponseResource removeDevice(Long id) {
		DevicesResponseResource response = new DevicesResponseResource();
		try{
			deviceCommand.deleteDevice(id);			
			response.setStatus("device deleted");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);	
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);	
		}
		return response;
	}

	
}
