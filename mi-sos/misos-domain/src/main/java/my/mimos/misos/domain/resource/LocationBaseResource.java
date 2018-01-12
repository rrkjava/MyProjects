/**
 * 
 */
package my.mimos.misos.domain.resource;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class LocationBaseResource {
	// The latitude in floating point
	protected Float longitude;
	
	// The longitude in floating point
	protected Float latitude;
	
	// Location constructed date and time
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected Timestamp locationDate;
	
}
