package my.mimos.misos.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import my.mimos.misos.domain.integrationservice.ImageRequestResource;
import my.mimos.misos.domain.integrationservice.ImageResource;
import my.mimos.misos.service.IntegrationService;

@Controller
@RequestMapping(value = "/disseminationImage")
public class ImageController extends AbstractController {

	@Autowired
	IntegrationService integrationService;

	@RequestMapping(value = "/getImage", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ImageResource> usersInternal(@RequestBody ImageRequestResource req) {
		ImageResource response = integrationService.getImage(req);
		return new ResponseEntity<ImageResource>(response, HttpStatus.OK);
	}

}