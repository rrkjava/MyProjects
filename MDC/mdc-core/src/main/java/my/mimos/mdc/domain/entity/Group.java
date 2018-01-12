package my.mimos.mdc.domain.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "mdc_mst_group")
public class Group extends EntityBase {
	
	private static final long serialVersionUID = -5066384060960015523L;

	@Id
	@Column(name = "group_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long groupId;
	
	@Column(name = "group_name",unique = true,nullable = false)
	private String groupName;	
	
	@Column(name = "group_status")
	private String groupStatus;	 

	@Column(name = "created_date")
	private Date createdDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name ="dept_id")
	private Department department;
	
	@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
    	name = "mdc_ref_user_group",
        joinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "group_id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")})
    private List<User> user;
	
}
