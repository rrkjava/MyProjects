package my.mimos.mdc.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import my.mimos.mdc.domain.entity.Role;
import my.mimos.mdc.domain.entity.User;
import my.mimos.mdc.domain.repository.UserRepository;
/**
 * 
 * @author beaula.fernandez
 *
 */

@Component
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByUsername(username);
		List<String> userRoles= new ArrayList<String>();
		if(null == user){
			throw new UsernameNotFoundException("No user present with username: "+username);
		}else{
			Set<Role> roles = user.getRole();
			if(!roles.isEmpty()){				
				for(Role role : roles){
					userRoles.add(role.getRoleName());
				}
			}else{
				userRoles = null ;
			}
			return new CustomUserDetails(user,userRoles);
		}
	}
		
}
