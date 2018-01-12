/**
 * 
 */
package my.mimos.misos.domain.resource;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author nandika.liyanage
 *
 */
//@Setter
//@Getter
@Data
@EqualsAndHashCode(callSuper=false)
public class DisseminationRequestResource extends DisseminationBaseResource {
	public DisseminationRequestResource() {}
	
	// Attribute inherit from Base Class
	// 1. messageId
	// 2. messageDate
	// 3. originator
	// 4. eventId
	// 5. messageType	
	// 6. userId

	private String notificationType;
	
	private String locationType;
	
	private String severityLevel;
	
	private String certainityLevel;
	
	private String impactedArea;
	
	private List<String> poi;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date targetDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date estimatedStartDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date estimatedEndDate;
	
	// List of dissemination channels
	private List<ChannelRequestResource> channel;
}
