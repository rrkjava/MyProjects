/**
 * 
 */
package my.mimos.misos.dissemination.service;

import my.mimos.misos.domain.channel.EmailChannelRequestResource;
import my.mimos.misos.domain.channel.EmailChannelResponseResource;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
public interface EmailService {
	
	public EmailChannelResponseResource postStatus(EmailChannelRequestResource req);
	
}
