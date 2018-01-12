package my.mimos.mdc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class TestController {
	
	@RequestMapping(value="/",method = RequestMethod.GET, produces="text/plain")
	@ResponseBody
	public String getResponse(){		
		return ">>>>>>>>>>  MOHA DIPLOMACY CHANNEL : MDC API >>>>>>>>>>";
	}	

}
