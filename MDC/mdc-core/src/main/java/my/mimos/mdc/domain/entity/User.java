package my.mimos.mdc.domain.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Table(name = "mdc_mst_user")
public class User  extends EntityBase {	

	private static final long serialVersionUID = -905368046991096912L;

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;	
	
	@Column(name = "username",unique = true,nullable = false)
	private String username;
	
	@Column(name = "password",nullable = false)
	private String password;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "email_id")
	private String emailId;
	
	@Column(name = "address",columnDefinition = "text")
	private String address;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name = "password_change_date")
	private Date lastPasswordReset;

	@Column(name = "approval_status")
	private String approvalStatus;
	
	@Column(name = "actived_status")
	private String activatedStatus;	
	
	/*@Column(name = "logout_date")
	private Date logoutDate;*/
	
	@Column(name = "status_comment")
	private String statusComment;
	
	@ManyToOne
	@JoinColumn(name = "dept_id")
	private Department department;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
    	name = "mdc_ref_user_role",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    private Set<Role> role;
	
	@OneToOne
	@JoinColumn(name = "upload_id")
	private Attachment upload;

	public User(User user) {
		super();
		this.userId = user.userId;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.username = user.username;
		this.password = user.password;
		this.emailId = user.emailId;
		this.createdDate = user.createdDate;
		this.updatedDate = user.updatedDate;
		this.approvalStatus = user.approvalStatus;
		this.activatedStatus = user.activatedStatus;
		this.statusComment = user.statusComment;
		this.department = user.department;
		this.role = user.role;
		this.lastPasswordReset = user.lastPasswordReset;
		this.phone=user.phone;
		this.address=user.address;
		this.upload=user.upload;
		//this.logoutDate=user.logoutDate;
	}

}
