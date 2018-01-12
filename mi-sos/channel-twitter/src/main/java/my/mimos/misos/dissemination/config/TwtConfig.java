/**
 * 
 */
package my.mimos.misos.dissemination.config;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

/**
 * @author krishna.redabotu
 *
 */

@Component
public class TwtConfig {

	public TwitterTemplate twitterTemplate(String appId, String appSecret, String accessToken,
			String accessTokenSecret) {
		return new TwitterTemplate(appId, appSecret, accessToken, accessTokenSecret);
	}

}
