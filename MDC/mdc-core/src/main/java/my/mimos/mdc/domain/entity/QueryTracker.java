package my.mimos.mdc.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "mdc_trx_query_tracker",uniqueConstraints = @UniqueConstraint(columnNames={"query_id","query_action","action_by_role","action_by_dept"}))
public class QueryTracker {
	
	@Id
	@Column(name = "tracker_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long trackerId;
	
	@ManyToOne
	@JoinColumn(name = "query_id")
	private Query query;
	
	@ManyToOne
	@JoinColumn(name = "response_id")
	private Response response;
	
	@Column(name = "query_action")
	private String queryAction;
	
	@Column(name = "start_date")
	private Date startDate;	
	
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "deadline_date")
	private Date deadlineDate ;
	
	@Column(name = "action_by_role")
	private String actionByRole;
	
	@Column(name = "action_by_dept")
	private Long actionByDept;
	
	@ManyToOne
	@JoinColumn(name = "action_done_by")
	private User actionDoneBy;
	
	@Column(name = "valid",columnDefinition="boolean default true")
	private boolean valid;
	
	@Column(name = "action_status",columnDefinition="boolean default true")
	private boolean actionStatus;

}
