package my.mimos.mdc.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Component;

import my.mimos.mdc.domain.entity.Group;

@Component
public interface GroupRepository extends JpaRepository<Group, Long>,QueryDslPredicateExecutor<Group> {
	
	public List<Group> findByGroupIdIn(List<Long> GroupIdList);

}
