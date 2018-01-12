package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class MessageTemplateResource {
	
	@JsonProperty("MessageTemplateId")
	private String messageTemplateId;
	
	@JsonProperty("MessageText")
	private String messageTemplate;
	
	@JsonProperty("Status")
	private String status;	

}
