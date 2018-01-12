/**
 * 
 */
package my.mimos.misos.domain.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author nandika.liyanage
 *
 */
//@Getter
//@Setter
@Data
@EqualsAndHashCode(callSuper=false)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "channelType")
public class ChannelRequestResource extends ChannelBaseResource {
	
	private String channelType;
	
	private String targetUserGroups;
	
	private String messageFormat;
	
	//private String notificationType;
	
	//private String certaintyLevel;
	
	//private String severityLevel;
	
	private String refTemplateId;
	
	private String message;
	
	//private String locationType;
	
	//private String locationData;
	
	//private String efectedArea;
	
	//private List<AffectedAreaResource> efectedArea;
	
	//@JsonFormat(pattern = "yyyyMMdd")
	//private Timestamp targetDate;
	
	// Shaiful : Added to support siren channel mobile no
	private String sirenMobileNo;
	
	private String faxEmailAddress;
	
}
