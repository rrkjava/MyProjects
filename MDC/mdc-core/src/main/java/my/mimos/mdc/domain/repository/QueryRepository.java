package my.mimos.mdc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import my.mimos.mdc.domain.entity.Query;

@Repository
public interface QueryRepository extends JpaRepository<Query, Long>,QueryDslPredicateExecutor<Query>{

}
