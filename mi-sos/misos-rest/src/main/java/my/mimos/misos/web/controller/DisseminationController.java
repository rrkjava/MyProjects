/**
 * 
 */
package my.mimos.misos.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.domain.resource.DisseminationRequestResource;
import my.mimos.misos.domain.resource.DisseminationResponseResource;
import my.mimos.misos.mapper.DisseminationResourceMapper;
import my.mimos.misos.service.GisService;
//import my.mimos.misos.service.DisseminationService;
import my.mimos.misos.web.service.DisseminationService;
import my.mimos.misos.web.service.ElasticDisseminationService;
import my.mimos.misos.web.service.RequestValidationService;
import my.mimos.misos.web.service.RuleService;

/**
 * @author nandika.liyanage
 *
 */
@Log4j
@Controller
@RequestMapping(value = "/disseminate")
public class DisseminationController extends AbstractController {
	@Autowired
	DisseminationService disseminationService;

	@Autowired 
	RuleService ruleService;
	
	@Autowired
	DisseminationResourceMapper resourceMapper;
	
	/*@Autowired
	private ElasticDisseminationService elasticService;*/
	
	@Autowired
	RequestValidationService requestValidationService;
	
	// Receive the dissemination request from agency
	@RequestMapping(value = "/disseminate", method = RequestMethod.POST , produces = "application/json")
	public ResponseEntity<DisseminationResponseResource> add(@RequestBody DisseminationRequestResource request) {
		
		DisseminationResponseResource response = new DisseminationResponseResource();
		ResponseEntity<DisseminationResponseResource> ret = null;
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			
			// Convert the request to json string
			String json = mapper.writeValueAsString(request);
			
			response = resourceMapper.getMapperFacade().map(request, DisseminationResponseResource.class);
			
			// Parse and validate the json based on the rule file
			ruleService.parseJson(json, response);
			
			// Validate all inputs against the Database values
			requestValidationService.validateJson(json, response);
			
			// Register the impacted area in geometry type
			//Long impactedAreaId = gisService.registerImpactedArea(request);
			
			// Start disseminate the message to the recipient
			response = disseminationService.disseminate(request);
			
			ret =  new ResponseEntity<DisseminationResponseResource>(response, HttpStatus.OK);
			
		} catch (RuntimeException e) {
			
			ret =  new ResponseEntity<DisseminationResponseResource>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			
			ret =  new ResponseEntity<DisseminationResponseResource>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		} finally {
			
		}
		
		return ret;
	}
}
