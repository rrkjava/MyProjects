package my.mimos.misos.domain.integrationservice;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class ImageRequestResource extends RecipientRequestResource {
	
	@JsonProperty("MessageId")
	private String messageId;
	
	@JsonProperty("MessageDate")
	@JsonFormat(pattern = "dd MMM yyyy HH:mm:ss")
	private Date messageDate;
	
	@JsonProperty("FloodTargetDate")
	@JsonFormat(pattern = "dd MMM yyyy HH:mm:ss")
	private Date floodTargetDate;
	
	@JsonProperty("Messages")
	private List<String> messages;

}
