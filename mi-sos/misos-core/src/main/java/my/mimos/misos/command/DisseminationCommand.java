/**
 * 
 */
package my.mimos.misos.command;

import java.util.List;

import my.mimos.misos.domain.entity.trx.ChannelMessage;
import my.mimos.misos.domain.entity.trx.ChannelRecipient;
import my.mimos.misos.domain.entity.trx.Message;
import my.mimos.misos.domain.resource.DisseminationRequestResource;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
public interface DisseminationCommand {

	Message registerMessage(DisseminationRequestResource req) throws RuntimeException;
	
	List<ChannelRecipient> getChannelRecipient(String iocMessageId, String channeltype);
	
	public Message findByIocMessageId(String iocMessageId) throws RuntimeException;
	
	public List<ChannelMessage> findchannelMessages(Long channelMessageId) throws RuntimeException;
	
	public void saveChannelMessageStatus(ChannelMessage channelMessage) throws RuntimeException;
	
	public Long getReciepentCount(String channelType,Long messageId) throws RuntimeException;

	void registerChannelRecipient(List<ChannelRecipient> channelRecipients);
}
