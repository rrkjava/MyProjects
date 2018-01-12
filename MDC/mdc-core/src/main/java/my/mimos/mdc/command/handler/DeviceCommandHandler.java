package my.mimos.mdc.command.handler;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.querydsl.core.BooleanBuilder;

import my.mimos.mdc.command.DeviceCommand;
import my.mimos.mdc.domain.entity.MobileDevice;
import my.mimos.mdc.domain.entity.QMobileDevice;
import my.mimos.mdc.domain.repository.DeviceRepository;
import my.mimos.mdc.resources.device.SearchDeviceRequestResource;

@Component
public class DeviceCommandHandler implements DeviceCommand{
	
	@Autowired
	DeviceRepository deviceRepository;

	@Override
	public MobileDevice addDevice(MobileDevice device) {
		MobileDevice addedDevice = null;
		try{
			addedDevice = deviceRepository.save(device);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return addedDevice;
	}

	@Override
	public MobileDevice findDevice(Long deviceId) {
		MobileDevice existing = null;
		try{
			existing = deviceRepository.findOne(deviceId);
			if(null == existing){
				throw new IllegalStateException("device doesnot exist");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return existing;
	}
	
	@Override
	public List<MobileDevice> listDevices() {
		List<MobileDevice> devices = null;
		try{
			devices = deviceRepository.findAll();			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return devices;
	}

	@Override
	public List<MobileDevice> searchDevices(SearchDeviceRequestResource searchCriteria) {
		List<MobileDevice> filteredDevices = null;
		try{
			QMobileDevice device = QMobileDevice.mobileDevice;
			
			BooleanBuilder where = new BooleanBuilder();
			
			if(null != searchCriteria.getDeviceId()){
				where.and(device.deviceId.eq(searchCriteria.getDeviceId()));
			}
			if(StringUtils.isNotBlank(searchCriteria.getDeviceType())){
				where.and(device.deviceType.like(searchCriteria.getDeviceType()));
			}
			if(StringUtils.isNotBlank(searchCriteria.getDeviceToken())){
				where.and(device.deviceToken.like(searchCriteria.getDeviceToken()));
			}
			if(StringUtils.isNotBlank(searchCriteria.getDeviceStatus())){
				where.and(device.deviceStatus.like(searchCriteria.getDeviceStatus()));
			}
			if(null !=  searchCriteria.getUserId()){
				where.and(device.user.userId.eq(searchCriteria.getUserId()));
			}	
			if(null != searchCriteria.getUserIds() && searchCriteria.getUserIds().size()!= 0){
				where.and(device.user.userId.in(searchCriteria.getUserIds()));
			}
			if(where.hasValue()){
				filteredDevices = (List<MobileDevice>) deviceRepository.findAll(where);	
			}
			}catch(Exception ex){
				ex.printStackTrace();		
				throw new RuntimeException(ex);
			}
		return filteredDevices;
	}

	@Override
	public void deleteDevice(Long id) {
		try{
			deviceRepository.delete(id);			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}		
	}

	@Override
	public MobileDevice findByDeviceToken(String deviceToken) {
		MobileDevice device = null;
		try{
			device = deviceRepository.findByDeviceToken(deviceToken);		
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return device;	
	}

}
