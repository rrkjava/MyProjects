package my.mimos.mdc.command.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.querydsl.core.BooleanBuilder;

import my.mimos.mdc.command.UserManagementCommand;
import my.mimos.mdc.domain.entity.Attachment;
import my.mimos.mdc.domain.entity.Department;
import my.mimos.mdc.domain.entity.Group;
import my.mimos.mdc.domain.entity.Login;
import my.mimos.mdc.domain.entity.MobileDevice;
import my.mimos.mdc.domain.entity.QGroup;
import my.mimos.mdc.domain.entity.QUser;
import my.mimos.mdc.domain.entity.Role;
import my.mimos.mdc.domain.entity.User;
import my.mimos.mdc.domain.repository.AttachmentRepository;
import my.mimos.mdc.domain.repository.DepartmentRepository;
import my.mimos.mdc.domain.repository.GroupRepository;
import my.mimos.mdc.domain.repository.LoginRepository;
import my.mimos.mdc.domain.repository.RoleRepository;
import my.mimos.mdc.domain.repository.UserRepository;
import my.mimos.mdc.resources.user.SearchGroupRequestResource;
import my.mimos.mdc.resources.user.SearchUsersRequestResource;

/**
 * 
 * @author beaula.fernandez
 *
 */

@Component
public class UserManagementCommandHandler implements UserManagementCommand{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	DepartmentRepository deptRepository;
	
	@Autowired
	GroupRepository groupRepository;
	
	@Autowired
	LoginRepository loginRepository;
	
	@Autowired
	AttachmentRepository attachmentRepository;

