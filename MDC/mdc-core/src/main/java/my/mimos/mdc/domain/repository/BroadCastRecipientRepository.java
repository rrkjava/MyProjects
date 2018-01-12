/**
 * 
 */
package my.mimos.mdc.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import my.mimos.mdc.domain.entity.BroadCastRecipient;

/**
 * @author krishna.redabotu
 *
 */

public interface BroadCastRecipientRepository extends JpaRepository<BroadCastRecipient,  Long>,QueryDslPredicateExecutor<BroadCastRecipient> {
	
}
