/**
 * 
 */
package my.mimos.misos.dissemination.service;

import my.mimos.misos.domain.channel.PublicPortalChannelRequestResource;
import my.mimos.misos.domain.channel.PublicPortalChannelResponseResource;

/**
 * @author krishna.redabotu
 *
 */
@FunctionalInterface
public interface PublicPortalService {
	public PublicPortalChannelResponseResource sendToPublicPortal(PublicPortalChannelRequestResource req);
}
