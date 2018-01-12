package my.mimos.misos.command.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.command.DisseminationAdminCommand;
import my.mimos.misos.domain.entity.trx.MisosReportView;
import my.mimos.misos.domain.repository.trx.MisosReportViewRepository;
import my.mimos.misos.domain.resource.SearchChannelRequestResource;
import my.mimos.misos.domain.resource.SummaryReportRequestResource;
import my.mimos.misos.domain.resource.SummaryReportResource;
import my.mimos.misos.domain.specification.MisosReportSpecification;
import my.mimos.misos.util.DateUtil;

/**
 * 
 * @author beaula.fernandez
 *
 */

@Log4j
@Component
public class DisseminationAdminCommandHandler implements DisseminationAdminCommand{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	MisosReportViewRepository misosReportViewRepository;	

	@Autowired
	MisosReportSpecification misosReportSpecification;
	
	@Autowired
	DateUtil dateUtil;

	@Override
	public List<SummaryReportResource> summaryReportChannelMessages(SummaryReportRequestResource req) throws RuntimeException {

		List<SummaryReportResource> messageList = new ArrayList<SummaryReportResource>();
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Tuple> cq= cb.createTupleQuery();
			Root<MisosReportView> root = cq.from(MisosReportView.class);
			List<Predicate> predicates = new ArrayList<Predicate>();
			
			if (!StringUtils.isEmpty(req.getStatus()) && !req.getStatus().equals("null")) {
				boolean status=Boolean.parseBoolean(req.getStatus());
				predicates.add(cb.equal(root.get("publishStatus"),status));
			}
			
			if (!StringUtils.isEmpty(req.getChannelType()) && !req.getChannelType().equals("null")) {
				predicates.add(cb.equal(root.get("targetChannel"),req.getChannelType()));
			}

			if (!StringUtils.isEmpty(req.getFromDate())) {
				Date fromDate = dateUtil.convertStringToDate(req.getFromDate());
				predicates.add(cb.greaterThanOrEqualTo(root.<Date>get("publishDate"), fromDate));
			}
			
			if (!StringUtils.isEmpty(req.getToDate())) {
				Date toDate = dateUtil.convertStringToDate(req.getToDate());
				Date date= dateUtil.addDaysToDate(toDate,1);
				predicates.add(cb.lessThanOrEqualTo(root.<Date>get("publishDate"), date));
			}
			cq.multiselect(cb.sum(root.<Long>get("recipientCount")).alias("totalRecipients"),
					cb.count(root).alias("totalMessages"),
					root.get("targetChannel").alias("channelType"),
					root.get("publishStatus").alias("publishStatus"));
			cq.where(predicates.toArray(new Predicate[]{}));
			cq.groupBy(root.get("targetChannel"),root.get("publishStatus"));
			
			TypedQuery<Tuple> tq = entityManager.createQuery(cq);
			
			for (Tuple t : tq.getResultList()) {
				SummaryReportResource resource=new SummaryReportResource();
				resource.setNumOfMessages((Long)(t.get("totalMessages")));
				resource.setNumOfRecipients(Long.parseLong((String) t.get("totalRecipients"),10));
				resource.setChannelType((String) t.get("channelType"));
				resource.setPublishStatus( (boolean) t.get("publishStatus"));
				messageList.add(resource);
			}
			
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			ex.printStackTrace();
		}
		return messageList;
	}
	
	@Override
	public List<MisosReportView> auditDetailReportChannelMessages(SearchChannelRequestResource req)
			throws RuntimeException {
		
		List<MisosReportView> messageList = null;

		try {
			MisosReportView misosReportView = new MisosReportView();
			misosReportView.setTargetChannel(req.getChannelType());
			misosReportView.setIocMessageId(req.getIocMessageId());
			misosReportView.setSeverityLevel(req.getSeverityLevel());
			misosReportView.setFromDate(req.getFromDate());
			misosReportView.setToDate(req.getToDate());
			
			misosReportSpecification.setMisosReportView(misosReportView);
			messageList =misosReportViewRepository.findAll(misosReportSpecification,new Sort(Direction.DESC,"publishDate"));
			 
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			ex.printStackTrace();
		}
		return messageList;
	}

	@Override
	public List<MisosReportView> recentDissemination() throws RuntimeException {
		List<MisosReportView> messageList = null;
		try {
			messageList = misosReportViewRepository.findTop5ByOrderByPublishDateDesc();
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			ex.printStackTrace();
		}
		return messageList;
	}
	
	

}
