package my.mimos.mdc.domain.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "mdc_trx_broadcast_recipient")
public class BroadCastRecipient {
 
	@Id
	@Column(name = "recipient_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long recepientId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "message_id")
	private BroadCast broadCast;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group;
	
	@Column(name = "recipient_type")
	private String recipientType;
	
	@Column(name="received_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedDate;
	
	@Column(name = "read_status")
	private String readStatus;
	
	@Column(name="read_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date readDate;
	
	@Column(name="last_activity_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActivityDate;
	
}
