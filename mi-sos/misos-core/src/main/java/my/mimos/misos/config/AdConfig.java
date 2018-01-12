/**
 * 
 */
package my.mimos.misos.config;

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
public class AdConfig {

	@Value("${AdService.url}")
	private String url;

}
