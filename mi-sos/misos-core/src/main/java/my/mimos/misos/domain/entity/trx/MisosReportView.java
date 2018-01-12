package my.mimos.misos.domain.entity.trx;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@Entity
@Table(name = "v_misos_report")
public class MisosReportView {
	@Id
	@Column(name="row")
	private Long id;
	
	@Column(name="ioc_mesage_id")
	private String iocMessageId;
	
	@Column(name="message_content")
	private String messageContent;	
	
	@Column(name="target_date")
	private Date targetDate;
	
	@Column(name="created_date")
	private Date createdDate;
	
	@Column(name="originator")
	private String originator;
	
	@Column(name="ref_target_channel_type_id")
	private String targetChannel;
	
	@Column(name="ref_severity_level_id")
	private String severityLevel;
	
	@Column(name="ref_notification_type_id")
	private String notificationType;
	
	@Column(name="ref_subscriber_type_id")
	private String targetGroup;
	
	@Column(name="recipient_count")
	private String recipientCount;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="publish_date")
	private Date publishDate;
	
    @Column(name="publish_status")
	private Boolean publishStatus;
    
    @Transient
	private String fromDate;
    
    @Transient
	private String toDate;


}
