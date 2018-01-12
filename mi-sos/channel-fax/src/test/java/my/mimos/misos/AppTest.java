/**
 * 
 */
package my.mimos.misos;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * @author Shaiful Hisham Mat Jali
 *
 */
public class AppTest extends TestCase {
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
    	boolean b = true;
    	
    	try {
    		
    		/*final String token = "72945da2d9ab2eb7b66cec73c4c1bedb398899283251a280a35088ef2fef1a4d";
    		
    		ApnsServiceBuilder serviceBuilder = APNS.newService();
    	    serviceBuilder.withCert("/Users/bajakhitam/Documents/ShaifulData/MIMOS/push_notification.p12", "m@573r0fpnpp375")
            		.withSandboxDestination();
    	    
    	    ApnsService service = serviceBuilder.build();
    	    String payload = APNS.newPayload()
                    .alertBody("Message from Mi-SOS engine.")
                    .alertTitle("Mi-SOS")
                    .sound("default")
                    .customField("custom", "custom value")
                    .build();
    	    service.push(token, payload);
    	    
    	    System.out.println("Dah hantar.");*/
    	    
    	} catch (Exception e) {
    		e.printStackTrace();    		
    		b = false;
    	}
    	
    	assertTrue( b );
    }
}
