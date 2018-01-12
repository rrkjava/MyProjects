package my.mimos.mdc.service;

import java.util.List;

import my.mimos.mdc.domain.entity.MobileDevice;

/**
 * 
 * @author beaula.fernandez
 *
 */


public interface PushNotificationService {
	
	public void sendNotificationToIOSDevice(String message,List<MobileDevice> deviceList,Long queryId,String NotifyType);
	public void sendNotificationToAndroidDevice(String message,List<MobileDevice> deviceList,Long queryId,String NotifyType);

}
