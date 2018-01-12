/**
 * 
 */
package my.mimos.misos.dissemination.router;

import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.domain.channel.MapChannelResponseResource;
import my.mimos.misos.domain.entity.trx.ChannelMessage;
import my.mimos.misos.domain.resource.ChannelResponseResource;
import my.mimos.misos.mapper.ChannelMapper;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
@Log4j
public class MapChannel extends ChannelRouterBase {
//	@Autowired
//	MapConfig config;
	
	public MapChannel(ChannelMapper channelMapper) {
		super(channelMapper);
	}
	
	/* (non-Javadoc)
	 * @see my.mimos.misos.dissemination.router.ChannelRouterBase#post(my.mimos.misos.domain.entity.trx.ChannelMessage)
	 */
	@Override
	public ChannelResponseResource post(ChannelMessage channelMessage) {
		// TODO Auto-generated method stub
		MapChannelResponseResource res = new MapChannelResponseResource();
		
		try {
			
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			
		} finally {
			
		}
		
		return res;
	}

}
