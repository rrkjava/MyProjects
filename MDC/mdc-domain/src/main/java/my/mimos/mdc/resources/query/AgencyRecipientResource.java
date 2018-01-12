package my.mimos.mdc.resources.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AgencyRecipientResource {
	
	private Long agencyId;
	private String agencyName;

}
