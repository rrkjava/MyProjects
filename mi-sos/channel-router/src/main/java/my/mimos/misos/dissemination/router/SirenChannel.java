/**
 * 
 */
package my.mimos.misos.dissemination.router;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.common.enums.StatusType;
import my.mimos.misos.dissemination.service.SirenService;
import my.mimos.misos.domain.channel.SirenChannelRequestResource;
import my.mimos.misos.domain.channel.SirenChannelResponseResource;
import my.mimos.misos.domain.entity.trx.ChannelMessage;
import my.mimos.misos.domain.resource.ChannelResponseResource;
import my.mimos.misos.mapper.ChannelMapper;

/**
 * @author krishna.redabotu
 *
 */
@Log4j
public class SirenChannel extends ChannelRouterBase {

	@Autowired
	SirenService sirenService;
	
	public SirenChannel(ChannelMapper channelMapper) {
		super(channelMapper);
	}

	/* (non-Javadoc)
	 * @see my.mimos.misos.dissemination.router.ChannelRouterBase#post(my.mimos.misos.domain.entity.trx.ChannelMessage)
	 */
	@Override
	public ChannelResponseResource post(ChannelMessage channelMessage) {
		
		SirenChannelResponseResource res = new SirenChannelResponseResource();
		
		try {
			SirenChannelRequestResource req = channelMapper.getMapperFacade().map(channelMessage,SirenChannelRequestResource.class);
			log.debug("IOC_MESSAGE_ID : " + req.getIocMessageId());
			res = sirenService.sendSiren(req);
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