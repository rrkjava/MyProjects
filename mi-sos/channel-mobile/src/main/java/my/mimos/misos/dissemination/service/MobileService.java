/**
 * 
 */
package my.mimos.misos.dissemination.service;

import my.mimos.misos.domain.channel.MobileChannelRequestResource;
import my.mimos.misos.domain.channel.MobileChannelResponseResource;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
@FunctionalInterface
public interface MobileService {
	public MobileChannelResponseResource pushNotification(MobileChannelRequestResource req);
}
