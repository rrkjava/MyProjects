/**
 * 
 */
package my.mimos.misos.web.service;

import my.mimos.misos.domain.resource.DisseminationRequestResource;
import my.mimos.misos.domain.resource.DisseminationResponseResource;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
public interface DisseminationService {
	DisseminationResponseResource disseminate(DisseminationRequestResource req);
}
