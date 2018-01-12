/**
 * 
 */
package my.mimos.misos.dissemination.config;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagePostData;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;


/**
 * @author krishna.redabotu
 *
 */


@Component
public class FbConfig {

	public Facebook facebbookTemplate(String userAccessToken) {
		return new FacebookTemplate(userAccessToken);
	}

	public PagePostData pagePostData(String pageId) {
		return new PagePostData(pageId);
	}

}
