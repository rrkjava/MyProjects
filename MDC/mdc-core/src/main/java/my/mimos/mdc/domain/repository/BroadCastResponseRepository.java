package my.mimos.mdc.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import my.mimos.mdc.domain.entity.BroadCastResponse;

/**
 * @author krishna.redabotu
 *
 */

public interface BroadCastResponseRepository extends JpaRepository<BroadCastResponse, Long>,QueryDslPredicateExecutor<BroadCastResponse> {

	public List<BroadCastResponse> findByBroadCast_MessageId(Long messageId);
}
