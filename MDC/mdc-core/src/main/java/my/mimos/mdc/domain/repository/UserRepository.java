package my.mimos.mdc.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import my.mimos.mdc.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,QueryDslPredicateExecutor<User>{
	
	public User findByUsername(String username);
	
	public List<User> findByUserIdIn(List<Long> userIdList);
}
