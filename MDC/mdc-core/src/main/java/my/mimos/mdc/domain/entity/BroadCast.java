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
@Table(name = "mdc_trx_broadcast")
public class BroadCast extends EntityBase {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "message_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long messageId;
	
	@Column(name = "subject")
	private String subject;

	@Column(name = "message",columnDefinition = "text")
	private String message;
	
	@Column(name="broadcast_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date broadcastDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "broadcast_by", referencedColumnName = "user_id")
	private User broadcastBy;
	
	@OneToMany(mappedBy="broadCast", cascade=CascadeType.ALL)
	private List<Attachment> attachment;
	
}
