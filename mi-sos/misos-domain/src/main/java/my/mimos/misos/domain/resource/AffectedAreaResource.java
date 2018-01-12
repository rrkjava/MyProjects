/**
 * 
 */
package my.mimos.misos.domain.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author bajakhitam
 *
 */

//@Getter
//@Setter
@Data
@EqualsAndHashCode(callSuper=false)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "area")
public class AffectedAreaResource {
	private String area;
}
