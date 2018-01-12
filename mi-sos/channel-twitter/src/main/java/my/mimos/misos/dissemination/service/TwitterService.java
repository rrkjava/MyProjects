/**
 * 
 */
package my.mimos.misos.dissemination.service;

import my.mimos.misos.domain.channel.TwitterChannelRequestResource;
import my.mimos.misos.domain.channel.TwitterChannelResponseResource;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
@FunctionalInterface
public interface TwitterService {
	public TwitterChannelResponseResource postTweet(TwitterChannelRequestResource req);
}
