package my.mimos.misos.dissemination.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@Component
@PropertySource("classpath:sms-connector.properties")
public class SirenConfig {
	
	/** DAPAT SMS CONFIGURATION **/
	
	@Value("${spring.channel-sms.keyword}")
	private String keyword;
	 
	@Value("${spring.channel-sms.username}")
	private String username;
	
	@Value("${spring.channel-sms.password}")
	private String password;
	
	@Value("${spring.channel-sms.url}")
	private String url;
	
}
