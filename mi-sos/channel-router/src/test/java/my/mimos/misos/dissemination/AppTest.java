package my.mimos.misos.dissemination;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */

//@SpringApplicationConfiguration(Application.class)
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
    	//System.out.println("PROP1:"+sampleProperty.getStringProp1());
    	
    	
        assertTrue( true );   	
    	
    }
}
