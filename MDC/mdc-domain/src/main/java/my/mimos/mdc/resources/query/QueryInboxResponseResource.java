package my.mimos.mdc.resources.query;

import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.resources.BaseResponseResource;
import my.mimos.mdc.resources.broadcast.BroadCastInboxResource;
/**
 * 
 * @author beaula.fernandez
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class QueryInboxResponseResource extends BaseResponseResource{
	
	private List<QueryInboxResource> inbox;
	private List<BroadCastInboxResource> broadCastInbox;

}
