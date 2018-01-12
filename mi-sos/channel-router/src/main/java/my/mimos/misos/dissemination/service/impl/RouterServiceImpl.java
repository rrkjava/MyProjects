/**
 * 
 */
package my.mimos.misos.dissemination.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.command.DisseminationCommand;
import my.mimos.misos.common.enums.ChannelType;
import my.mimos.misos.common.enums.StatusType;
import my.mimos.misos.dissemination.router.ChannelRouterBase;
import my.mimos.misos.dissemination.router.ChannelRouterFactory;
import my.mimos.misos.dissemination.service.RouterService;
import my.mimos.misos.domain.entity.trx.ChannelMessage;
import my.mimos.misos.domain.entity.trx.Message;
import my.mimos.misos.domain.resource.ChannelResponseResource;
import my.mimos.misos.domain.resource.DisseminationRequestResource;
import my.mimos.misos.domain.resource.DisseminationResponseResource;
import my.mimos.misos.mapper.DisseminationResourceMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@Log4j
@Component
public class RouterServiceImpl implements RouterService {
	
	public static final Logger performanceLog=LoggerFactory.getLogger("perf-log");
	
	@Autowired
	DisseminationResourceMapper mapper;
		
	@Autowired
	ChannelRouterFactory routerFactory;
	
	@Autowired
	DisseminationCommand disseminationCommand;
	
	/* (non-Javadoc)
	 * @see my.mimos.misos.dissemination.service.RouterService#route(my.mimos.misos.domain.resource.DisseminationRequestResource)
	 * 
	 * 1. Transform the Request Message to Entity class using mapper
	 * 2. Router will determine the channel handler for every target channel
	 * 3. Post the message to the channel handler
	 * 4. Channel handler will return the status of the message
	 * 
	 */
	@Override
	public DisseminationResponseResource route(DisseminationRequestResource req) {
		
		DisseminationResponseResource res = new DisseminationResponseResource();
		List<ChannelResponseResource> channelResponseList = new ArrayList<ChannelResponseResource>(0);
		
		try {
		
			// 1. Transform to Message
			Message message = mapper.getMapperFacade().map(req,  Message.class);
			Long recipientCount = 0L;
			
			res.setStatus("SUCCESS.");
			res.setStatusCode("S0010");
			res.setStatusType(StatusType.SUCCESS);
			
			Message dbMessage= disseminationCommand.findByIocMessageId(message.getIocMesageId());
			
			performanceLog.info("----------------------------------------------------------------------------------");
			performanceLog.info("Performance check for ioc dissemination message id : " + message.getIocMesageId());
			performanceLog.info("----------------------------------------------------------------------------------");
			
			List<ChannelMessage> channelMessages = disseminationCommand.findchannelMessages(dbMessage.getId());
			
			log.debug("Message : " + message.toString());						
			log.debug("No. of channel to route : " + message.getChannelMesages().size());
			long startTimeAllChannels = System.currentTimeMillis();
			
			for (ChannelMessage channelMessage : message.getChannelMesages()) {
				
				String targetChannel = channelMessage.getTargetChannelTypeId();
				
				log.debug("Target channel : " + targetChannel);
				log.debug("Target group : " + channelMessage.getSubscriberTypeId());
				
				ChannelResponseResource channelResponse = new ChannelResponseResource();
				
				// 2. Get the channel handler from the factory
				ChannelRouterBase channel = routerFactory.getChannelRouter(targetChannel);
				if (channel != null) {
					
					long startTime = System.currentTimeMillis();
					
					channelMessage.setMessage(message);
					// 3. Post the message to the channel handler
					channelResponse = channel.post(channelMessage);
					
					log.info("******************************************************************");
					log.info("Response from channel router : " + channelResponse.getStatus() +
							" for total recipients : " + channelResponse.getRecipientCount() );
					log.info("******************************************************************");
					
					long estimatedTime = System.currentTimeMillis() - startTime;
					performanceLog.info("Time taken for Channel " + channelMessage.getTargetChannelTypeId() +" : "+estimatedTime + "ms");
					
					
					if(channelResponse.getStatusType() == StatusType.ERROR) {
						res.setStatus("Channel error.");
						res.setStatusCode("E0021");
						res.setStatusType(StatusType.ERROR);
					}
					for (ChannelMessage channelMsg : channelMessages) {
						String channeltype = channelResponse.getChannelType();
						if (channelMsg.getTargetChannelTypeId().equals(channeltype)) {
							if (channelResponse.getStatusType() == StatusType.SUCCESS) {
								channelMsg.setPublishStatus(true);
							} else {
								channelMsg.setPublishStatus(false);
							}
							if (channeltype.equals(channeltype.equals(ChannelType.CH_PUBLIC_PORTAL.toString()))) {
								channelMsg.setRecipientCount(1L);
							} else {	
								if(null!=channelResponse.getRecipientCount()){
									recipientCount = Long.valueOf(channelResponse.getRecipientCount());
									channelMsg.setRecipientCount(recipientCount);
								}else{
									channelMsg.setRecipientCount(0L);
								}
							}
							disseminationCommand.saveChannelMessageStatus(channelMsg);
						}
					}
				} else {
					log.debug("Target dissemination channel not found.");
					// Set the error status in the channel response
					channelResponse.setStatus("Target dissemination channel not found.");
					channelResponse.setStatusCode("E0021");
					channelResponse.setStatusType(StatusType.ERROR);
					channelResponse.setChannelType(targetChannel);
					channelResponse.setTargetUserGroups(channelMessage.getSubscriberTypeId());
				}
				
				channelResponseList.add(channelResponse);
			}
			
			res.setChannels(channelResponseList);
			

			long estimatedTimeAllChannels = System.currentTimeMillis() - startTimeAllChannels;
			performanceLog.info("Time taken for All Channels  : "+estimatedTimeAllChannels + "ms");
			
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			
			res.setStatus("Unable to route the channel message.");
			res.setStatusCode("E0021");
			res.setStatusType(StatusType.ERROR);
			
			
		} finally {
			
			res.setEventId(req.getEventId());
			res.setMessageId(req.getMessageId());
			res.setMessageDate(req.getMessageDate());
			res.setOriginator(req.getOriginator());
			res.setUserId(req.getUserId());
			res.setMessageType(req.getMessageType());
			
		}
		
		return res;
	}

}
