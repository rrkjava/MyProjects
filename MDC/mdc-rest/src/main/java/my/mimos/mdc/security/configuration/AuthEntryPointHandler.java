package my.mimos.mdc.security.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
/**
 * 
 * @author beaula.fernandez
 *
 */

@Component
public class AuthEntryPointHandler implements AuthenticationEntryPoint{

	/**
	 * Handles request without authentication tokens
	 * throws a 401 error (Access denied)
	*/
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		//response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");		
	}

}
