package my.mimos.mdc.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import my.mimos.mdc.command.DeviceCommand;
import my.mimos.mdc.command.UserManagementCommand;
import my.mimos.mdc.domain.entity.MobileDevice;
import my.mimos.mdc.domain.entity.User;
import my.mimos.mdc.resources.device.SearchDeviceRequestResource;
import my.mimos.mdc.resources.user.SearchUsersRequestResource;
import my.mimos.mdc.service.AsyncService;
import my.mimos.mdc.service.EmailService;
import my.mimos.mdc.service.PushNotificationService;
import my.mimos.mdc.utils.MdcConstants;
/**
 * 
 * @author beaula.fernandez
 * Note : The following ports should be enabled on the server where the war has been deployed
 * SMTP - smtp.gmail.com -  587 
 * Apple Push Notification - gateway.sandbox.push.apple.com - 2195 
 * Android Push Notification - 
 */


@Component
public class AsyncServiceImpl implements AsyncService{
	
	@Autowired
	DeviceCommand deviceCommand;
	
	@Autowired
	PushNotificationService pushService;
	
	@Autowired
	UserManagementCommand userCommand;
	
	@Autowired
	EmailService emailService;

	@Async("notificationTaskExecutor")
	public void pushNotificationToDevices(Long queryId,List<Long> userIds,String message,String NotifyType){
		try{	
			if(null != userIds){
			userIds = userIds.stream().distinct().collect(Collectors.toList());	
			SearchDeviceRequestResource searchUserDevice = new SearchDeviceRequestResource();
			searchUserDevice.setUserIds(userIds);
			searchUserDevice.setDeviceStatus(MdcConstants.STATUS_ACTIVE);	
			List<MobileDevice> deviceList = deviceCommand.searchDevices(searchUserDevice);
			
			if( null != deviceList && deviceList.size()>0){
				Map<Object, List<MobileDevice>> mapped = deviceList.stream()
		                .collect(Collectors.groupingBy(li -> li.getDeviceType())); 
				
				List<MobileDevice> androidDeviceList = mapped.get(MdcConstants.DEVICE_TYPE_ANDROID);
				List<MobileDevice> iOSDeviceList = mapped.get(MdcConstants.DEVICE_TYPE_IOS);
				
				if(null != iOSDeviceList && iOSDeviceList.size()>0)
				pushService.sendNotificationToIOSDevice(message, iOSDeviceList, queryId,NotifyType);
				
				if(null != androidDeviceList && androidDeviceList.size()>0)
				pushService.sendNotificationToAndroidDevice(message,androidDeviceList,queryId,NotifyType);				
			}	
		}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	

	@Async("notificationTaskExecutor")
	public void sendEmailNotification(Long queryId,List<Long> userIds,String message){
		try{
			if(null != userIds){
				userIds = userIds.stream().distinct().collect(Collectors.toList());	
				SearchUsersRequestResource searchCriteria = new SearchUsersRequestResource();
				searchCriteria.setUserIds(userIds);
				List<User> users = userCommand.searchUsers(searchCriteria);
				List<String> emails = null;
				if(null!= users && users.size()>0){
					emails = users.stream().map(User::getEmailId).collect(Collectors.toList());
				}
				emailService.sendEmail(emails,message);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
