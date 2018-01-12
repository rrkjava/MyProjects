package my.mimos.mdc.resources.query;


import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.resources.BaseResponseResource;

@Data
@EqualsAndHashCode(callSuper=false)
public class ListRecipientResponseResource extends BaseResponseResource{
	
	private Set<UserRecipientResource> users;
	private Set<GroupRecipientResource> groups;
	private Set<AgencyRecipientResource> agencies;

}
