package my.mimos.misos.domain.specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import my.mimos.misos.domain.entity.trx.MisosReportView;
import my.mimos.misos.util.DateUtil;

@Component
public class MisosReportSpecification  implements Specification<MisosReportView> {
	
private MisosReportView misosReportView;
	
	@Autowired
	DateUtil dateUtil;

	public void setMisosReportView(MisosReportView misosReportView) {
		this.misosReportView = misosReportView;
	}

	@Override
	public Predicate toPredicate(Root<MisosReportView> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty((misosReportView.getTargetChannel())) && !misosReportView.getTargetChannel().equals("null")) {
			predicates.add(cb.equal(root.get("targetChannel"), misosReportView.getTargetChannel()));
		}

		if (!StringUtils.isEmpty(misosReportView.getIocMessageId())) {
			predicates.add(cb.equal(root.get("iocMessageId"), misosReportView.getIocMessageId()));
		}

		if (!StringUtils.isEmpty(misosReportView.getSeverityLevel()) && !misosReportView.getSeverityLevel().equals("null")) {
			predicates.add(cb.equal(root.get("severityLevel"), misosReportView.getSeverityLevel()));
		}

		if (!StringUtils.isEmpty(misosReportView.getFromDate())) {
			Date fromDate = dateUtil.convertStringToDate(misosReportView.getFromDate());
			predicates.add(cb.greaterThanOrEqualTo(root.<Date>get("publishDate"), fromDate));
		}
		
		if (!StringUtils.isEmpty(misosReportView.getToDate())) {
			Date toDate = dateUtil.convertStringToDate(misosReportView.getToDate());
			Date date= dateUtil.addDaysToDate(toDate,1);
			predicates.add(cb.lessThanOrEqualTo(root.<Date>get("publishDate"), date));
		}

		return andTogether(predicates, cb);
	}

	private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
		return cb.and(predicates.toArray(new Predicate[0]));
	}

}
