package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class MessageTypeResource {
	
	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("MessageType")
	private String messageType;
	
	@JsonProperty("MessageTypeCode")
	private String messageTypeCode;

}
