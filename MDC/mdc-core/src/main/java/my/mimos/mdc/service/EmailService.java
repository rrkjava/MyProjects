package my.mimos.mdc.service;

import java.util.List;

public interface EmailService {
	
	public boolean sendEmail(List<String> toAddress,String body);

}
