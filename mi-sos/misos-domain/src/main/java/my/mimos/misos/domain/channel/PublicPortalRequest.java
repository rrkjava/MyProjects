package my.mimos.misos.domain.channel;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PublicPortalRequest {	
	
	@JsonProperty("DisseReponseEnt")
	private PublicPortalChannelRequestResource disseminationRequest;
	
	@JsonProperty("MethodType")
	private int methodType;	
	
	@JsonProperty("TransactionFrom")
	private String transactionFrom;
	
}
