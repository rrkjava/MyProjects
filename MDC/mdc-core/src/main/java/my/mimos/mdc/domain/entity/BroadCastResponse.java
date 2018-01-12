/**
 * 
 */
package my.mimos.mdc.domain.entity;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "mdc_trx_broadcast_response")
public class BroadCastResponse extends EntityBase {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "resp_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long responseId;
	
	@Column(name = "response_message",columnDefinition = "text")
	private String responseMessage;
	
	@ManyToOne
	@JoinColumn(name = "message_id")
	private BroadCast broadCast;
	
	@Column(name="response_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date responseDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "response_by", referencedColumnName = "user_id")
	private User responseBy;
	
	@Column(name = "reply_flag",columnDefinition="boolean default false")
	private boolean directReplyFlag;
	
	@OneToMany(mappedBy="broadCastResponse", cascade=CascadeType.ALL)
	private List<Attachment> attachment;
	
}
