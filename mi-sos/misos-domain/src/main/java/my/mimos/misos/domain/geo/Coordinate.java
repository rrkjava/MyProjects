/**
 * 
 */
package my.mimos.misos.domain.geo;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
@Data
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class Coordinate {
	private double longitude; 	// x-axis
	private double latitude;	// y-axis
	
	public Coordinate(double longitude, double latitude) {
    	this.longitude = longitude;
    	this.latitude = latitude;	    	
    }
}
