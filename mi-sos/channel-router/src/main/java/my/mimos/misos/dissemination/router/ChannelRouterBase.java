/**
 * 
 */
package my.mimos.misos.dissemination.router;

import my.mimos.misos.domain.entity.trx.ChannelMessage;
import my.mimos.misos.domain.resource.ChannelResponseResource;
import my.mimos.misos.mapper.ChannelMapper;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
public abstract class ChannelRouterBase {
	protected ChannelMapper channelMapper;
	
	public ChannelRouterBase() {}
	
	public ChannelRouterBase(ChannelMapper channelMapper) {
		this.channelMapper = channelMapper;
	}
	
	public abstract ChannelResponseResource post(ChannelMessage channelMessage);
}
