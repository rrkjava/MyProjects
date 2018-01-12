/**
 * 
 */
package my.mimos.misos.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.command.DisseminationCommand;
import my.mimos.misos.common.enums.ChannelType;
import my.mimos.misos.common.enums.StatusType;
import my.mimos.misos.dissemination.service.RouterService;
import my.mimos.misos.domain.entity.trx.Message;
import my.mimos.misos.domain.integrationservice.ApplicationAlertResource;
import my.mimos.misos.domain.resource.ChannelResponseResource;
import my.mimos.misos.domain.resource.DisseminationRequestResource;
import my.mimos.misos.domain.resource.DisseminationResponseResource;
import my.mimos.misos.service.IntegrationService;
import my.mimos.misos.web.service.DisseminationService;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@Log4j
@Component
public class DisseminationServiceImpl implements DisseminationService {
	
	@Autowired
	IntegrationService intergrationService;
	
	@Autowired
	DisseminationCommand disseminationCommand;
	
	@Autowired
	RouterService routerService;
	
	/* (non-Javadoc)
	 * @see my.mimos.misos.web.service.DisseminationService#disseminate(my.mimos.misos.domain.resource.DisseminationRequestResource)
	 * 
	 * 1. Post the message to the Channel Router
	 * 2. Channel Router will return the status of every channel
	 * 
	 */
	@Override
	public DisseminationResponseResource disseminate(DisseminationRequestResource req) {
		// TODO Auto-generated method stub
		
		DisseminationResponseResource res = new DisseminationResponseResource();

		try {
			log.debug("Resource : " + req.toString());
						
			// Register the message in the Db
			Message message = disseminationCommand.registerMessage(req);			
			
			// Pass the request to channel router			
			res=routerService.route(req);
			log.debug("Response : " + res.toString());
			log.debug("Dissemination message sent to channel router.");
			
			//Alert Message service
			List<ChannelResponseResource> channelsResponse= res.getChannels();
			ApplicationAlertResource alert= new ApplicationAlertResource();
			List<String> statusMsg=new ArrayList<String>();
			int i=0;
			int j=0;
			for (ChannelResponseResource channelResp : channelsResponse) {
				if(channelResp.getStatusType().equals(StatusType.SUCCESS)){
					i=i+1;
				} else {
					j=j+1;
				}
				
				String channelName = ChannelType.nameByChannelType(channelResp.getChannelType());
				String alertMessage=channelName + " "+ channelResp.getStatusType() + " with " + channelResp.getRecipientCount() + " recipients";
				statusMsg.add(alertMessage);
			}
			String alertMessage= i + " "+ StatusType.SUCCESS + " <br/> " + j + " "+ StatusType.ERROR;
			alert.setAlertId(res.getMessageId());
			alert.setAlertMessage(alertMessage);
			alert.setMessages(statusMsg);
			intergrationService.applicationAlert(alert);
			
			//res.setStatusType(StatusType.SUCCESS);
			//res.setStatusCode("S0010");
		} catch (RuntimeException ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			
			res.setStatusType(StatusType.ERROR);
			res.setStatusCode("E0031");
			res.setStatus(ex.getMessage());
			
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			
			res.setStatusType(StatusType.ERROR);
			res.setStatusCode("E0021");
			res.setStatus("Unable to contact channel router.");
			
		} finally {
			
		}

		return res;
	}

}
