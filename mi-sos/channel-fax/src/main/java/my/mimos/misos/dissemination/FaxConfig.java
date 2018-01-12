package my.mimos.misos.dissemination;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@Component
@PropertySource("classpath:email-config.properties")
public class FaxConfig {
	
	/** MAIL CONFIGURATION **/
	
	@Value("${spring.channel-email.username}")
	private String username;
	
	@Value("${spring.channel-email.subject.malay}")
	private String subjectMalay;
	
	@Value("${spring.channel-email.subject.english}")
	private String subjectEnglish;
	
}
