package my.mimos.mdc.domain.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author krishna.redabotu
 *
 */


@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "mdc_mst_role")
public class Role  extends EntityBase {	
	
	private static final long serialVersionUID = 4149707239336024643L;

	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long roleId;
	
	@Column(name = "role_name")
	private String roleName;
	
	@Column(name = "role_desc")
	private String roleDesc;

}
