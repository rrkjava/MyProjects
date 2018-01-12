package my.mimos.mdc.service;

import java.util.List;

public interface AsyncService {

	public void pushNotificationToDevices(Long queryId,List<Long> userIds,String message,String Notifytype);
	public void sendEmailNotification(Long queryId,List<Long> userIds,String message);
	
	
}
