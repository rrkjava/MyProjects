package my.mimos.mdc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import my.mimos.mdc.domain.entity.MobileDevice;

@Repository
public interface DeviceRepository extends JpaRepository<MobileDevice, Long>,QueryDslPredicateExecutor<MobileDevice>{
	
	public MobileDevice findByDeviceToken(String deviceToken);

}
