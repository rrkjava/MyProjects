/**
 * 
 */
package my.mimos.misos.command.handler;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.command.DisseminationCommand;
import my.mimos.misos.domain.entity.trx.ChannelMessage;
import my.mimos.misos.domain.entity.trx.ChannelRecipient;
import my.mimos.misos.domain.entity.trx.Message;
import my.mimos.misos.domain.repository.trx.ChannelRecipientRepository;
import my.mimos.misos.domain.repository.trx.ChannelRepository;
import my.mimos.misos.domain.repository.trx.MesageRepository;
import my.mimos.misos.domain.resource.DisseminationRequestResource;
import my.mimos.misos.mapper.DisseminationResourceMapper;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
@Log4j
@Component
public class DisseminationCommandHandler implements DisseminationCommand {
	@Autowired
	DisseminationResourceMapper resourceMapper;
	
	@Autowired
	MesageRepository messageRepo;		
	
	@Autowired
	ChannelRecipientRepository channelRecipientRepo;
	
	@Autowired
	ChannelRepository channelRepo;	
	
	@Override
	public Message registerMessage(DisseminationRequestResource req) throws RuntimeException {
		Message ret = null;
		
		try {
			// Transform the request into entity
			Message message = resourceMapper.getMapperFacade().map(req, Message.class);
			
			// Check if same message already exist
			Message existing = messageRepo.findByIocMesageId(message.getIocMesageId());
			
			if(existing == null) {
				log.debug("Saving message in the database.");				
				message.associateAllChannel();
				ret = messageRepo.save(message);
				
			} else {
				// Same message already exist, throw error
				throw new RuntimeException("Duplicate message found.");
				
			}
			
		} finally {
			
		}
		
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * @see my.mimos.misos.command.DisseminationCommand#registerChannelRecipient(java.util.List)
	 */
	@Override
	public void registerChannelRecipient(List<ChannelRecipient> channelRecipients) {		
		try{
			//channelRecipients.forEach(recipient-> channelRecipientRepo.save(recipient));
			channelRecipientRepo.save(channelRecipients);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see my.mimos.misos.command.DisseminationCommand#getChannelRecipient(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ChannelRecipient> getChannelRecipient(String iocMessageId, String channeltype) {
		// TODO Auto-generated method stub
		List<ChannelRecipient> ret = null;
		
		try {
			
			ret = channelRecipientRepo.findByIocMessageIdAndChannelType(iocMessageId, channeltype);
			
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			
		} finally {
			
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see my.mimos.misos.command.DisseminationCommand#findMessage(java.lang.String)
	 */
	@Override
	public Message findByIocMessageId(String iocMessageId) throws RuntimeException {
		Message res = null;
		try {
			Message messsage = messageRepo.findByIocMesageId(iocMessageId);
			if (messsage != null) {
				res = messsage;
			} else {
				throw new RuntimeException("iocMessageId not exists");
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw new RuntimeException(e);
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see my.mimos.misos.command.DisseminationCommand#findchannelMessages(java.lang.Long)
	 */
	@Override
	public List<ChannelMessage> findchannelMessages(Long channelMessageId) throws RuntimeException {
		List<ChannelMessage> res = null;
		try {
			List<ChannelMessage> messsage = channelRepo.findByMessageId(channelMessageId);
			if (messsage != null) {
				res = messsage;
			} else {
				throw new RuntimeException("iocMessageId not exists");
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw new RuntimeException(e);
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see my.mimos.misos.command.DisseminationCommand#saveChannelMessageStatus(my.mimos.misos.domain.entity.trx.ChannelMessage)
	 */
	@Override
	public void saveChannelMessageStatus(ChannelMessage channelMessage) throws RuntimeException {
		Date date = new Date();
		try {
			ChannelMessage channelMsg = channelRepo.findById(channelMessage.getId());
			if (channelMsg != null) {
				channelMsg.setPublishStatus(channelMessage.getPublishStatus());
				channelMsg.setPublishDate(date);
				channelMsg.setRecipientCount(channelMessage.getRecipientCount());
				channelRepo.save(channelMsg);
			} else {
				throw new RuntimeException("Message not exists");
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see my.mimos.misos.command.DisseminationCommand#getReciepentCount(java.lang.String, java.lang.Long)
	 */
	@Override
	public Long getReciepentCount(String channelType, Long messageId) throws RuntimeException {

		// TODO Auto-generated method stub
		Long res = 0L;

		try {
			res = channelRecipientRepo.countByChannelTypeandMessageId(channelType, messageId);
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
		}
		return res;
	}
}
