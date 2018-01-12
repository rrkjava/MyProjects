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

@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "mdc_trx_response_approval")
public class ResponseApproval{

	@Id
	@Column(name = "approval_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long approvalId;
	
	@ManyToOne
	@JoinColumn(name = "response_id")
	private Response response;
	
	@Column(name = "approval_status")
	private String approvalStatus;
	
	@Column(name = "approval_comment")
	private String approvalComment;  
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User approvalBy ;
	
	@Column(name = "read_reciept",columnDefinition="boolean default true")
	private boolean readReciept;	
	
}
