package my.mimos.mdc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.mimos.mdc.domain.entity.NotificationTracker;

@Repository
public interface NotificationTrackerRepository extends JpaRepository<NotificationTracker,Long>{

}
