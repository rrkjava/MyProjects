/**
 * 
 */
package my.mimos.misos.dissemination.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author krishna.redabotu
 *
 */

@Component
@Getter
@Setter
public class PublicPortalConfig {

	@Value("${PublicPortal.url}")
	private String url;
	
	@Value("${PublicPortal.service}")
    private String service;
    
}
