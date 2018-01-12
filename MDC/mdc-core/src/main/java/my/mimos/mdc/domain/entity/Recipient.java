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

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 
 * @author beaula.fernandez
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "mdc_trx_recepient", uniqueConstraints = @UniqueConstraint(columnNames={"query_id","user_id","group_id","dept_id"}))
public class Recipient extends EntityBase {	
	
	private static final long serialVersionUID = -3600904785121514741L;

	@Id
	@Column(name = "recipient_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long recepientId;
	
	@ManyToOne
	@JoinColumn(name = "query_id")
	private Query query;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User recipient;	
	
	@Column(name = "recipient_type")
	private String recipientType;	
	
	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group;	
	
	@ManyToOne
	@JoinColumn(name = "dept_id")
	private Department dept;	
	
	@Column(name = "assigned_date")
	private Date assignedDate;
	
	@Column(name = "read_status")
	private String readStatus;
	
	@JoinColumn(name = "ack_id")
	private Long ack_id;
	
	@Column(name = "last_activity_date")
	private Date lastActivityDate;
	
}
