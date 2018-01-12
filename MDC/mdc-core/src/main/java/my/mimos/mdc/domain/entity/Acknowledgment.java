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
/**
 * Saves the acknowledgments from focal point recipients 
 * @author beaula.fernandez
 *
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "mdc_trx_acknowledgment")
public class Acknowledgment {
	
	@Id
	@Column(name = "ack_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ackId;
	
	@Column(name = "ack_status")
	private String ackStatus;
	
	@Column(name = "ack_comment")
	private String ackComment;  
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User ackBy ;
	
	@Column(name = "read_reciept",columnDefinition="boolean default true")
	private boolean readReciept;
	
	@ManyToOne
	@JoinColumn(name = "query_id")
	private Query query;
	
}
