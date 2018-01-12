package my.mimos.mdc.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;

import lombok.extern.log4j.Log4j;
import my.mimos.mdc.domain.entity.MobileDevice;
import my.mimos.mdc.domain.entity.NotificationTracker;
import my.mimos.mdc.domain.repository.NotificationTrackerRepository;
import my.mimos.mdc.service.PushNotificationService;
import my.mimos.mdc.utils.MdcConstants;
/**
 * 
 * @author beaula.fernandez
 *
 */

@Log4j
@Component
public class PushNotificationServiceImpl implements PushNotificationService{

	@Value("${GCM_API_KEY}")
	private String GCM_API_KEY;
	
	@Autowired
	NotificationTrackerRepository repo;
	

	@Override
	public void sendNotificationToIOSDevice(String message,List<MobileDevice> deviceList,Long queryId,String NotifyType) {
		try{
			// Load the APNS certificate
			ClassLoader classLoader = PushNotificationServiceImpl.class.getClassLoader();
			URL url = classLoader.getResource("CertificatesAPN_MDC.p12");
			
			File certFile = new File(url.getFile());
			FileInputStream inputStream = new FileInputStream(certFile);

			ApnsServiceBuilder serviceBuilder = APNS.newService();
			serviceBuilder.withCert(inputStream, "mimos123").withSandboxDestination();

			// Setup the pay load
			ApnsService service = serviceBuilder.build();
			
			String NotifyId=new String();
			if (MdcConstants.NOTIFY_TYPE_BROADCAST.equalsIgnoreCase(NotifyType)) {
				NotifyId = "messageId";
			} else {
				NotifyId = "queryId";
			}
			
			String payload = APNS.newPayload()	
					.alertBody(message)
					.sound("default")	
					.customField(NotifyId, queryId)				
					.build();		
			
			//TEST DEVICE
			//service.push("55875cc18018109f3823eaef6d2276381e520239d814cca2f831dab40ef8b0fb", payload);
			List<Long> deviceIdList = new ArrayList<>();
			
			log.info("Sending notification to iOS devices...");
			for(MobileDevice device : deviceList) {
				service.push(StringUtils.trim(device.getDeviceToken()), payload);
				deviceIdList.add(device.getDeviceId());
			}
			log.info("Push notification to iOS devices completed...");
			
			//UPDATE NOTIFICATION TRACKER
			NotificationTracker notifications = new NotificationTracker();
			notifications.setDevices(StringUtils.join(deviceIdList, ','));
			notifications.setMessage(message);
			notifications.setQueryId(String.valueOf(queryId));
			notifications.setNotificationDate(new Date());
			repo.save(notifications);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	@Override
    public void sendNotificationToAndroidDevice(String message,List<MobileDevice> deviceList, Long queryId,String NotifyType) {
		final int retries = 3;	
		List<Long> deviceIdList = new ArrayList<>();
		try {	
			if(null != GCM_API_KEY && StringUtils.isNotBlank(GCM_API_KEY)){
			    Sender sender = new Sender(GCM_API_KEY);
			    
			    String NotifyId=new String();
			    if (MdcConstants.NOTIFY_TYPE_BROADCAST.equalsIgnoreCase(NotifyType)) {
					NotifyId = "messageId";
				} else {
					NotifyId = "queryId";
				}
			    
			    Message msg = new Message.Builder()
			    		.addData("title", "MECA") 
			    		.addData("body", message)
			    		.addData(NotifyId, String.valueOf(queryId))
			    		.build();
			    
			    log.info("Sending notification to Android devices...");; 
			    for(MobileDevice device : deviceList) {
		    		sender.send(msg, device.getDeviceToken(), retries);
		    		deviceIdList.add(device.getDeviceId());
				}
			    log.info("Push notification to Android devices completed...");
			    
			    //UPDATE NOTIFICATION TRACKER
				NotificationTracker notifications = new NotificationTracker();
				notifications.setDevices(StringUtils.join(deviceIdList, ','));
				notifications.setMessage(message);
				notifications.setQueryId(String.valueOf(queryId));
				notifications.setNotificationDate(new Date());
				repo.save(notifications);
				
			}else{
				log.info("Unable to push notification to android - missing GCM API key.");
			}
	    }catch (Exception e) {
               e.printStackTrace();                  
        } 
    }

	
	

	/*public static void main(String args[]){
		new PushNotificationServiceImpl().sendNotificationToIOSDevice("Testing notification", null,8000L);
		new PushNotificationServiceImpl().sendNotificationToAndroidDevice(null, "Testing notification", (long) 0000);
	}*/
}
