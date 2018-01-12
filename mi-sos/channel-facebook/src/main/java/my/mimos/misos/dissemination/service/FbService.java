/**
 * 
 */
package my.mimos.misos.dissemination.service;

import my.mimos.misos.domain.channel.FbChannelRequestResource;
import my.mimos.misos.domain.channel.FbChannelResponseResource;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@FunctionalInterface
public interface FbService {
	public FbChannelResponseResource postStatus(FbChannelRequestResource req);
}
