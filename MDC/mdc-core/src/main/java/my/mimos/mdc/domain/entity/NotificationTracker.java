package my.mimos.mdc.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "mdc_trx_notifcation_tracker")
public class NotificationTracker {
	
	@Id
	@Column(name = "tracker_id")
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long trackerId;
	
	@Column(name = "query_id")
	private String queryId;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "devices")
	private String devices;
	
	@Column(name = "notification_date")
	private Date notificationDate;

}
