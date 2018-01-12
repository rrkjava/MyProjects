/**
 * 
 */
package my.mimos.misos.dissemination.service;

import java.util.List;

import my.mimos.misos.domain.channel.SmsChannelRequestResource;
import my.mimos.misos.domain.channel.SmsChannelResponseResource;
import my.mimos.misos.domain.channel.sms.DestAddr;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
public interface SmsService {
	public SmsChannelResponseResource sendSms(SmsChannelRequestResource req);
	public List<DestAddr> resolveRecipients(SmsChannelRequestResource req);
}
