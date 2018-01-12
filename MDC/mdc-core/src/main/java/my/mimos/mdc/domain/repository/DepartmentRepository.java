package my.mimos.mdc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import my.mimos.mdc.domain.entity.Department;

@Component
public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
