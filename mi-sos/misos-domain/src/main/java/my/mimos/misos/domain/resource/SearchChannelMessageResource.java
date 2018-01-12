/**
 * 
 */
package my.mimos.misos.domain.resource;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class SearchChannelMessageResource extends BaseResource {
	
	private String iocMessageId;
	private String channelType;
	private String MessageContent;
	private String severityLevel;
	@JsonFormat(shape =JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private Date publishDate;
	private String publishStatus;
	private String createdDate;
	private String recipientCount;
	private String originator;
	private String notificationType;
	private String targetGroup;
	private String city;	
	
}
