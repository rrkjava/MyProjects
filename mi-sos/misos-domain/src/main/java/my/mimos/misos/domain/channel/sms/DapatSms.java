/**
 * 
 */
package my.mimos.misos.domain.channel.sms;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@Data
@XStreamAlias("SMSES")
public class DapatSms implements Serializable {
	
	@XStreamAlias("Keyword")
	private String keyword;
	
	@XStreamAlias("Message")
	private MessageType message;
	
	@XStreamAlias("DestAddrs")
	//@XStreamImplicit(itemFieldName="DestAddr")
	private List<DestAddr> destAddr;
	
}
