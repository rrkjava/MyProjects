package my.mimos.mdc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import my.mimos.mdc.domain.entity.BroadCast;

/**
 * @author krishna.redabotu
 *
 */

public interface BroadCastRepository extends JpaRepository<BroadCast, Long>,QueryDslPredicateExecutor<BroadCast> {

}
