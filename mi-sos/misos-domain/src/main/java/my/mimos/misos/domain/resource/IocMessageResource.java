package my.mimos.misos.domain.resource;



import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class IocMessageResource {

	private Long id;
	
	private String iocMessageId;
	
	private String messageContent;	
	
	private String targetDate;
	
	private String originator;
	
	private String channelType;
	
	private String severityLevel;
	
	private String notificationType;
	
	private String city;	
	
	private String recipientCount;	
	
	private String createdDate;
	
	private String publishDate;
	
	private Boolean publishStatus;
}
