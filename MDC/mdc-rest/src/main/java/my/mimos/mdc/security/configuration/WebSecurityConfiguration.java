package my.mimos.mdc.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/**
 * 
 * @author beaula.fernandez
 *
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired 
	private UserDetailsService userDetailsService;	 
	
	protected void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity		
			.authorizeRequests()
			.antMatchers("/login/**").permitAll()
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.antMatchers("/focal/**").access("hasRole('ROLE_FOCAL')").and()
			.addFilterBefore(new TokenAuthenticationFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class)
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()		
			.csrf().disable()
			.exceptionHandling().authenticationEntryPoint(new AuthEntryPointHandler());
	}
	
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authManagerBuilder) throws Exception {    
		authManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
	} 
	
	@Bean(name="passwordEncoder")
    public PasswordEncoder passwordencoder(){
		return new BCryptPasswordEncoder();
    }

}