	public void registerUser(User user) {
		try{
			userRepository.save(user);
		}
		catch(DataIntegrityViolationException dataException){
			// Log the error trace 
			// throw a short error code
			throw new RuntimeException(dataException.getMessage());
		}catch(Exception ex){;
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public User findById(Long userId) {
		User existingUser= null;
		try{
			existingUser = userRepository.findOne(userId);
			if(null == existingUser){
				throw new IllegalStateException("user doesnot exist");
			}
		}
		catch(Exception ex){
			throw new IllegalStateException(ex.getMessage());
		}
		return existingUser;
	}

	@Override
	public User findById(String userId) {
		User existingUser= null;
		try{
			existingUser = userRepository.findOne(Long.valueOf(userId));
			if(null == existingUser){
				throw new IllegalStateException("user doesnot exist");
			}
		}
		catch(NumberFormatException numbeFormatEx){
			throw new IllegalStateException("invalid datatype for id");
		}
		catch(Exception ex){
			throw new IllegalStateException(ex.getMessage());
		}
		return existingUser;
	}
	
	@Override
	public User findByUsername(String username) {
		User existingUser= null;
		try{
			existingUser = userRepository.findByUsername(username);
		}catch(Exception ex){
			throw new IllegalStateException(ex.getMessage());
		}
		return existingUser;		
	}
	
	@Override
	public List<User> findByIds(List<Long> userIdList) {
		List<User> existingUsers= null;
		try{
			existingUsers = userRepository.findByUserIdIn(userIdList);
		}
		catch(NumberFormatException numbeFormatEx){
			throw new IllegalStateException("invalid datatype for id");
		}
		catch(Exception ex){
			throw new IllegalStateException(ex.getMessage());
		}
		return existingUsers;
	}

	@Override
	public List<User> searchUsers(SearchUsersRequestResource searchCriteria) {
		List<User> filteredResult = null;
		try{
			QUser user = QUser.user;
			BooleanBuilder where = new BooleanBuilder();
			
			if(null != searchCriteria.getDeptId()){
				where.and(user.department.deptId.eq(Long.valueOf(searchCriteria.getDeptId())));
			}
			if(StringUtils.isNotBlank(searchCriteria.getUserStatus())){
				where.and(user.approvalStatus.like(searchCriteria.getUserStatus()));
			}			
			if(null != searchCriteria.getUserIds() && searchCriteria.getUserIds().size()!= 0){
				where.and(user.userId.in(searchCriteria.getUserIds()));
			}
			if(null != searchCriteria.getDeptIds() && searchCriteria.getDeptIds().size()!= 0){
				where.and(user.department.deptId.in(searchCriteria.getDeptIds()));
			}
			if(StringUtils.isNotBlank(searchCriteria.getDeptName())){
				where.and(user.department.deptName.like(searchCriteria.getDeptName()));
			}
			if(StringUtils.isNotBlank(searchCriteria.getUserRole())){
				where.and(user.role.any().roleName.like(searchCriteria.getUserRole()));
			}
			if(where.hasValue()){
				filteredResult = (List<User>) userRepository.findAll(where);	
			}
		}catch(Exception ex){
			ex.printStackTrace();		
			throw new RuntimeException(ex);
		}
		return filteredResult;
	}


	@Override
	public List<Group> searchGroups(SearchGroupRequestResource searchCriteria) {
		List<Group> filteredResult = null;
		try{
			QGroup group = QGroup.group;
			BooleanBuilder where = new BooleanBuilder();
			
			if(null != searchCriteria.getDeptId()){
				where.and(group.department.deptId.eq(searchCriteria.getDeptId()));
			}					
			if(null != searchCriteria.getUserId()){
				where.and(group.user.any().userId.eq(searchCriteria.getUserId()));
			}			
			if(where.hasValue()){
				filteredResult = (List<Group>) groupRepository.findAll(where);	
			}
		}catch(Exception ex){
			ex.printStackTrace();		
			throw new RuntimeException(ex);
		}
		return filteredResult;
	}

	@Override
	public List<User> findAllUsers() {
		List<User> users = null;
		try{
			users = userRepository.findAll();		
		}catch(Exception ex){
			ex.printStackTrace();		
			throw new RuntimeException(ex);
		}
		return users;
	}

	@Override
	public void saveRole(Role role) {
		try{
			roleRepository.save(role);
		}
		catch(DataIntegrityViolationException dataException){
			throw new RuntimeException(dataException.getMessage());
		}catch(Exception ex){;
			throw new RuntimeException(ex);
		}		
	}

	@Override
	public Role getRolebyId(Long roleId) {
		Role role = null;
		try{
			role = roleRepository.findOne(roleId);
		}
		catch(DataIntegrityViolationException dataException){
			throw new RuntimeException(dataException.getMessage());
		}catch(Exception ex){;
			throw new RuntimeException(ex);
		}	
		return role;
	}

	@Override
	public List<Role> getAllRoles() {
		List<Role> roles = null;
		try{
			roles = roleRepository.findAll();
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
		return roles;
	}
	
	public void deleteRole(Long roleId){
		try{
			roleRepository.delete(roleId);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void saveDept(Department dept) {
		try{
			deptRepository.save(dept);
		}
		catch(DataIntegrityViolationException dataException){
			throw new RuntimeException(dataException.getMessage());
		}catch(Exception ex){;
			throw new RuntimeException(ex);
		}			
	}

	@Override
	public Department getDepartmentbyId(Long deptId) {
		Department dept = null;
		try{
			dept = deptRepository.findOne(deptId);
		}
		catch(DataIntegrityViolationException dataException){
			throw new RuntimeException(dataException.getMessage());
		}catch(Exception ex){;
			throw new RuntimeException(ex);
		}	
		return dept;
	}

	@Override
	public List<Department> getAllDepartments() {
		List<Department> depts = null;
		try{
			depts = deptRepository.findAll();
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
		return depts;
	}

	@Override
	public void deleteDept(Long deptId) {
		try {
			deptRepository.delete(deptId);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void saveGroup(Group group) {
		try {
			groupRepository.save(group);
		} catch (DataIntegrityViolationException dataException) {
			throw new RuntimeException(dataException.getMessage());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
	@Override
	public Group findGroupById(Long groupId) {
		Group group = null;
		try {
			group = groupRepository.findOne(groupId);
			if (null == group) {
				throw new IllegalStateException("group doesnot exist");
			}
		} catch (Exception ex) {
			throw new IllegalStateException(ex.getMessage());
		}
		return group;
	}

	@Override
	public List<Group> getAllGroups() {
		List<Group> groups = null;
		try {
			groups = groupRepository.findAll();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return groups;
	}

	@Override
	public void deleteGroup(Group group) {
		try {
			groupRepository.delete(group);
		} catch (DataIntegrityViolationException dataException) {
			throw new RuntimeException(dataException.getMessage());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public void saveLogin(Login login) {
		try {
			loginRepository.save(login);
		} catch (DataIntegrityViolationException dataException) {
			throw new RuntimeException(dataException.getMessage());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public List<Group> findByGroupIds(List<Long> groupIdList) {
		List<Group> existingUsers = new ArrayList<Group>();
		try {
			if (groupIdList != null) {
				existingUsers = groupRepository.findByGroupIdIn(groupIdList);
			}
		} catch (NumberFormatException numbeFormatEx) {
			throw new IllegalStateException("invalid datatype for id");
		} catch (Exception ex) {
			throw new IllegalStateException(ex.getMessage());
		}
		return existingUsers;
	}
	
	@Override
	public Date getLastSuccessfulLogin(String username,String status, Date loginDate) {
		Date lastSuccessfulLoginDate = null;
		try {
			lastSuccessfulLoginDate = loginRepository.findLastSuccessfulLoginDate(username, status,loginDate);			
		}catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return lastSuccessfulLoginDate;
	}
	
	@Override
	public int countFailedLogin(String username, String status, Date loginDate) {
		int count = 0;
		try {
			count = loginRepository.countFailedLogin(username, status , loginDate);
		}catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		return count;
	}
	
	public Login findLatestDeviceLoginDetails(MobileDevice device, String username){
		Login loginDetails = null;
		try {
			loginDetails = loginRepository.findLoginDetails(username,device);
		}catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		return loginDetails;
		
	}
	
	
	@Override
	public Attachment saveAttachment(Attachment attachment) {
		Attachment savedItem = null;
		try{
			savedItem = attachmentRepository.save(attachment);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return savedItem;
	}
	
	@Override
	public Attachment getAttachment(Long uploadId) {
		Attachment attachment = null;
		try{
			attachment = attachmentRepository.findOne(uploadId);			
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
		return attachment;
	}

}
