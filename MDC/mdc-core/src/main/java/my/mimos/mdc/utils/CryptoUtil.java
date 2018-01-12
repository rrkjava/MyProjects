package my.mimos.mdc.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CryptoUtil {
	
	public String encryptText(String text){
		String encryptedTempPassword = null;
		try{
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        encryptedTempPassword = passwordEncoder.encode(text);
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return encryptedTempPassword;		
	}
	
	public String decryptText(String text){	
		String decryptedText = null;
		try{
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return decryptedText;		
	}	
	
	/*public static void main(String args[]){
		String d = encryptText("el2rl");
		System.out.println(d);
	}*/

}
