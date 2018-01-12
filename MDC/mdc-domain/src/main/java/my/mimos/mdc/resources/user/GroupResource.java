/**
 * 
 */
package my.mimos.mdc.resources.user;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class GroupResource {

	private Long groupId;
	private String groupName;
	private Date createdDate;
	private String groupStatus;
	private Long deptId;

}
