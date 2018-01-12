/**
 * 
 */
package my.mimos.misos.domain.repository.trx;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import my.mimos.misos.domain.entity.trx.ChannelMessage;

/**
 * @author krishna.redabotu
 *
 */
public interface ChannelRepository extends PagingAndSortingRepository<ChannelMessage, Long> {
	
	public List<ChannelMessage> findByMessageId(Long id);
	
	public ChannelMessage findById(Long id);
	
	@Override
	public List<ChannelMessage> findAll();
}
