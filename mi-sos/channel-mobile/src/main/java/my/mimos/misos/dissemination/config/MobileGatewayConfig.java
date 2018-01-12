/**
 * 
 */
package my.mimos.misos.dissemination.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@Component
@Getter
@Setter
public class MobileGatewayConfig {

	@Value("${MobileGateway.url}")
	private String url;
	
	@Value("${MobileGateway.service}")
    private String service;
    
}
