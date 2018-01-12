package my.mimos.mdc.security.configuration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.GenericFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

import my.mimos.mdc.utils.AuthTokenUtils;

public class TokenAuthenticationFilter extends GenericFilter{

	@Autowired 
	private AuthTokenUtils authTokenUtils;
	
	private UserDetailsService userDetailsService;
	public TokenAuthenticationFilter(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final long serialVersionUID = 1L;
	
	public static final String LOGIN_MATCHER = "/login/**";
	public static final String ROOT_MATCHER = "/";
	public static final String DEVICE_REG_MATCHER = "/device/add/**";
    

    private List<String> pathsToSkip = Arrays.asList(           
            LOGIN_MATCHER,
            ROOT_MATCHER,
            DEVICE_REG_MATCHER
    );

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
	
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String authHeader = request.getHeader(AUTHORIZATION_HEADER);
		
		//TEST
		System.out.println(" CALLER IP ADDRESS : " +request.getRemoteAddr());
			  		
		if (skipPathRequest(request, pathsToSkip)) {
			filterChain.doFilter(request, response);
		}else{		
			if (authHeader == null || !authHeader.startsWith("Bearer")) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization header.");
			}else{				
				try {
					String userAuthToken = authHeader.substring(7);
					authTokenUtils = new AuthTokenUtils();
					String username = authTokenUtils.getUsernameFromToken(userAuthToken);
					
					if(username == null){
						throw new Exception("INVALID TOKEN");
					}
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);											
					
					if (authTokenUtils.validateToken(userAuthToken, userDetails)) {
						
						// produces authentication token - principal,credentials,authorities
						UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,
								userDetails.getPassword(), userDetails.getAuthorities());  
						
						// sets the security information associated with the current thread of execution
						SecurityContextHolder.getContext().setAuthentication(token); 
						
						filterChain.doFilter(request, response);
					}else{
						throw new Exception("TOKEN EXPIRED");
					}
					
				} catch (Exception ex) {
					System.out.println(ex.getMessage()); // replace by logger
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
				}
			}
		}
	}
	
	private boolean skipPathRequest(HttpServletRequest request, List<String> pathsToSkip ) {
        Assert.notNull(pathsToSkip, "pathsToSkip is null!");
        List<RequestMatcher> requestMatchers = pathsToSkip.stream().map(path -> new AntPathRequestMatcher(path)).collect(Collectors.toList());
        OrRequestMatcher matchers = new OrRequestMatcher(requestMatchers);
        return matchers.matches(request);
    }

	@Override
	public void destroy() {
		
	}

}
