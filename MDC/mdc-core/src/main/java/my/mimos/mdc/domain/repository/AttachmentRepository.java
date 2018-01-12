package my.mimos.mdc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Component;

import my.mimos.mdc.domain.entity.Attachment;

@Component
public interface AttachmentRepository extends JpaRepository<Attachment, Long>,QueryDslPredicateExecutor<Attachment>{
}
