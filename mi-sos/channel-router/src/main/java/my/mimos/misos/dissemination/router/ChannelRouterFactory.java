/**
 * 
 */
package my.mimos.misos.dissemination.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import my.mimos.misos.common.enums.ChannelType;
import my.mimos.misos.mapper.ChannelMapper;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
//@Log4j
@Component
public class ChannelRouterFactory {
	@Autowired
	ChannelMapper channelMapper;	
	
	@Autowired
	AutowireCapableBeanFactory beanFactory;
	
	public ChannelRouterBase getChannelRouter(String channelType) {
		ChannelRouterBase res = null;
		
		if(channelType.equals(ChannelType.CH_FACEBOOK.toString())) {
			res = new FacebookChannel(channelMapper);			
			
		} else if(channelType.equals(ChannelType.CH_TWITTER.toString())) {
			res = new TwitterChannel(channelMapper);
			
		} else if(channelType.equals(ChannelType.CH_MOBILE_APP.toString())) {
			res = new MobileChannel(channelMapper);
			
		} else if(channelType.equals(ChannelType.CH_SMS.toString())) {
			res = new SmsChannel(channelMapper);
			
		} else if(channelType.equals(ChannelType.CH_EMAIL.toString())) {
			res = new EmailChannel(channelMapper);
			
		} else if(channelType.equals(ChannelType.CH_MAP.toString())) {
			res = new MapChannel(channelMapper);
			
		} else if(channelType.equals(ChannelType.CH_PUBLIC_PORTAL.toString())) {
			res = new PublicPortalChannel(channelMapper);
			
		} else if(channelType.equals(ChannelType.CH_SIREN.toString())) {
			res = new SirenChannel(channelMapper);
			
		} else if(channelType.equals(ChannelType.CH_FAX.toString())) {
			res = new FaxChannel(channelMapper);
		}
		
		// Manually inject the Config beans
		if(res != null)
			beanFactory.autowireBean(res);
		
		return res;
	}
}
