package my.mimos.mdc.domain.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "mdc_trx_query")
public class Query extends EntityBase {	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "query_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long queryId;
	
	@Column(name = "subject")
	private String subject;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sent_by", referencedColumnName = "user_id")
	private User sentBy;
	
	@Column(name = "query_status")
	private String queryStatus;
	
	@Column(name = "approval_comment")
	private String approvalComment;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "modified_date")
	private Date modifiedDate;
	
	@Column(name = "approved_date")
	private Date approvedDate;	
	
	@ManyToOne
	@JoinColumn(name = "approved_by")
	private User approvedBy;
	
	@OneToMany(mappedBy="query", cascade=CascadeType.ALL)
    private Set<Recipient> recipients;
	
	@OneToMany(mappedBy="query", cascade=CascadeType.ALL)
	private List<Attachment> attachment;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "urgency_level_id", referencedColumnName = "urgency_level_id")
	private UrgencyLevel urgencyLevel;	
	
	// Indicates if the sender has unread content in the conversation thread of a query.
	@Column(name = "sender_read_status",columnDefinition="boolean default true")
	private boolean senderReadStatus;
	
	// Indicates if the recipients of the query has read it.
	@Column(name = "read_reciept",columnDefinition="boolean default true")
	private boolean readReciept;
	
	//Indicates if the query has been forwarded.
	@Column(name = "forward_flag",columnDefinition="boolean default true")
	private boolean forwardFlag;

}