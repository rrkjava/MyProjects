/**
 * 
 */
package my.mimos.misos.dissemination.service;

import my.mimos.misos.domain.resource.DisseminationRequestResource;
import my.mimos.misos.domain.resource.DisseminationResponseResource;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
@FunctionalInterface
public interface RouterService {
	DisseminationResponseResource route(DisseminationRequestResource req);
}
