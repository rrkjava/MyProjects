/**
 * 
 */
package my.mimos.misos.domain.entity.trx;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@Entity
@Table(name = "trx_channel_recipient")
public class ChannelRecipient {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	// Use auto sequence generator
	@Column(name="channel_recipient_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "message_id")
	private Message message;
	
	@Column(name = "ref_target_channel_type_id")
	private String targetChannelTypeId;
	
	@Column(name="contact_info", length = 100)
	private String contactInfo;
	
	@Column(name="additional_contact_info")
	private String additionalContactInfo;
	
	@Column(name = "device_id")
	private String mobileDeviceId;
}
