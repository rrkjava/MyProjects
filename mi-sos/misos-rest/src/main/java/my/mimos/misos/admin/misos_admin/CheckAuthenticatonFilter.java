package my.mimos.misos.admin.misos_admin;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import my.mimos.misos.admin.misos_admin.vo.AuthRequestResource;
import my.mimos.misos.admin.misos_admin.vo.AuthResponseResource;
import my.mimos.misos.config.AdConfig;

/**
 * 
 * @author beaula.fernandez
 * For Development, use the below commented dofilter() method at the bottom, it avoids the call to check AD service for uap auth. 	   
 */


@Component
public class CheckAuthenticatonFilter implements Filter {
	
	@Autowired
	AdConfig adConfig;

//	@Override
//	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//
//		try{
//
//			HttpServletRequest request = (HttpServletRequest) req;
//			HttpServletResponse response = (HttpServletResponse) res;
//			String headerName="REMOTE_USER"; // uap header passed in request
//			String pid = request.getHeader(headerName);	
//			System.out.println("PID from Mi-UAP header : " + pid );
//			
//			boolean authorized = false;
//			String displayName = null;
//			String givenName = null;
//			String isActivated=null;
//
//			//pid="7c50c68cf47a0fdbb127c5a66da05970e36d0239"; //test value
//
//			String path = request.getRequestURI();
//			if (path.contains("error.html")) {
//				chain.doFilter(req, response);
//			}
//			else{				
//				AuthResponseResource authResponseResource = checkIfAuthorized(pid);
//				if(authResponseResource.getResult() !=null){
//					if(authResponseResource.getResult().getAdUserData().getMisosRoles().equalsIgnoreCase("Admin")
//							&& authResponseResource.getResult().getAdUserData().getIsActivated().equalsIgnoreCase("TRUE")){
//
//						authorized = true;
//						displayName = authResponseResource.getResult().getAdUserData().getDisplayName();
//						givenName = authResponseResource.getResult().getAdUserData().getGivenName();	
//						isActivated = authResponseResource.getResult().getAdUserData().getIsActivated();
//
//						req.setAttribute("displayName", displayName);
//						req.setAttribute("givenName", givenName);
//						req.setAttribute("authorized", authorized);
//
//						String displayNameCookie =displayName.replaceAll("\\s","#");
//						String givenNameCookie =givenName.replaceAll("\\s","#");
//
//						response.addHeader("displayName", displayName);
//						Cookie cookie = new Cookie("displayName", displayNameCookie);
//						response.addCookie(cookie);
//						cookie = new Cookie("givenName", givenNameCookie);
//						response.addCookie(cookie);
//						cookie = new Cookie("authorized", "true");
//						response.addCookie(cookie);
//						cookie = new Cookie("isActivated", isActivated);
//						response.addCookie(cookie);
//
//						System.out.println("Logged in user : " + displayName );
//
//						chain.doFilter(req, response);
//					}else{
//						authorized = false; 
//						System.out.println("User doesnot have misos role as admin / isactivated is false!");
//					}
//				}else{
//					authorized = false;
//					System.out.println("No results for the pid from AD directory search!");
//				}	
//
//				if(authorized==false){
//					response.sendRedirect("/misos/error.html");
//				}
//			}					
//		}catch(Exception ex){
//			ex.printStackTrace(); 
//		}
//
//	}

	public AuthResponseResource checkIfAuthorized(String pid){

		AuthRequestResource authRequestResource = new AuthRequestResource();
		AuthResponseResource authResponseResource = new AuthResponseResource();
		try{			  
			RestTemplate rest = new RestTemplate();
			//String url = "https://11.1.32.41:9999/ad-service/search"; // STAGING
			String url = adConfig.getUrl(); // From properties file
			authRequestResource.setUapPID(pid);
			disableSslVerification();// to be removed in prod env [only for staging]
			authResponseResource = rest.postForObject(url, authRequestResource, AuthResponseResource.class);		  

		}catch(Exception e){
			e.printStackTrace();
		}
		return authResponseResource;		  
	}

	@Override
	public void destroy() {}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

	private static void disableSslVerification() {
		try
		{
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}
				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			}
			};

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}

	/**
	 * For Dev, use the below commented dofilter method, it avoids the call to check AD service for uap auth. 
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		  try{

			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			String headerName="REMOTE_USER"; // uap header passed in request
			String pid = request.getHeader(headerName);	
			System.out.println("PID from Mi-UAP header : " + pid );
			
			//TESTING WEBSERVICE
			if(pid != null){
				AuthResponseResource authResponseResource = checkIfAuthorized(pid);
				if(null != authResponseResource && null != authResponseResource.getResult()){
					System.out.println("" + authResponseResource.getResult().getAdUserData().getDisplayName() );
				}
			}

			boolean authorized = false;
			String displayName = null;
			String givenName = null;
			String isActivated=null;


			String path = request.getRequestURI();
			if (path.contains("error.html")) {
				 chain.doFilter(req, response);
			}
			else{	  
						  displayName = "ADMINISTRATOR";
						  givenName = "ADMIN";
						  isActivated="TRUE";

						  req.setAttribute("displayName", displayName);
						  req.setAttribute("givenName", givenName);
						  req.setAttribute("authorized", authorized);

						  String displayNameCookie =displayName.replaceAll("\\s","#");
						  String givenNameCookie =givenName.replaceAll("\\s","#");

						  response.addHeader("displayName", displayName);
						  Cookie cookie = new Cookie("displayName", displayNameCookie);
						  response.addCookie(cookie);
						  cookie = new Cookie("givenName", givenNameCookie);
						  response.addCookie(cookie);
						  cookie = new Cookie("authorized", "true");
						  response.addCookie(cookie);
						  cookie = new Cookie("isActivated", isActivated);
						  response.addCookie(cookie);

						  System.out.println("Logged in user : " + displayName );

						  chain.doFilter(req, response);

			}					
		  }catch(Exception ex){
			 ex.printStackTrace(); 
		  }

	  }
	 


}