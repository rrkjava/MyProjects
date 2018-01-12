package my.mimos.mdc.domain.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author beaula.fernandez
 *
 */

@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "mdc_trx_response")
public class Response extends EntityBase {	

	private static final long serialVersionUID = -706952131221598003L;

	@Id
	@Column(name = "response_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long responseId;
	
	@ManyToOne
	@JoinColumn(name = "query_id")
	private Query query;
	
	@Column(name = "response_desc")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "response_by")
	private User responseBy;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "modified_date")
	private Date modifiedDate;
	
	@Column(name = "response_status")
	private String responseStatus;
	
	@Column(name = "approval_date")
	private Date approvalDate;
	
	@Column(name = "approval_comment")
	private String approvalComment;
	
	@ManyToOne
	@JoinColumn(name = "approved_by")
	private User approvedBy;
	
	@Column(name = "sensitive_flag")
	private Boolean sensitiveFlag;	

	@OneToMany(mappedBy="response", cascade=CascadeType.ALL)
	private List<Attachment> attachment;
	
	@Column(name = "read_reciept",columnDefinition="boolean default true")
	private boolean readReciept;
	
	/*Indicates if the reply was a direct reply from Admin to FDM without involving Focal*/
	@Column(name = "direct_reply_flag",columnDefinition="boolean default false")
	private boolean directReplyFlag;
	
	@OneToMany(mappedBy="response", cascade=CascadeType.ALL)
	private List<ResponseApproval> approvals;
	
	/*Indicates if the final reply added either by KLN or a reply from admin approved by KLN for FDM*/
	@Column(name = "final_reply_flag",columnDefinition="boolean default false")
	private boolean finalReplyFlag;
	
	/*Indicates if the final reply needs to be exposed to FDM and Focal*/
	@Column(name = "send_final_reply_flag",columnDefinition="boolean default false")
	private boolean sendFinalReplyFlag;
			
}
