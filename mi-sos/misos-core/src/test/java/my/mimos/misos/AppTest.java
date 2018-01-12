package my.mimos.misos;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKTReader;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import my.mimos.misos.mapper.GeoMapper;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	assertTrue( true );
    }
    
    public void testGeoMapper () {
    	boolean b = true;
    	
    	// Sample POLYGON
		// POLYGON((102.2190284729 5.0351715087891, 103.03201675415 4.8264312744141, 103.2297706604 4.2881011962891, 103.16385269165 3.9145660400391, 102.6584815979 4.0683746337891, 102.36185073853 4.5188140869141, 102.3948097229 4.7495269775391, 102.2190284729 5.0351715087891))
		// Sample POINT
		// POINT(102.89580 4.14700) = Inside the polygon
		// POINT(102.46180, 4.34870) = Outside the polygon
    	
    	try {
    		String wkt = "POLYGON(" +
    				"(" +
    					"102.2190284729 5.0351715087891," + 
    					"103.03201675415 4.8264312744141," + 
    					"103.2297706604 4.2881011962891," + 
    					"103.16385269165 3.9145660400391," + 
    					"102.6584815979 4.0683746337891," + 
    					"102.36185073853 4.5188140869141," + 
    					"102.3948097229 4.7495269775391," + 
    					"102.2190284729 5.0351715087891" +
    				")"+
    			")";
    		
    		GeometryFactory geometryFactory = new GeometryFactory();
    		WKTReader reader = new WKTReader( geometryFactory );
    		Polygon poly = (Polygon) reader.read(wkt);
    		
    		GeoMapper mapper = new GeoMapper();
    		my.mimos.misos.domain.geo.Polygon dest = mapper.getMapperFacade().map(poly,  my.mimos.misos.domain.geo.Polygon.class);
    		
    		System.out.println(dest);
    		
    	} catch (Exception e) {
    		b = false;
    	}
    	
    	assertTrue( b );
    }
}
