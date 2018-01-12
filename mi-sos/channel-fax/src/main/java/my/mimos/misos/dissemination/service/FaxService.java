/**
 * 
 */
package my.mimos.misos.dissemination.service;

import my.mimos.misos.domain.channel.FaxChannelRequestResource;
import my.mimos.misos.domain.channel.FaxChannelResponseResource;

/**
 * @author krishna.redabotu
 *
 */
@FunctionalInterface
public interface FaxService {
	public FaxChannelResponseResource sendFax(FaxChannelRequestResource req) throws RuntimeException;
}
