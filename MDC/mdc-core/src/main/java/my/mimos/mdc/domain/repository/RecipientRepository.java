package my.mimos.mdc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import my.mimos.mdc.domain.entity.Recipient;
/**
 * 
 * @author beaula.fernandez
 *
 */
@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long>,QueryDslPredicateExecutor<Recipient>{
	

}
