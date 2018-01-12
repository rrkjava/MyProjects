package my.mimos.misos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class AdminConfig {

	@Value("${AdminConfig.url}")
	private String url;
}
