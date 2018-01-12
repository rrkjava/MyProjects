package my.mimos.mdc.utils;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import my.mimos.mdc.resources.user.LoginResource;
import my.mimos.mdc.security.CustomUserDetails;


@Component
public class AuthTokenUtils {

	@Value("${token.secret}")
	private String secret;

	@Value("${token.expiration}")
	private Long expiration;
	
	@Autowired
	DateUtil dateUtil;
	
	private final String secretKey = "mdc";
	private final Long expirationTime = 3600L; // short lived token : 1 hr in seconds
	//private final Long expirationTime = 300L; // short lived token : 5 min
//	private final Long expirationTime = 900L; // short lived token : 15 min
	
	/**
     * Generates a JWT token. 
     * user name as subject,role,dates as additional claims. 
     * Roles help to define the access level at service end points 
     * 
     * @param user the user for which the token will be generated
     * @return the JWT token
     */
    public LoginResource generateToken(UserDetails user) {
    	LoginResource userLogin = new LoginResource();
    	try{    	
    		Date issuedOn = generateCurrentDate();
    		Date expiresOn = generateExpirationDate();
    		
	        Claims claims = Jwts.claims().setSubject(user.getUsername());
	        claims.put("role", user.getAuthorities());
	        claims.put("issuedOn",issuedOn);
	        claims.put("expiresOn",expiresOn);
	
	        String userAuthToken = Jwts.builder()
	                .setClaims(claims)
	                .signWith(SignatureAlgorithm.HS512, this.secretKey.getBytes())
	                .compact();
	        userLogin.setUserAuthToken(userAuthToken);
	        userLogin.setExpiresOn(dateUtil.dateToString(expiresOn));
	        userLogin.setIssuedOn(dateUtil.dateToString(issuedOn));
	        
	        Collection<? extends GrantedAuthority> roles = user.getAuthorities();
	        Set<String> userRoles = AuthorityUtils.authorityListToSet(roles);
	        userLogin.setUserRoles(userRoles);
	        
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
		return userLogin;
    }
    
    /**
     * generate token using claims
     * used for refreshing expired token from claims
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
          .setClaims(claims)
          .signWith(SignatureAlgorithm.HS512, this.secret)
          .compact();
      }
    
    /**
	 * Get all claims set during token generation
	 * @param token
	 * @return
	 */
	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(secretKey.getBytes())
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

    /**
     * Read the username from the userAuthToken
     * @param token
     * @return
     */
	public String getUsernameFromToken(String token) {
		String username = null;;
		try {
			final Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return username;
	}
	
	
	/**
	 * Read the create date of the token
	 * @param token
	 * @return
	 */
	public Date getCreatedDateFromToken(String token) {
		Date created;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			created = new Date((Long) claims.get("issuedOn"));
		} catch (Exception e) {
			created = null;
		}
		return created;
	}

	/**
	 * REad the expiration date from token
	 * @param token
	 * @return
	 */
	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			expiration = new Date((Long) claims.get("expiresOn"));
		}catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}
	

	private Date generateCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + this.expirationTime * 1000);
	}
	
	/**
	 * check token expiry
	 * @param token
	 * @return
	 */
    public Boolean isTokenExpired(String token) {
    	Boolean isTokenExpired = true;
	    final Date expiration = this.getExpirationDateFromToken(token);
	    final Date currentDate = this.generateCurrentDate();
	    if(null != expiration && ! expiration.before(currentDate)){
	    	isTokenExpired = false;
	    }
	    return isTokenExpired;
	}
    
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }
    
   /** 
    * re-generate the token from existing claims of expired token
    * @param token
    * @return
    */
    public LoginResource refreshToken(String token) {
        String refreshedToken = null;
        LoginResource userLogin = new LoginResource();
        Date issuedOn = generateCurrentDate();
		Date expiresOn = generateExpirationDate();
		
        try {
          final Claims claims = this.getClaimsFromToken(token);         
          refreshedToken = this.generateToken(claims);
          claims.put("issuedOn", issuedOn);
          claims.put("expiresOn",expiresOn);
          
          userLogin.setUserAuthToken(refreshedToken);
	      userLogin.setExpiresOn(dateUtil.dateToString(expiresOn));
	      userLogin.setIssuedOn(dateUtil.dateToString(issuedOn));
	        
        } catch (Exception e) {
          refreshedToken = null;
        }
        return userLogin;
      }
    
    private Boolean isCreatedBeforeLogout(Date created, Date logoutDate) {
    	if(logoutDate ==null){
    		return false;
    	}
        return created.before(logoutDate);
    }
    
    /**
     * validates the token username, expiry date and 
     * if password had been changed after the isssue of token
     * @param token
     * @param userDetails
     * @return
     */
	public Boolean validateToken(String token, UserDetails userDetails) {
		
		CustomUserDetails user = (CustomUserDetails) userDetails;
		final String username = this.getUsernameFromToken(token);
		final Date created = this.getCreatedDateFromToken(token);
		
		return (username.equals(user.getUsername()) && !(this.isTokenExpired(token))
				&& !(this.isCreatedBeforeLastPasswordReset(created, user.getLastPasswordReset())));
				//)&& !(this.isCreatedBeforeLogout(created, user.getLogoutDate())));
	}
}
