/**
 * 
 */
package my.mimos.misos.domain.geo;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@Data
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class Polygon {
	private List<Coordinate> boundary;
	
	public Polygon(List<Coordinate> boundary) {
    	this.boundary = boundary;
    }	
}
