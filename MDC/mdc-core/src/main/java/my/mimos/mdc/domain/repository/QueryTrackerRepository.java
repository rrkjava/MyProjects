package my.mimos.mdc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import my.mimos.mdc.domain.entity.QueryTracker;

@Repository
public interface QueryTrackerRepository extends JpaRepository<QueryTracker, Long>,QueryDslPredicateExecutor<QueryTracker>{

}
