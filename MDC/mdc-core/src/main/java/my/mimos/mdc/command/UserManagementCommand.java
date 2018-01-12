package my.mimos.mdc.command;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import my.mimos.mdc.domain.entity.Attachment;
import my.mimos.mdc.domain.entity.Department;
import my.mimos.mdc.domain.entity.Group;
import my.mimos.mdc.domain.entity.Login;
import my.mimos.mdc.domain.entity.MobileDevice;
import my.mimos.mdc.domain.entity.Role;
import my.mimos.mdc.domain.entity.User;
import my.mimos.mdc.resources.user.SearchGroupRequestResource;
import my.mimos.mdc.resources.user.SearchUsersRequestResource;

/**
 * 
 * @author beaula.fernandez
 *
 */

@Component
public interface UserManagementCommand {
	
	public void registerUser(User user);
	
	public User findById(String userId);
	
	public User findById(Long userId);
	
	public User findByUsername(String userId);

	public List<User> findByIds(List<Long> userIdList);
	
	public List<User> searchUsers(SearchUsersRequestResource searchCriteria);

	public List<User> findAllUsers();

	public void saveRole(Role role);

	public Role getRolebyId(Long roleId);

	public List<Role> getAllRoles();

	public void deleteRole(Long roleId);

	public void saveDept(Department dept);

	public Department getDepartmentbyId(Long deptId);

	public List<Department> getAllDepartments();

	public void deleteDept(Long deptId);
	
	public void saveGroup(Group group);
	
	public void deleteGroup(Group group);
	
	public Group findGroupById(Long groupId);
	
	public List<Group> getAllGroups();

	public void saveLogin(Login login);

	public int countFailedLogin(String username, String status, Date loginDate);

	public Date getLastSuccessfulLogin(String username, String status, Date loginDate);
	
	public List<Group> findByGroupIds(List<Long> groupIdList);

	public List<Group> searchGroups(SearchGroupRequestResource searchCriteria);

	public Login findLatestDeviceLoginDetails(MobileDevice device, String username);
	
	public Attachment saveAttachment(Attachment attachment);
	
	public Attachment getAttachment(Long uploadId);

}
