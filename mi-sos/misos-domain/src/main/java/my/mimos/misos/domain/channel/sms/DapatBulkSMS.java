/**
 * 
 */
package my.mimos.misos.domain.channel.sms;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
@Data
@XStreamAlias("Suite")
public class DapatBulkSMS implements Serializable {

	@XStreamAlias("Action")
	private String action;
	
	@XStreamAlias("Username")
	private String userName;
	
	@XStreamAlias("Password")
	private String password;
	
	@XStreamAlias("SMSES")
	private DapatSms sms;
	
}
