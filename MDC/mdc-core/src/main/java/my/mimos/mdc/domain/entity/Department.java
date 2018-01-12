package my.mimos.mdc.domain.entity;

import javax.persistence.CascadeType;
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
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "mdc_mst_dept")
public class Department  extends EntityBase {	
	private static final long serialVersionUID = -5688730818944115493L;

	@Id
	@Column(name = "dept_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long deptId;
	
	@Column(name = "dept_name",unique = true)
	private String deptName;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="parent_id",referencedColumnName="dept_id")
	private Department department;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "dept_type_id")
	private DepartmentType departmentType;

}
