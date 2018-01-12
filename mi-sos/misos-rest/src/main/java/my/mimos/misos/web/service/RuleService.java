/**
 * 
 */
package my.mimos.misos.web.service;

import my.mimos.misos.domain.resource.DisseminationResponseResource;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

public interface RuleService {

	public void parseJson(String jsonString, DisseminationResponseResource response) throws RuntimeException;
	
}
