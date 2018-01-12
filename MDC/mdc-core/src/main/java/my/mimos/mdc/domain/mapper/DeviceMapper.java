package my.mimos.mdc.domain.mapper;

import org.springframework.stereotype.Component;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import my.mimos.mdc.domain.entity.MobileDevice;
import my.mimos.mdc.resources.device.DeviceResource;
@Component
public class DeviceMapper extends ConfigurableMapper {	
	
	MapperFacade mapperFacade;
	protected void configure(MapperFactory factory) {
		factory.classMap(MobileDevice.class, DeviceResource.class)		
		    .field("deviceId", "deviceId")
		    .field("deviceType", "deviceType")
		    .field("deviceToken", "deviceToken")
		    .field("deviceStatus", "deviceStatus")			    
		    .field("createdDate", "createdDate")
		    .field("user.userId", "userId")
		    .byDefault()
		    .register(); 
		
			mapperFacade = factory.getMapperFacade();
		}
		
		public MapperFacade getMapperFacade(){
			return mapperFacade;
		}

}
