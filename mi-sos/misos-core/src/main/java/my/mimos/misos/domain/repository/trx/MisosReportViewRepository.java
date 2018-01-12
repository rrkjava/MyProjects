package my.mimos.misos.domain.repository.trx;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import my.mimos.misos.domain.entity.trx.MisosReportView;

public interface MisosReportViewRepository extends PagingAndSortingRepository<MisosReportView, Long>,JpaSpecificationExecutor<MisosReportView> {

	List<MisosReportView> findAll(Specification<MisosReportView> spec, Sort sort );
	
	List<MisosReportView> findTop5ByOrderByPublishDateDesc();
}
