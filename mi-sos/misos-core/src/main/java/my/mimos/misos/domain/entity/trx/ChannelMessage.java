/**
 * 
 */
package my.mimos.misos.domain.entity.trx;

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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import my.mimos.misos.domain.entity.EntityBase;

/**
 * @author Shaiful Hisham Mat Jali
*/

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@Entity
@Table(name = "trx_channel")
public class ChannelMessage extends EntityBase {	
	private static final long serialVersionUID = 8889153237733190210L;

	@GeneratedValue(strategy=GenerationType.AUTO)	// Use auto sequence generator
	@Id
	@Column(name="channel_message_id")
	private Long id;
	
	@JsonBackReference
	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	@JoinColumn(name = "message_id")
	private Message message;
	
	@Column(name = "ref_target_channel_type_id")
	private String targetChannelTypeId;
	
	@Column(name = "ref_subscriber_type_id")
	private String subscriberTypeId;
	
	@Column(name = "ref_message_format_id")
	private String messageFormatId;	

	@Column(name = "ref_message_template_id")
	private String messageTemplateId;
	
	// Referring to message from IBM-IOC
	@Column(length=1000)
	private String messageContent;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="publish_date", nullable = false,
    	    columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
	private Date publishDate=new Date();
	
    @Column(name="publish_status",columnDefinition = "bit default 0",nullable=false)
	private Boolean publishStatus=false;
    
    @Column(name="recipient_count",columnDefinition = "bigint default 0",nullable=false)
	private Long recipientCount=0L;
    
    @Column(length=50, name="siren_mobile_no")
    private String sirenMobileNo;
    
    @Column(length=50, name="fax_email_address")
    private String faxEmailAddress;
    
}
