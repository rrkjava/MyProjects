/**
 * 
 */
package my.mimos.misos.domain.channel.sms;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

import lombok.Data;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@Data
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"message"})
public class MessageType implements Serializable {

	@XStreamAlias("Type")
	private String type;
	
	private String message;
}
