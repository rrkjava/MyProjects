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
@Table(name = "mdc_trx_comment")
public class Comment extends EntityBase {
	
	private static final long serialVersionUID = 327625504693296785L;

	@Id
	@Column(name = "comment_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long commentId;
	
	@Column(name = "comment_desc")
	private String commentDesc;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User commentBy ;
	
	@ManyToOne
	@JoinColumn(name = "query_id")
	private Query query;
	
	@Column(name = "created_date")
	private Date createdDate;	
	
	@Column(name = "read_reciept",columnDefinition="boolean default true")
	private boolean readReciept;

}
