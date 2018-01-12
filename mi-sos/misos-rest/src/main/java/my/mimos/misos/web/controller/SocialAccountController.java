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

import my.mimos.misos.domain.integrationservice.SocialAccountRequestResource;
import my.mimos.misos.domain.integrationservice.SocialAccountResponseResource;
import my.mimos.misos.service.IntegrationService;

/**
 * @author krishna.redabotu
 *
 */


@Controller
@RequestMapping(value = "/disseminationSocial")
public class SocialAccountController {

	@Autowired
	IntegrationService integrationService;

	@RequestMapping(value = "/getSocialAccount", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<SocialAccountResponseResource> usersInternal(@RequestBody SocialAccountRequestResource req) {
		SocialAccountResponseResource response = integrationService.getsocialAccount(req);
		return new ResponseEntity<SocialAccountResponseResource>(response, HttpStatus.OK);
	}

}
