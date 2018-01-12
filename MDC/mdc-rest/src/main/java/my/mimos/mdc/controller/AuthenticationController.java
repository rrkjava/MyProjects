package my.mimos.mdc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import my.mimos.mdc.resources.user.LoginRequestResource;
import my.mimos.mdc.resources.user.LoginResponseResource;
import my.mimos.mdc.service.UserManagementService;

/**
 * 
 * @author beaula.fernandez
 *
 */

@RestController
@RequestMapping(value="/")
public class AuthenticationController {
	
	@Autowired
	UserManagementService userService;		
	
	@RequestMapping(value="/login",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<LoginResponseResource> login(@Valid @RequestBody LoginRequestResource request){
		LoginResponseResource response = userService.login(request);
		return new ResponseEntity<LoginResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/refreshtoken",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<LoginResponseResource> refreshToken(HttpServletRequest request){
		LoginResponseResource response = userService.refreshToken(request);
		return new ResponseEntity<LoginResponseResource>(response, HttpStatus.OK);
	}

}
