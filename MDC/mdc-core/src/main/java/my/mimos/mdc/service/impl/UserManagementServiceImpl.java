package my.mimos.mdc.service.impl;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import my.mimos.mdc.command.DeviceCommand;
import my.mimos.mdc.command.UserManagementCommand;
import my.mimos.mdc.domain.entity.Attachment;
import my.mimos.mdc.domain.entity.Department;
import my.mimos.mdc.domain.entity.Group;
import my.mimos.mdc.domain.entity.Login;
import my.mimos.mdc.domain.entity.MobileDevice;
import my.mimos.mdc.domain.entity.Role;
import my.mimos.mdc.domain.entity.User;
import my.mimos.mdc.domain.mapper.UserMapper;
import my.mimos.mdc.enums.StatusType;
import my.mimos.mdc.resources.BaseResponseResource;
import my.mimos.mdc.resources.device.SearchDeviceRequestResource;
import my.mimos.mdc.resources.query.AttachmentResource;
import my.mimos.mdc.resources.query.AttachmentResponseResource;
import my.mimos.mdc.resources.query.MediaUploadResponseResource;
import my.mimos.mdc.resources.user.AddDeptRequestResource;
import my.mimos.mdc.resources.user.AddGroupRequestResource;
import my.mimos.mdc.resources.user.AddGroupUserRequestResource;
import my.mimos.mdc.resources.user.AddGroupUserResponseResource;
import my.mimos.mdc.resources.user.AddRoleRequestResource;
import my.mimos.mdc.resources.user.AssignRoleRequestResource;
import my.mimos.mdc.resources.user.ChangePasswordRequestResource;
import my.mimos.mdc.resources.user.ConcatDetailsResource;
import my.mimos.mdc.resources.user.ContactDetailsRequestResource;
import my.mimos.mdc.resources.user.DepartmentResource;
import my.mimos.mdc.resources.user.ForgotPasswordRequestResource;
import my.mimos.mdc.resources.user.GetContactDetailsResponseResource;
import my.mimos.mdc.resources.user.GetDeptResponseResource;
import my.mimos.mdc.resources.user.GetDeptsResponseResource;
import my.mimos.mdc.resources.user.GetGroupProfileResponseResource;
import my.mimos.mdc.resources.user.GetRoleResponseResource;
import my.mimos.mdc.resources.user.GetRolesResponseResource;
import my.mimos.mdc.resources.user.GetUserGroupResponseResource;
import my.mimos.mdc.resources.user.GroupResource;
import my.mimos.mdc.resources.user.LoginRequestResource;
import my.mimos.mdc.resources.user.LoginResource;
import my.mimos.mdc.resources.user.LoginResponseResource;
import my.mimos.mdc.resources.user.LogoutRequestResource;
import my.mimos.mdc.resources.user.RoleResource;
import my.mimos.mdc.resources.user.SearchUsersRequestResource;
import my.mimos.mdc.resources.user.SearchUsersResponseResource;
import my.mimos.mdc.resources.user.UpdateDeptRequestResource;
import my.mimos.mdc.resources.user.UpdateGroupRequestResource;
import my.mimos.mdc.resources.user.UpdateProfileRequestResource;
import my.mimos.mdc.resources.user.UpdateRoleRequestResource;
import my.mimos.mdc.resources.user.UserActivationRequestResource;
import my.mimos.mdc.resources.user.UserActivationResponseResource;
import my.mimos.mdc.resources.user.UserProfileResponseResource;
import my.mimos.mdc.resources.user.UserRegistrationResponseResource;
import my.mimos.mdc.resources.user.UserResource;
import my.mimos.mdc.security.CustomUserDetails;
import my.mimos.mdc.service.EmailService;
import my.mimos.mdc.service.UserManagementService;
import my.mimos.mdc.utils.AuthTokenUtils;
import my.mimos.mdc.utils.CryptoUtil;
import my.mimos.mdc.utils.MdcCommonUtil;
import my.mimos.mdc.utils.MdcConstants;
import my.mimos.mdc.utils.PasswordGenerate;

@Component
@Log4j
public class UserManagementServiceImpl implements UserManagementService{
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	UserManagementCommand userManagementCommand;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	MdcCommonUtil commonUtil;
	
