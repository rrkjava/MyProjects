/**
 * 
 */
package my.mimos.misos.domain.parser;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class Header {

protected String attributeName;
	
	protected String dataType;
	
	protected String isUnique;
	
	protected String method;
	
	@JsonProperty("class")
	protected String className;
	
	protected String status;
	
	protected String format;
	
	protected String prefix;
	
	protected String maxLength;
	
	protected String minLength;
	
	@JsonProperty("default")
	protected String defaultValue;
	
	protected String regex;
}
