/**
 * 
 */
package my.mimos.misos.domain.resource;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author nandika.liyanage
 *
 */

//@Getter
//@Setter
@Data
//@EqualsAndHashCode(callSuper=false)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "messageId")
public abstract class DisseminationBaseResource {
	// Unique message identifier from IBM-IOC
	protected String messageId;

	// Message constructed date and time
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Timestamp messageDate;

	// Message originator (institute or agency)
	protected String originator;

	protected String eventId;
	
	// Type of the message
	protected String messageType;
	
	// User reference from IBM-IOC
	protected String userId;
}
