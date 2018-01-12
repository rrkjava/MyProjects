/**
 * 
 */
package my.mimos.misos.dissemination.service;

import java.util.List;

import my.mimos.misos.domain.channel.SirenChannelRequestResource;
import my.mimos.misos.domain.channel.SirenChannelResponseResource;
import my.mimos.misos.domain.channel.sms.DestAddr;

/**
 * @author krishna.redabotu
 *
 */
public interface SirenService {
	public SirenChannelResponseResource sendSiren(SirenChannelRequestResource req);
	public List<DestAddr> resolveRecipients(SirenChannelRequestResource req);
}
