/**
 * 
 */
package my.mimos.misos.domain.repository.trx;

import org.springframework.data.repository.PagingAndSortingRepository;

import my.mimos.misos.domain.entity.trx.Message;



/**
 * @author Shaiful Hisham Mat Jali
 *
 */
public interface MesageRepository extends PagingAndSortingRepository<Message, Long> {

	public Message findByIocMesageId(String iocMesageId);
}
