/**
 * 
 */
package my.mimos.misos.mapper;

import java.util.ArrayList;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Polygon;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
public class GeoMapper extends MapperBase {
	
	@Override
	protected void configure(MapperFactory factory) {
		
		// Object mapping between com.vividsolutions.jts.geom.Coordinate and my.mimos.misos.domain.geo.Coordinate
		factory.classMap(Coordinate.class, my.mimos.misos.domain.geo.Coordinate.class)
				.customize(new CustomMapper<Coordinate, my.mimos.misos.domain.geo.Coordinate> () {
					@Override
					public void mapAtoB(Coordinate a, my.mimos.misos.domain.geo.Coordinate b, MappingContext context) {
						// TODO Auto-generated method stub
						//super.mapAtoB(a, b, context);
						b.setLongitude(a.x);
						b.setLatitude(a.y);
					}
				})
				.register();
		
		// Object mapping between com.vividsolutions.jts.geom.Polygon and my.mimos.misos.domain.geo.Polygon
		factory.classMap(Polygon.class, my.mimos.misos.domain.geo.Polygon.class)
				.customize(new CustomMapper<Polygon, my.mimos.misos.domain.geo.Polygon> () {
					@Override
					public void mapAtoB(Polygon a, my.mimos.misos.domain.geo.Polygon b, MappingContext context) {
						// TODO Auto-generated method stub
						//super.mapAtoB(a, b, context);
						ArrayList<my.mimos.misos.domain.geo.Coordinate> bCoordinates = new ArrayList<my.mimos.misos.domain.geo.Coordinate>(0);
						Coordinate [] aCoordinates = a.getCoordinates();
						for(Coordinate aCoordinate : aCoordinates) {
							my.mimos.misos.domain.geo.Coordinate bCoordinate = mapperFacade.map(aCoordinate, my.mimos.misos.domain.geo.Coordinate.class);
							bCoordinates.add(bCoordinate);
						}
						b.setBoundary(bCoordinates);
					}
				})
				.register();
		
		mapperFacade = factory.getMapperFacade();		
	}
	
}
