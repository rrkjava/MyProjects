package my.mimos.misos.domain.channel;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.misos.domain.resource.ChannelBaseResource;

/**
 * 
 */

/**
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class PublicPortalChannelRequestResource extends ChannelBaseResource {
	
	
	@JsonProperty("CertaintyLevelCode")
	private String certaintyLevelCode;
	
	@JsonProperty("EstimatedFloodDate")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date estimatedFloodDate;
	
	@JsonProperty("EstimatedEndDate")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date estimatedFloodEndDate;
	
	@JsonProperty("ImpactedAreaCode")
	private String impactedAreaCode;
	
	@JsonProperty("LocationTypeCode")
	private String locationTypeCode;
	
	@JsonProperty("Message")
	private String message;
	
	/*@JsonProperty("MessageDate")
	private String messageDate;*/
	
	@JsonProperty("MessageDate")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date messageDate;
	
	@JsonProperty("MessageId")
	private String messageId;
	
	@JsonProperty("MessageFormatCode")
	private String messageFormatCode;
	
	@JsonProperty("MessageTypeCode")
	private String messageTypeCode;
	
	@JsonProperty("NotificationTypeCode")
	private String notificationTypeCode;
	
	@JsonProperty("SeverityLevelCode")
	private String severityLevelCode;
	
	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("StatusType")
	private String statusType;
	
	@JsonProperty("TargetChannelTypeCode")
	private String targetChannelTypeCode;
	
	@JsonProperty("PoiId")
	private String poi;
	
}
