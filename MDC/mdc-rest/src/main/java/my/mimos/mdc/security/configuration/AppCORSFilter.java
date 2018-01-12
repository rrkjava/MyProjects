/**
 * 
 */
package my.mimos.mdc.security.configuration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class AppCORSFilter implements Filter {
	
	public AppCORSFilter() {
	    log.debug("Cross-Origin Resource Sharing ---> Enabling requests from different origin");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		String reqHead = request.getHeader("Access-Control-Request-Headers");
		if (!StringUtils.isEmpty(reqHead)) {
            response.addHeader("Access-Control-Allow-Headers", reqHead);
        }
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) {}

	@Override
	public void destroy() {}

}