	@Autowired
	AuthTokenUtils authTokenUtils;
	
	@Autowired
	CryptoUtil cryptoUtil;	
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordGenerate passwordGenerate;
	
	@Autowired
	DeviceCommand deviceCommand;
	
	private static final String AUTHORIZATION_HEADER = "Authorization";
	
	public UserRegistrationResponseResource registerUser(UserResource userResource) {
		UserRegistrationResponseResource userRegistrationResponseResource = new UserRegistrationResponseResource();		
		try{
			
			//Register user in database in 'waiting for approval' status
			User user = userMapper.getMapperFacade().map(userResource, User.class);
			user.setCreatedDate(new Date());
			user.setUpdatedDate(new Date());
			user.setApprovalStatus(MdcConstants.STATUS_PENDING);
			user.setActivatedStatus(MdcConstants.STATUS_PENDING);
			user.setPassword(null);
			
			userManagementCommand.registerUser(user);
			
			// add response parameters
			userRegistrationResponseResource.setStatus("User Registration Successful.");
			userRegistrationResponseResource.setStatusCode("S0001");
			userRegistrationResponseResource.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			ex.printStackTrace();
			userRegistrationResponseResource.setStatus("User Registration Failed.");
			userRegistrationResponseResource.setStatusCode(ex.getMessage());
			userRegistrationResponseResource.setStatusType(StatusType.ERROR);
		}
		return userRegistrationResponseResource;		
	}
	
	@Override
	public UserProfileResponseResource getUserProfile(String userId) {
		UserProfileResponseResource response = new UserProfileResponseResource();		
		try{
			User existingUser = userManagementCommand.findById(userId);
			UserResource user = userMapper.getMapperFacade().map(existingUser, UserResource.class);
			response.setUser(user);
			
			response.setStatus("User profile Successfully retrieved.");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);			
			
		}catch(Exception ex){
			
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}
	

	@Override
	public UserActivationResponseResource approveUser(UserActivationRequestResource request) {
		UserActivationResponseResource response = new UserActivationResponseResource();		
		boolean flag = false;
		String statusMessage = null; 
		try{
			User existingUser = userManagementCommand.findById(request.getUserId());
			String emailContent = null;
			
			if(request.isApprove()){				
				existingUser.setApprovalStatus(MdcConstants.STATUS_ACTIVE);	
				String tempPassword = commonUtil.generateTemporaryPassword();
				
				String encryptedTempPassword = cryptoUtil.encryptText(tempPassword);
				existingUser.setPassword(encryptedTempPassword);
				
				emailContent = commonUtil.userApprovalEmailContent(existingUser.getUsername(), tempPassword, "http://mdc.mimos.my/app/download");
			
			}else{
				existingUser.setApprovalStatus(MdcConstants.STATUS_INACTIVE);
				emailContent = commonUtil.userRejectedEmailContent(request.getComment());
			}
			
			existingUser.setStatusComment(request.getComment());
			existingUser.setUpdatedDate(new Date());
			userManagementCommand.registerUser(existingUser);
			
			if(StringUtils.isNotBlank(existingUser.getEmailId())){
				flag = emailService.sendEmail(Arrays.asList(existingUser.getEmailId()), emailContent);
			}
			if(flag == false){
				statusMessage = "User status updated. Error in sending email notification to user";
			}else{
				statusMessage = "User status updated.";
			}
			response.setStatus(statusMessage);
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);			
			
		}catch(Exception ex){			
			ex.printStackTrace();			
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		
		return response;
	}
	
