package my.mimos.mdc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import my.mimos.mdc.domain.entity.Response;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long>,QueryDslPredicateExecutor<Response>{

}
