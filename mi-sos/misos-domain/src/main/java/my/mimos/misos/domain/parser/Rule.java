/**
 * 
 */
package my.mimos.misos.domain.parser;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@Getter
@Setter
@ToString
public class Rule {

	private Header[] header;
	
	private Channels[] channels;
}
