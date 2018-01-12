/**
 * 
 */
package my.mimos.misos.util;

import org.springframework.stereotype.Component;

import my.mimos.misos.domain.geo.Coordinate;
import my.mimos.misos.domain.geo.Polygon;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@Component
public class Utility {

	public boolean isCoordinateInsidePolygon(Polygon polygon, Coordinate coord) {

		boolean isInside = false;
		int i, j;

		try {
			// create an array of coordinates from the polygon boundary list
			Coordinate[] verts = (Coordinate[]) polygon.getBoundary().toArray(new Coordinate[polygon.getBoundary().size()]);
			int sides = verts.length;

			for (i = 0, j = sides - 1; i < sides; j = i++) {
				// verifying if your coordinate is inside the polygon
				if ((((verts[i].getLongitude() <= coord.getLongitude())
						&& (coord.getLongitude() < verts[j].getLongitude()))
						|| ((verts[j].getLongitude() <= coord.getLongitude())
								&& (coord.getLongitude() < verts[i].getLongitude())))
						&& (coord.getLatitude() < (verts[j].getLatitude() - verts[i].getLatitude())
								* (coord.getLongitude() - verts[i].getLongitude())
								/ (verts[j].getLongitude() - verts[i].getLongitude()) + verts[i].getLatitude())) {
					
					isInside = !isInside;
				}
			}

		} catch (Exception e) {

		} finally {

		}

		return isInside;
	}
	
}
