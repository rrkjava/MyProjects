package my.mimos.mdc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import my.mimos.mdc.domain.entity.Comment;

public interface QueryCommentRepository extends JpaRepository<Comment, Long>,QueryDslPredicateExecutor<Comment>{

}
