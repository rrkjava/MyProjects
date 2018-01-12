/**
 * 
 */
package my.mimos.misos.dissemination.router;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.common.enums.StatusType;
import my.mimos.misos.dissemination.service.TwitterService;
import my.mimos.misos.domain.channel.TwitterChannelRequestResource;
import my.mimos.misos.domain.channel.TwitterChannelResponseResource;
import my.mimos.misos.domain.entity.trx.ChannelMessage;
import my.mimos.misos.domain.resource.ChannelResponseResource;
import my.mimos.misos.mapper.ChannelMapper;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
@Log4j
public class TwitterChannel extends ChannelRouterBase {
	
	@Autowired
	TwitterService twitterService;
	
	public TwitterChannel(ChannelMapper channelMapper) {
		super(channelMapper);
	}
	
	/* (non-Javadoc)
	 * @see my.mimos.misos.dissemination.router.ChannelRouterBase#post(my.mimos.misos.domain.entity.trx.ChannelMessage)
	 * 
	 * 1. Post the message to Twitter Channel service
	 * 
	 */
	@Override
	public ChannelResponseResource post(ChannelMessage channelMessage) {
		// TODO Auto-generated method stub
		TwitterChannelResponseResource res = null;
		
		try {
			
			TwitterChannelRequestResource req = channelMapper.getMapperFacade().map(channelMessage, TwitterChannelRequestResource.class);
			res=twitterService.postTweet(req);
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
