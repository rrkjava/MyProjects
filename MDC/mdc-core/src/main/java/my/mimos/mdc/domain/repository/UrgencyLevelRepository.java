package my.mimos.mdc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import my.mimos.mdc.domain.entity.UrgencyLevel;

@Component
public interface UrgencyLevelRepository extends JpaRepository<UrgencyLevel, Long>{

	public UrgencyLevel findByUrgencyLevel(String name);

}
