package my.mimos.misos.web.service;

import my.mimos.misos.domain.resource.DisseminationResponseResource;
/**
 * 
 * @author beaula.fernandez
 *
 */

public interface RequestValidationService {
	
	public boolean getDataFromIntegrationService(String requestJsonKey,String requestJsonValue);
	
	public void validateJson(String jsonString, DisseminationResponseResource response) throws RuntimeException;

}
