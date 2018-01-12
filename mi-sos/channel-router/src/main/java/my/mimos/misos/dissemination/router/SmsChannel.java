/**
 * 
 */
package my.mimos.misos.dissemination.router;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.common.enums.StatusType;
import my.mimos.misos.dissemination.service.SmsService;
import my.mimos.misos.domain.channel.SmsChannelRequestResource;
import my.mimos.misos.domain.channel.SmsChannelResponseResource;
import my.mimos.misos.domain.entity.trx.ChannelMessage;
import my.mimos.misos.domain.resource.ChannelResponseResource;
import my.mimos.misos.mapper.ChannelMapper;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
@Log4j
public class SmsChannel extends ChannelRouterBase {
	
	@Autowired
	SmsService smsService;
	
	public SmsChannel(ChannelMapper channelMapper) {
		super(channelMapper);
	}
	
	/* (non-Javadoc)
	 * @see my.mimos.misos.dissemination.router.ChannelRouterBase#post(my.mimos.misos.domain.entity.trx.ChannelMessage)
	 * 
	 * 1. Transform from ChannelMessage to FbChannelRequestResource
	 * 2. Post the message to SMS Channel service
	 * 
	 */
	@Override
	public ChannelResponseResource post(ChannelMessage channelMessage) {
		// TODO Auto-generated method stub
		SmsChannelResponseResource res = new SmsChannelResponseResource();
		
		try {
			
			SmsChannelRequestResource req = channelMapper.getMapperFacade().map(channelMessage, SmsChannelRequestResource.class);
			log.debug("IOC_MESSAGE_ID : " + req.getIocMessageId());
			
			res = smsService.sendSms(req);
			
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			
			res.setChannelType(channelMessage.getTargetChannelTypeId());
			res.setTargetUserGroups(channelMessage.getSubscriberTypeId());
			res.setStatus("Target dissemination channel unreachable.");
			res.setStatusCode("E0021");
			res.setStatusType(StatusType.ERROR);
			
		}
		return res;
	}

}
