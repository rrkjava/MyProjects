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
public class ApplicationAlertRequestResource extends BaseRequestResource {

	@JsonProperty("ApplicationAlert")
	private ApplicationAlertResource applicationAlert ;
}