	@Override
	public LoginResponseResource login(LoginRequestResource request){
		
		String username = null;
		String password = null;
		String loginStatus = null;
		String userStatus= null;
		
		Login loginDetails = new Login();		
		LoginResponseResource response = new LoginResponseResource();		
		try{
			//get user credentials from request
			username = request.getUsername();
			password = request.getPassword();			
			loginDetails.setUsername(username);
			loginDetails.setLoginDate(new Date());			
			
			// authenticate user based on credentials 
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
			Authentication authentication = this.authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			//check user status
			User existingUser = getUserDetailsFromSecurityContext();			
			if(existingUser != null){
				userStatus = existingUser.getApprovalStatus();
			}else{
				throw new RuntimeException("User doesnot exist");
			}
			if(!userStatus.equalsIgnoreCase(MdcConstants.STATUS_ACTIVE)){
				throw new RuntimeException("user status is " + userStatus);
			}
			
			loginStatus = MdcConstants.LOGIN_SUCCESS;
			
			// generate user token 
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);	
			LoginResource login = new LoginResource();
			login = authTokenUtils.generateToken(userDetails);			
			User user = (User)authentication.getPrincipal();
			Long userId = user.getUserId();
			login.setUserId(userId);
			
			//Authenticate device  	
			SearchDeviceRequestResource searchCriteria = new SearchDeviceRequestResource();
			searchCriteria.setUserId(existingUser.getUserId());
			List<MobileDevice> device = deviceCommand.searchDevices(searchCriteria);
			if(null!=device && !device.isEmpty()){	
				MobileDevice mobile = device.get(0);
				Long deviceId = mobile.getDeviceId();
				login.setDeviceId(deviceId);
				loginDetails.setDeviceId(mobile);
			}		
			
			response.setAuthentication(login);
			response.setStatus("User login successful.");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){		
			// set status of login attempt
			loginStatus = MdcConstants.LOGIN_FAILED;
			
			//block user if login attempts has failed more than 3 consecutive times in the past hour
			if(countFailedLoginAttempts(username) >= MdcConstants.LOGIN_FAIL_LIMIT){
				blockUserAccount(username);
				response.setStatus("user status is " + MdcConstants.STATUS_BLOCKED);
			}
			else{
				response.setStatus(ex.getMessage());
			}
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
			
			ex.printStackTrace();
		}
		finally{
			// record the login details of the user
			loginDetails.setStatus(loginStatus);
			userManagementCommand.saveLogin(loginDetails);
		}
		return response;		
	}
	
	public User getUserDetailsFromSecurityContext(){		
		User user = null;
		try{
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
			if (principal instanceof CustomUserDetails) {
				CustomUserDetails userDetails = (CustomUserDetails) principal;
				user = userMapper.getMapperFacade().map(userDetails, User.class);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return user;
	}

	//Not required for OAuth 2.0 Implicit grant
	//Refer : https://auth0.com/docs/api-auth/grant/implicit
	@Override
	public LoginResponseResource refreshToken(HttpServletRequest request) {
		LoginResponseResource response = new LoginResponseResource();
		try{
			String authHeader = request.getHeader(AUTHORIZATION_HEADER);	
			if (authHeader == null || !authHeader.startsWith("Bearer")) {
				throw new RuntimeException("missing token in header");
			}
			else{
				String userAuthToken = authHeader.substring(7);
				LoginResource refreshedToken = authTokenUtils.refreshToken(userAuthToken);	
				
				response.setAuthentication(refreshedToken);
				response.setStatus("token refresh successful.");
				response.setStatusCode("S0001");
				response.setStatusType(StatusType.SUCCESS);
			}	
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public SearchUsersResponseResource searchUsers(SearchUsersRequestResource searchCriteria) {
		SearchUsersResponseResource response = new SearchUsersResponseResource();
		try{
			List<User> userList = userManagementCommand.searchUsers(searchCriteria);
			if(null == userList){
				response.setStatus("No results");
			}else{
				List<UserResource> users = userMapper.getMapperFacade().mapAsList(userList, UserResource.class);
				response.setUsers(users);
				response.setStatus("user search successful.");
			}
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public SearchUsersResponseResource listAllUsers() {
		SearchUsersResponseResource response = new SearchUsersResponseResource();
		try{
			List<User> userList = userManagementCommand.findAllUsers();
			List<UserResource> users = userMapper.getMapperFacade().mapAsList(userList, UserResource.class);
			response.setUsers(users);
			
		    response.setStatus("all users listed");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public BaseResponseResource changePassword(ChangePasswordRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			User existingUser = userManagementCommand.findById(request.getUserId()); 
			String encryptedPassword = cryptoUtil.encryptText(request.getNewPassword());
			existingUser.setPassword(encryptedPassword);
			existingUser.setLastPasswordReset(new Date());
			userManagementCommand.registerUser(existingUser);
			
			response.setStatus("password updated");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public BaseResponseResource updateUserProfile(UpdateProfileRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			User existingUser = userManagementCommand.findById(request.getUserId()); 
			if(null != request.getFirstName()){
				existingUser.setFirstName(request.getFirstName());
			}
			if(null != request.getLastName()){
				existingUser.setLastName(request.getLastName());
			}
			if(null != request.getEmail()){
				existingUser.setEmailId(request.getEmail());
			}
			if(null != request.getPhone()){
				existingUser.setEmailId(request.getPhone());
			}
			if(null != request.getAddress()){
				existingUser.setEmailId(request.getAddress());
			}
			existingUser.setUpdatedDate(new Date());
			userManagementCommand.registerUser(existingUser);
			
			response.setStatus("user profile updated");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public BaseResponseResource addRole(AddRoleRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			Role role = new Role();
			role.setRoleName(request.getRoleName());
			role.setRoleDesc(request.getRoleDesc());
			userManagementCommand.saveRole(role);
			
			response.setStatus("role added");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public BaseResponseResource updateRole(UpdateRoleRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			Role role = userManagementCommand.getRolebyId(request.getRoleId());
			if(StringUtils.isNotBlank(request.getRoleName())){
				role.setRoleName(request.getRoleName());
			}
			if(StringUtils.isNotBlank(request.getRoleDesc())){
				role.setRoleDesc(request.getRoleDesc());
			}
			userManagementCommand.saveRole(role);
			
			response.setStatus("role updated");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public BaseResponseResource removeRole(Long roleId) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			userManagementCommand.deleteRole(roleId);
			
			response.setStatus("role deleted");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public GetRolesResponseResource listAllRoles() {
		GetRolesResponseResource response = new GetRolesResponseResource();
		try{
			List<Role> roleList = userManagementCommand.getAllRoles();
			List<RoleResource> roles = userMapper.getMapperFacade().mapAsList(roleList, RoleResource.class);
			response.setRoles(roles);
			
			response.setStatus("roles listed");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public GetRoleResponseResource getRole(Long roleId) {
		GetRoleResponseResource response = new GetRoleResponseResource();
		try{
			Role role = userManagementCommand.getRolebyId(roleId);
			RoleResource roleResource= userMapper.getMapperFacade().map(role, RoleResource.class);
			response.setRole(roleResource);
			
			response.setStatus("roles listed");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public BaseResponseResource assignRole(AssignRoleRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			User existingUser = userManagementCommand.findById(request.getUserId());
			Role existingRole = userManagementCommand.getRolebyId(request.getRoleId());
			if(null == existingRole){
				throw new RuntimeException("role doesnot exist");
			}
			Set<Role> userRoles = existingUser.getRole();
			if(null != userRoles){
				userRoles.add(existingRole);
			}else{
				userRoles = new HashSet<Role>();
				userRoles.add(existingRole);
			}	
			existingUser.setRole(userRoles);
			userManagementCommand.registerUser(existingUser);
			
			response.setStatus("role added to user");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public BaseResponseResource deassignRole(AssignRoleRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			User existingUser = userManagementCommand.findById(request.getUserId());
			Role existingRole = userManagementCommand.getRolebyId(request.getRoleId());
			if(null == existingRole){
				throw new RuntimeException("role doesnot exist");
			}
			Set<Role> userRoles = existingUser.getRole();
			if(null != userRoles){
				userRoles.remove(existingRole);
			}			
			existingUser.setRole(userRoles);
			userManagementCommand.registerUser(existingUser);
			
			response.setStatus("role removed from user");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;	
	}

	@Override
	public BaseResponseResource addDepartment(AddDeptRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			Department dept = new Department();
			dept.setDeptName(request.getDeptName());
			userManagementCommand.saveDept(dept);
			
			response.setStatus("department added");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	
	}

	@Override
	public GetDeptResponseResource getDepartment(Long deptId) {
		GetDeptResponseResource response = new GetDeptResponseResource();
		try{
			Department dept = userManagementCommand.getDepartmentbyId(deptId);
			DepartmentResource department= userMapper.getMapperFacade().map(dept, DepartmentResource.class);
			response.setDepartment(department);
			
			response.setStatus("department listed");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public GetDeptsResponseResource listAllDepartments() {
		GetDeptsResponseResource response = new GetDeptsResponseResource();
		try{
			List<Department> deptList = userManagementCommand.getAllDepartments();
			List<DepartmentResource> depts = userMapper.getMapperFacade().mapAsList(deptList, DepartmentResource.class);
			response.setDepartments(depts);
			
			response.setStatus("departments listed");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public BaseResponseResource removeDepartment(Long deptId) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			userManagementCommand.deleteDept(deptId);
			
			response.setStatus("department deleted");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public BaseResponseResource updateDepartment(UpdateDeptRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			Department dept = userManagementCommand.getDepartmentbyId(request.getDeptId());
			if(StringUtils.isNotBlank(request.getDeptName())){
				dept.setDeptName(request.getDeptName());
			}
			userManagementCommand.saveDept(dept);
			
			response.setStatus("role updated");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
			
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public BaseResponseResource addGroup(AddGroupRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		try {
			Group group = new Group();
			group.setGroupName(request.getGroupName());
			group.setGroupStatus(MdcConstants.STATUS_ACTIVE);
			group.setCreatedDate(new Date());
			Department dept = userManagementCommand.getDepartmentbyId(request.getDeptId());
			if (dept != null) {
				group.setDepartment(dept);
			} else {
				throw new RuntimeException("department doesnot exist");
			}
			userManagementCommand.saveGroup(group);
			response.setStatus("group created");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}
	
	@Override
	public AddGroupUserResponseResource addGroupUser(AddGroupUserRequestResource request) {
		AddGroupUserResponseResource response = new AddGroupUserResponseResource();
		try {

			Long groupId = request.getGroupId();
			Group group = userManagementCommand.findGroupById(groupId);
			List<User> existingGrpUsers = group.getUser();

			List<Long> userIdList = request.getUserIds();
			List<User> existingUsers = userManagementCommand.findByIds(userIdList);
			List<Long> rejectedUsers=new ArrayList<Long>();
			List<Long> addedUsers=new ArrayList<Long>();

			for (User user : existingUsers) {
				if (!existingGrpUsers.contains(user)) {
					if (group.getDepartment().equals(user.getDepartment())) {
						existingGrpUsers.add(user);
						addedUsers.add(user.getUserId());
					} else {
						rejectedUsers.add(user.getUserId());
					}
				}
			}
			
			group.setUser(existingGrpUsers);
			userManagementCommand.saveGroup(group);
			response.setAddedUsers(addedUsers);
			response.setRejectedUsers(rejectedUsers);
			response.setStatus("users added to group");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}
	
	
	@Override
	public BaseResponseResource removeGroupUser(AddGroupUserRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		try {

			Long groupId = request.getGroupId();
			Group group = userManagementCommand.findGroupById(groupId);
			List<User> existingGrpUsers = group.getUser();

			List<Long> userIdList = request.getUserIds();
			List<User> existingUsers = userManagementCommand.findByIds(userIdList);

			for (User user : existingUsers) {
				if (existingGrpUsers.contains(user)) {
					existingGrpUsers.remove(user);
				}
			}
			group.setUser(existingGrpUsers);
			userManagementCommand.saveGroup(group);
			log.debug("users removed successfully!");
			response.setStatus("users removed from group");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public BaseResponseResource updateGroup(UpdateGroupRequestResource request) {
		
		BaseResponseResource response = new BaseResponseResource();
		try {
			Long groupId = request.getGroupId();
			Group group = userManagementCommand.findGroupById(groupId);
			group.setGroupName(request.getGroupName());
			if(StringUtils.isNotBlank(request.getGroupStatus())){
				group.setGroupStatus(request.getGroupStatus());	
			}
			group.setCreatedDate(new Date());
			userManagementCommand.saveGroup(group);
			response.setStatus("group is updated");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public GetUserGroupResponseResource listAllGroups() {

		GetUserGroupResponseResource response = new GetUserGroupResponseResource();
		try {
			List<Group> groupList = userManagementCommand.getAllGroups();
			List<GroupResource> groups = userMapper.getMapperFacade().mapAsList(groupList, GroupResource.class);
			response.setGroups(groups);
			response.setStatus("groups listed");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public BaseResponseResource removeGroup(Long groupId) {
		
		BaseResponseResource response = new BaseResponseResource();
		try {
			Group group = userManagementCommand.findGroupById(groupId);
			List<User> groupUsers=group.getUser();
			groupUsers.removeAll(groupUsers);
			group.setUser(groupUsers);
			group.setDepartment(null);
			userManagementCommand.deleteGroup(group);
			response.setStatus("group is removed");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public GetGroupProfileResponseResource groupProfile(Long groupId) {
		
		GetGroupProfileResponseResource response = new GetGroupProfileResponseResource();
		try {
			Group group = userManagementCommand.findGroupById(groupId);
			response.setGroup(userMapper.getMapperFacade().map(group, GroupResource.class));
			response.setDepartment(userMapper.getMapperFacade().map(group.getDepartment(), DepartmentResource.class));
			response.setUsers(userMapper.getMapperFacade().mapAsList(group.getUser(), UserResource.class));
			response.setStatus("group profile");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}
	
	
	public int countFailedLoginAttempts(String username){		
		int count = 0;
		try{	
			//set the date to (current time stamp - 1 hour)
			//Date startDate = new Date(System.currentTimeMillis() - 3600 * 1000);
			
			//set the date to (current time stamp - 1/2 hour)
			Date startDate = new Date(System.currentTimeMillis() - 1800 * 1000);
			
			//set the date to the last successful login in past 1/2 an hour
			Date lastSuccessfulLoginDate = userManagementCommand.getLastSuccessfulLogin(username, MdcConstants.LOGIN_SUCCESS, startDate);
			if(null != lastSuccessfulLoginDate){
				startDate = lastSuccessfulLoginDate;
			}			
			//count of latest consecutive failure attempts in past 1/2hour
			count = userManagementCommand.countFailedLogin(username, MdcConstants.LOGIN_FAILED, startDate);
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return count;		
	}
	
	public void blockUserAccount(String username){
		try{
			User existingUser = userManagementCommand.findByUsername(username);
			existingUser.setApprovalStatus(MdcConstants.STATUS_BLOCKED);
			userManagementCommand.registerUser(existingUser);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void unblockUserAccount(String username){
		try{
			User existingUser = userManagementCommand.findByUsername(username);
			if(existingUser.getApprovalStatus().equalsIgnoreCase(MdcConstants.STATUS_BLOCKED)){
				existingUser.setApprovalStatus(MdcConstants.STATUS_ACTIVE);
			}
			userManagementCommand.registerUser(existingUser);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public BaseResponseResource forgotPassword(ForgotPasswordRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		try{
			boolean flag = false;
			String statusMessage = null; 
			User existingUser = userManagementCommand.findByUsername(request.getUsername());
			existingUser.setLastPasswordReset(new Date());
//			String newpassword=passwordGenerate.generateRandomPassword();
			String encryptedPassword = cryptoUtil.encryptText("MecaKDN@123");
//			existingUser.setPassword(encryptedPassword);
//			if(StringUtils.isNotBlank(existingUser.getEmailId())){
//				flag = emailService.sendEmail(Arrays.asList(existingUser.getEmailId()), commonUtil.resetPasswordEmailContent(newpassword));
//			} 
//			if(flag == false){
//				statusMessage = "Error in sending email resetpassword to user";
//				throw new RuntimeException(statusMessage);
//			} else {
//				userManagementCommand.registerUser(existingUser);
//				statusMessage = "Resetpassword success,New password sent to your email";
//			}
			response.setStatus(encryptedPassword);
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		}catch(Exception ex){
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}
	
	@Override
	public BaseResponseResource logout(LogoutRequestResource request) {
		BaseResponseResource response = new BaseResponseResource();
		try {		
			//need to be uncommented when device auth is added
			/*User existingUser = userManagementCommand.findById(request.getUserId());
			MobileDevice device = deviceCommand.findDevice(request.getDeviceId());
			Login loginDetails = userManagementCommand.findLatestDeviceLoginDetails(device, existingUser.getUsername());	
			if(loginDetails == null){
				throw new RuntimeException("user is not logged in");
			}
			loginDetails.setLogoutDate(new Date());
			userManagementCommand.saveLogin(loginDetails);*/
			
			response.setStatus("logout successfully");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);

		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public GetContactDetailsResponseResource getContactDetails(ContactDetailsRequestResource request) {
		GetContactDetailsResponseResource response = new GetContactDetailsResponseResource();
		try {
			// USER DETAILS
			User existingUser = userManagementCommand.findById(request.getUserId());
			SearchUsersRequestResource searchCriteria = new SearchUsersRequestResource();
			if (!CollectionUtils.isEmpty(existingUser.getRole())) {
				for (Role role : existingUser.getRole()) {
					searchCriteria.setUserRole(role.getRoleName());
					if(role.getRoleName().equalsIgnoreCase(MdcConstants.ROLE_ADMIN)){
						searchCriteria.setUserRole("%");
					}
				}
			} else {
				throw new RuntimeException("Role doesnot exist");
			}
			List<User> userList = userManagementCommand.searchUsers(searchCriteria);
			List<ConcatDetailsResource> concatDetails=userMapper.getMapperFacade().mapAsList(userList, ConcatDetailsResource.class);
			response.setConcatDetails(concatDetails);
			response.setStatus("Contact Deatils Retrieved");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

	@Override
	public MediaUploadResponseResource uploadFlag(MultipartFile multipartFile, String title, Long userId) {
		MediaUploadResponseResource response = new MediaUploadResponseResource();
		String statusMsg = "";
		try {
			long mediaSize = multipartFile.getSize();
			String contentType = multipartFile.getContentType();
			byte[] byteArray = multipartFile.getBytes();
			InputStream in = new ByteArrayInputStream(byteArray);
			BufferedImage image = ImageIO.read(in);
			if (image == null) {
				statusMsg = "file should be image format";
				throw new RuntimeException(statusMsg);
			}
			byte[] resizeByteArray = createResizedCopy(byteArray, 512, 512);
			Attachment attachment = new Attachment();
			attachment.setContentType(contentType);
			attachment.setUploadTitle(title);
			attachment.setUploadDate(new Date());
			attachment.setContent(resizeByteArray);
			attachment.setUploadSize(mediaSize);
			User loggedInUser = getUserDetailsFromSecurityContext();
			attachment.setUploadBy(loggedInUser);
			Attachment savedAttachment = userManagementCommand.saveAttachment(attachment);
			// USER DETAILS
			User existingUser = userManagementCommand.findById(userId);
			if (savedAttachment.getUploadId() != null) {
				existingUser.setUpload(savedAttachment);
				userManagementCommand.registerUser(existingUser);
			} else {
				statusMsg = "image upload fail";
				throw new RuntimeException(statusMsg);
			}
			response.setUploadId(savedAttachment.getUploadId());
			statusMsg = "flag upload successful";
			response.setStatus(statusMsg);
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus(ex.getMessage());
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}
	
	public byte[] createResizedCopy(byte[] byteArray,int scaledWidth, int scaledHeight)
	{
		byte[] imageBytes = null;
		try {
			// converting byteArray to image
			InputStream in = new ByteArrayInputStream(byteArray);
			BufferedImage image = ImageIO.read(in);

			if (image.getHeight() <= scaledWidth && image.getWidth() <= scaledHeight) {
				return byteArray;
			}
			// Image rescaled
			int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
			BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, type);
			Graphics2D g = (Graphics2D) scaledBI.createGraphics();
			g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
			g.dispose();
			// image to bytes
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(scaledBI, "jpg", baos);
			baos.flush();
			imageBytes = baos.toByteArray();
			baos.close();
			log.debug("Image resized successfully!");
		} catch (IOException e) {
			log.debug("Eorro in resized image "+ e.getMessage());
		}
		return imageBytes;
	}

	@Override
	public AttachmentResponseResource getFlag(Long uploadId) {
		AttachmentResponseResource response = new AttachmentResponseResource();
		try {
			Attachment uploadedAttachment = userManagementCommand.getAttachment(uploadId);
			AttachmentResource attachment = userMapper.getMapperFacade().map(uploadedAttachment,AttachmentResource.class);
			response.setAttachment(attachment);
			response.setStatus("flag retrieved successful");
			response.setStatusCode("S0001");
			response.setStatusType(StatusType.SUCCESS);
		} catch (Exception ex) {
			ex.printStackTrace();
			response.setStatus("flag to retrieve attachment");
			response.setStatusCode("E0001");
			response.setStatusType(StatusType.ERROR);
		}
		return response;
	}

}
