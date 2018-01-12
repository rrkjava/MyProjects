package my.mimos.mdc.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;
import my.mimos.mdc.service.EmailService;

@Log4j
@Component
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public boolean sendEmail(List<String> toAddress,String body){
		boolean flag = false;
		log.info("Sending email notification...");
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			toAddress = toAddress.stream().distinct().collect(Collectors.toList());
			InternetAddress[] address = new InternetAddress[toAddress.size()];
			for (int i = 0; i < toAddress.size(); i++) {				
				address[i] = new InternetAddress(toAddress.get(i));				
			}			
			helper.setFrom("mecac@mimos.my");
			helper.setSubject("MECA");
			message.setRecipients(Message.RecipientType.CC, address);
			message.setContent(body,"text/html");
			javaMailSender.send(message);
			flag = true;
			log.info("Sending email notification completed...");
		}catch(Exception ex){
			ex.printStackTrace();			
		}
		return flag;		
	}		

}
