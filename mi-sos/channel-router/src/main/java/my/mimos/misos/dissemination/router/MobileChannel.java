/**
 * 
 */
package my.mimos.misos.dissemination.router;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.common.enums.StatusType;
import my.mimos.misos.dissemination.service.MobileService;
import my.mimos.misos.domain.channel.MobileChannelRequestResource;
import my.mimos.misos.domain.channel.MobileChannelResponseResource;
import my.mimos.misos.domain.entity.trx.ChannelMessage;
import my.mimos.misos.domain.resource.ChannelResponseResource;
import my.mimos.misos.mapper.ChannelMapper;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
@Log4j
public class MobileChannel extends ChannelRouterBase {

	@Autowired
	MobileService mobileService;
	/**
	 * @param mapper
	 */
	public MobileChannel(ChannelMapper channelMapper) {
		super(channelMapper);
	}

	/* (non-Javadoc)
	 * @see my.mimos.misos.dissemination.router.ChannelRouterBase#post(my.mimos.misos.domain.channel.ChannelBaseResource)
	 */
	@Override
	public ChannelResponseResource post(ChannelMessage channelMessage) {
		// TODO Auto-generated method stub
		MobileChannelResponseResource res = new MobileChannelResponseResource();
		
		try {
			
			MobileChannelRequestResource req = channelMapper.getMapperFacade().map(channelMessage,  MobileChannelRequestResource.class);
			log.debug("IOC_MESSAGE_ID : " + req.getIocMessageId());
			
			res = mobileService.pushNotification(req);
			res.setChannelType("CH_04");
		
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
