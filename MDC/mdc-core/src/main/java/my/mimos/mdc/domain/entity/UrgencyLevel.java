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
/**
 * 
 * @author beaula.fernandez
 *
 */

@Getter 
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "mdc_mst_urgency_level")
public class UrgencyLevel extends EntityBase {	

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "urgency_level_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long urgencyLevelId;
	
	@Column(name = "urgency_level")
	private String urgencyLevel;
	
	@Column(name = "priority")
	private String priority;
	
	@Column(name = "createdDate")
	private Date createdDate;
	
	@Column(name = "modifiedDate")
	private Date modifiedDate;
	
}
