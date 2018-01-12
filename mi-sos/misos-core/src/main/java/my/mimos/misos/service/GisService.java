/**
 * 
 */
package my.mimos.misos.service;


import my.mimos.misos.domain.resource.DisseminationRequestResource;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
public interface GisService {

	// Register the impacted area in geometry type
	Long registerImpactedArea(DisseminationRequestResource req) throws RuntimeException;	
}
