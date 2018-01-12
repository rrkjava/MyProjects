package my.mimos.mdc.resources.user;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserResource {
	
	@NotNull(message = "first name is required")
	@NotBlank(message = "first name is required")
	@Pattern(regexp = "[a-z-A-Z]*", message = "first name has invalid characters")
	private String firstName;
	
	@NotNull(message = "last name is required")
	@NotBlank(message = "last name is required")
	@Pattern(regexp = "[a-z-A-Z]*", message = "last name has invalid characters")
	private String lastName;
	
	@NotNull(message = "username is required")
	@NotBlank(message = "username is required")
	private String username;
	
	@NotNull(message = "emailId is required")
	@NotBlank(message = "emailId is required")
	@Email(message = "email id is invalid")
	private String emailId;
	
	@NotNull(message = "phone number is required")
	@NotBlank(message = "phone number is required")
	@Pattern(regexp="(^$|[0-9]{11})")
	private String phone;
	
	@NotNull(message = "address is required")
	@NotBlank(message = "address is required")
	private String address;
	
	@NotNull(message = "department is required")	
	private DepartmentResource department;
	
	@NotNull(message = "role is required")
	private Set<RoleResource> role;
	
	private String userId;
	private String approvalStatus;	
	private String activatedStatus;	
	private String statusComment;
	private String uploadedFlag;
	/*private String password;*/
	
}
