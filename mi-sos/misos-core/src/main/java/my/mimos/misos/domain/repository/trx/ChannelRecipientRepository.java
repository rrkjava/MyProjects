/**
 * 
 */
package my.mimos.misos.domain.repository.trx;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import my.mimos.misos.domain.entity.trx.ChannelRecipient;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
public interface ChannelRecipientRepository extends PagingAndSortingRepository<ChannelRecipient, Long> {

	@Query("select c from ChannelRecipient c where c.message.iocMesageId = ?1 and c.targetChannelTypeId = ?2")
	List<ChannelRecipient> findByIocMessageIdAndChannelType(String iocMessageId, String channeltype);
	
	//@Query("select count(c) from ChannelRecipient c where c.targetChannelType.id = ?1 and c.message.id = ?2")
	@Query("select count(c) from ChannelRecipient c where c.targetChannelTypeId = ?1 and c.message.id = ?2")
	Long countByChannelTypeandMessageId(String channelType,Long messageId);
}
