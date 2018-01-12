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
@Table(name = "mdc_mst_dept_type")
public class DepartmentType extends EntityBase {	
	
	private static final long serialVersionUID = 5724841645253121814L;

	@Id
	@Column(name = "dept_type_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long deptTypeId;
	
	@Column(name = "dept_type_name")
	private String deptTypeName;
	
	@Column(name = "dept_type_desc")
	private String deptTypeDesc;
	
}
