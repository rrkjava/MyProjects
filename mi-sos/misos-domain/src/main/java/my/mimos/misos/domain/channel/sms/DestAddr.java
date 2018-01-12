/**
 * 
 */
package my.mimos.misos.domain.channel.sms;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@Data
@XStreamAlias("DestAddr")
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"mt"})
public class DestAddr implements Serializable {

	@XStreamAlias("DestAddr")
	private String mt;
	
	public DestAddr(String mt) {
		this.mt = mt;
	}
}
