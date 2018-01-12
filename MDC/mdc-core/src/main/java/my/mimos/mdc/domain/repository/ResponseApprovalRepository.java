package my.mimos.mdc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import my.mimos.mdc.domain.entity.ResponseApproval;

@Repository
public interface ResponseApprovalRepository extends JpaRepository<ResponseApproval, Long>,QueryDslPredicateExecutor<ResponseApproval>{

}
