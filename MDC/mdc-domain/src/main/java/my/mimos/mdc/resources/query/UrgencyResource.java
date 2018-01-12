package my.mimos.mdc.resources.query;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 
 * @author beaula.fernandez
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class UrgencyResource{
	
	private String urgencyId;
	private String urgencyLevel;
	private int priority;


}
