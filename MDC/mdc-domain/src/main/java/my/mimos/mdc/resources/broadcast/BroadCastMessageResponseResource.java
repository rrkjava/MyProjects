package my.mimos.mdc.resources.broadcast;


/**
 * @author krishna.redabotu
 *
 */



import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.resources.BaseResponseResource;

@Data
@EqualsAndHashCode(callSuper=false)
public class BroadCastMessageResponseResource extends BaseResponseResource{
	
	private BroadCastResponseResource response;
	
}
