/**
 * 
 */
package my.mimos.misos.domain.integrationservice;

import java.util.List;

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
public class ApplicationAlertResource {

	@JsonProperty("AlertCategory")
	private String alertCategory;

	@JsonProperty("AlertDate")
	private String alertDate;

	@JsonProperty("AlertId")
	private String alertId;

	@JsonProperty("AlertMessage")
	private String alertMessage;

	@JsonProperty("AlertTitle")
	private String alertTitle;

	@JsonProperty("ApplicationCode")
	private String applicationCode;

	@JsonProperty("RedirectLink")
	private String redirectLink;

	@JsonProperty("UpdatedBy")
	private String updatedBy;

	@JsonProperty("UpdatedDate")
	private String updatedDate;
	
	@JsonProperty("Messages")
	private List<String> messages;

}
