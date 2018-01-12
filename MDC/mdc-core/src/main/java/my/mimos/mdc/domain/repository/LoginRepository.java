package my.mimos.mdc.domain.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import my.mimos.mdc.domain.entity.Login;
import my.mimos.mdc.domain.entity.MobileDevice;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long>{
	
	@Query("SELECT COUNT(u) FROM Login u WHERE u.username=?1 and u.status=?2 and u.loginDate >= ?3")
    public int countFailedLogin(@Param("username") String username, @Param("status") String status, @Param("loginDate") Date loginDate );

	@Query("SELECT max(u.loginDate) FROM Login u WHERE u.username=?1 and u.status=?2 and u.loginDate >= ?3")
	public Date findLastSuccessfulLoginDate(@Param("username") String username, @Param("status") String status, @Param("loginDate") Date loginDate);
	
	//find record where device id = something and max(login date) and logout date is null //
	//@Query("SELECT r FROM Login r WHERE r.loginDate=(SELECT  max(u.loginDate) FROM Login u WHERE u.deviceId=?1 and u.username=?2)")
	@Query("SELECT  u FROM Login u WHERE u.username =:username and u.deviceId =:device and u.logoutDate is null")
	public Login findLoginDetails( @Param("username") String username,@Param("device") MobileDevice device);
	
}
