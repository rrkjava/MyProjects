/**
 * 
 */
package my.mimos.misos.domain.entity.trx;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name = "trx_message")
//@SequenceGenerator(name="message_seq", sequenceName="SEQ_MESSAGE", initialValue=1, allocationSize=1)
public class Message extends EntityBase {
	
	private static final long serialVersionUID = 1L;

	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="message_seq")
	@GeneratedValue(strategy=GenerationType.AUTO)	// Use auto sequence generator
	@Id
	@Column(name="message_id")
	private Long id;
	
	// Message ID from IBM-IOC
	@Column(length=20, unique=true)
	private String iocMesageId;
	
	// Referring to messageDate from IBM-IOC
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date", length=29)
	private Date createdDate;
	
	// Referring to userId from IBM-IOC
	@Column(length=50)
	private String createdBy;
	
	@Column(length=50)
	private String originator;
	
	@Column(length=50)
	private String eventId;
	
	
	@Column(name = "ref_message_type_id")
	private String messageTypeId;
	
	
	@Column(name = "ref_notification_type_id")
	private String notificationTypeId;
	
	
	@Column(name = "ref_location_type_id")
	private String locationTypeId;
	

	@Column(name = "ref_severity_level_id")
	private String severityLevelId;	
	
	@Column(name = "ref_certainty_level_id")
	private String certaintyLevelId;
	
	@Column(name = "impacted_area")
	private String impactedArea;
	
	@ElementCollection
	@CollectionTable(name="trx_poi", joinColumns=@JoinColumn(name="message_id"))
	@Column(name="poi_id")
	private List<String> poi;	
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="target_date", length=13)
	private Date targetDate;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="estimated_start_date", length=13)
	private Date estimatedStartDate;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="estimated_end_date", length=13)
	private Date estimatedEndDate;
	
	@JsonBackReference
	// A collection of channel message
	@OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChannelMessage> channelMesages = new ArrayList<ChannelMessage>();
	
	// To associate all the channel created from mapper
	public void associateAllChannel() {
		for(ChannelMessage ch : this.getChannelMesages()) {
			ch.setMessage(this);
		}
	}
}
