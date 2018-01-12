package my.mimos.mdc.domain.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author beaula.fernandez
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
/*@Entity
@Table(name = "mdc_ref_user_role")*/
public class UserRole  extends EntityBase {	
	
	@Id
	@Column(name = "user_role_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userRoleId;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}
