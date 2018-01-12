package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class BaseRequestResource {

	@JsonProperty("TransactionFrom")
	private String transactionFrom;
	
	@JsonProperty("MethodType")
	private String methodType;
	
	@JsonProperty("Desc")
	private String description;
	
}
