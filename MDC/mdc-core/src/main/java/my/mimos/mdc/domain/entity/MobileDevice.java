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
import javax.persistence.UniqueConstraint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "mdc_mst_device",uniqueConstraints = @UniqueConstraint(columnNames={"device_token"}))
public class MobileDevice {
	
	@Id
	@Column(name = "device_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long deviceId;
	
	@Column(name = "device_type")
	private String deviceType;
	
	@Column(name = "device_token")
	private String deviceToken;	
	
	@Column(name = "device_status")
	private String deviceStatus;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	
}
