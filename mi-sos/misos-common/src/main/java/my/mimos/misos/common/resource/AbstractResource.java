/**
 * 
 */
package my.mimos.misos.common.resource;

import java.util.Date;

import lombok.Setter;
import lombok.ToString;

/**
 * @author nandika.liyanage
 *
 */
@Setter
@ToString
public abstract class AbstractResource {
	protected Long resourceId;

	protected Long version;

	protected Long createdBy;

	protected Date createdDate;

	protected Long lastModifiedBy;

	protected Date lastModifiedDate;

	protected AbstractResource(Long resourceId, Long version) {
		this.resourceId = resourceId;
		this.version = version;
	}

}
