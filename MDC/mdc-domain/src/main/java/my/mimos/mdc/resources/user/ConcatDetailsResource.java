/**
 * 
 */
package my.mimos.mdc.resources.user;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class ConcatDetailsResource {
	
	private String fullName;
	private String phone;
	private String emailId;
	private String emabssyName;
	private String address;
	private Long uploadedFlag;

}
