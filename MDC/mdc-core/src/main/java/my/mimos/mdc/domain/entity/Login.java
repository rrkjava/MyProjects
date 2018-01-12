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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * 
 * @author beaula.fernandez
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mdc_trx_login_audit")
public class Login {
	
	@Id
	@Column(name = "login_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long loginId;
	
	@Column(name = "username")
	private String username;
	
	@ManyToOne
	@JoinColumn(name = "device_id")
	private MobileDevice deviceId;
	
	@Column(name = "login_date")
	private Date loginDate;
	
	@Column(name = "logout_date")
	private Date logoutDate;
	
	@Column(name = "status")
	private String status;

}
