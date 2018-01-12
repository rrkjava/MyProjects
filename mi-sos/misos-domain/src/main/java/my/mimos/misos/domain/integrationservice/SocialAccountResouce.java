/**
 * 
 */
package my.mimos.misos.domain.integrationservice;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class SocialAccountResouce {

	@JsonProperty("AccessToken")
	private String accessToken;

	@JsonProperty("AccessTokenSecret")
	private String accessTokenSecret;

	@JsonProperty("AccountType")
	private String accountType;

	@JsonProperty("ConsumerKey")
	private String consumerKey;

	@JsonProperty("ConsumerSecret")
	private String consumerSecret;
	
	@JsonProperty("FacebookPageId")
	private String facebookPageId;
}
