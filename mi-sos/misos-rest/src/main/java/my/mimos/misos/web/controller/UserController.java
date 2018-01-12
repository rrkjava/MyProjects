package my.mimos.misos.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.domain.integrationservice.ChannelRecipientRequestResource;
import my.mimos.misos.domain.integrationservice.ChannelRecipientResponseResource;
import my.mimos.misos.domain.integrationservice.RecipientRequestResource;
import my.mimos.misos.domain.integrationservice.RecipientResponseResource;
import my.mimos.misos.service.IntegrationService;

@Log4j
@Controller
@RequestMapping(value = "/recipients")
public class UserController extends AbstractController {
	
	@Autowired
	IntegrationService integrationService;
	
	@RequestMapping(value = "/filterByGroupAndPOI", method = RequestMethod.POST , produces = "application/json")
	public ResponseEntity<ChannelRecipientResponseResource> usersInternal(@RequestBody ChannelRecipientRequestResource request) {
		log.info("Calling Integration Service : filterByGroupAndPOI : " + request);
		ChannelRecipientResponseResource response = integrationService.getUsersByGroupAndPOI(request);
		return new ResponseEntity<ChannelRecipientResponseResource>(response , HttpStatus.OK);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.POST , produces = "application/json")
	public ResponseEntity<ChannelRecipientResponseResource> usersAll(@RequestBody ChannelRecipientRequestResource request) {
		log.info("Calling Integration Service : AllUsers : " + request);
		ChannelRecipientResponseResource response = integrationService.getUsersAll(request);
		return new ResponseEntity<ChannelRecipientResponseResource>(response , HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getChannelRecipients", method = RequestMethod.POST , produces = "application/json")
	public ResponseEntity<RecipientResponseResource> getChannelRecipients(@RequestBody RecipientRequestResource request) {
		log.info("Calling Integration Service : userDetailsByDisseFilter : " + request);
		RecipientResponseResource response = integrationService.getRecipients(request);
		return new ResponseEntity<RecipientResponseResource>(response , HttpStatus.OK);
	}

	
}