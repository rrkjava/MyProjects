package my.mimos.mdc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import my.mimos.mdc.domain.entity.Role;

@Component
public interface RoleRepository extends JpaRepository<Role, Long>{

}
