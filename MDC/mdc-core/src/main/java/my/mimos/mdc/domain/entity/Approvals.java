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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/*@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "mdc_trx_approvals")*/
public class Approvals {
	
	@Id
	@Column(name = "approval_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long approvalId;
	
	@Column(name = "response_id")
	private Response response;
	
	@Column(name = "approval_status")
	private String approvalStatus;
	
	@Column(name = "approval_level")
	private String approvalLevel; // another table
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User approvedBy;
	
	@Column(name = "approval_date")
	private Date approvalDate;	
	
	@Column(name = "approval_comment")
	private String approvalComment;	
}
