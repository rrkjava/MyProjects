package my.mimos.mdc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Component;
import my.mimos.mdc.domain.entity.Acknowledgment;

@Component
public interface AckRepository extends JpaRepository<Acknowledgment, Long>,QueryDslPredicateExecutor<Acknowledgment>{
}

