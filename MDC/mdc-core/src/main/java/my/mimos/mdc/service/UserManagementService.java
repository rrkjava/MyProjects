package my.mimos.mdc.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import my.mimos.mdc.domain.entity.User;
import my.mimos.mdc.resources.BaseResponseResource;
import my.mimos.mdc.resources.query.AttachmentResponseResource;
import my.mimos.mdc.resources.query.MediaUploadResponseResource;
import my.mimos.mdc.resources.user.AddDeptRequestResource;
import my.mimos.mdc.resources.user.AddGroupRequestResource;
import my.mimos.mdc.resources.user.AddGroupUserRequestResource;
import my.mimos.mdc.resources.user.AddGroupUserResponseResource;
import my.mimos.mdc.resources.user.AddRoleRequestResource;
import my.mimos.mdc.resources.user.AssignRoleRequestResource;
import my.mimos.mdc.resources.user.ChangePasswordRequestResource;
import my.mimos.mdc.resources.user.ContactDetailsRequestResource;
import my.mimos.mdc.resources.user.ForgotPasswordRequestResource;
import my.mimos.mdc.resources.user.GetContactDetailsResponseResource;
import my.mimos.mdc.resources.user.GetDeptResponseResource;
import my.mimos.mdc.resources.user.GetDeptsResponseResource;
import my.mimos.mdc.resources.user.GetGroupProfileResponseResource;
import my.mimos.mdc.resources.user.GetRoleResponseResource;
import my.mimos.mdc.resources.user.GetRolesResponseResource;
import my.mimos.mdc.resources.user.GetUserGroupResponseResource;
import my.mimos.mdc.resources.user.LoginRequestResource;
import my.mimos.mdc.resources.user.LoginResponseResource;
import my.mimos.mdc.resources.user.LogoutRequestResource;
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

@Component
public interface UserManagementService {

	public UserRegistrationResponseResource registerUser(UserResource user);

	public UserProfileResponseResource getUserProfile(String userId);
	
	public UserActivationResponseResource approveUser(UserActivationRequestResource request);
	
	public LoginResponseResource login(LoginRequestResource request);
	
	public User getUserDetailsFromSecurityContext();

	public LoginResponseResource refreshToken(HttpServletRequest request);

	public SearchUsersResponseResource searchUsers(SearchUsersRequestResource request);

	public SearchUsersResponseResource listAllUsers();

	public BaseResponseResource changePassword(ChangePasswordRequestResource request);

	public BaseResponseResource updateUserProfile(UpdateProfileRequestResource request);

	public BaseResponseResource addRole(AddRoleRequestResource request);

	public BaseResponseResource updateRole(UpdateRoleRequestResource request);

	public BaseResponseResource removeRole(Long roleId);

	public GetRolesResponseResource listAllRoles();

	public GetRoleResponseResource getRole(Long roleId);

	public BaseResponseResource assignRole(AssignRoleRequestResource request);

	public BaseResponseResource deassignRole(AssignRoleRequestResource request);

	public BaseResponseResource addDepartment(AddDeptRequestResource request);

	public GetDeptResponseResource getDepartment(Long deptId);

	public GetDeptsResponseResource listAllDepartments();

	public BaseResponseResource removeDepartment(Long deptId);

	public BaseResponseResource updateDepartment(UpdateDeptRequestResource request);
	
	public BaseResponseResource addGroup(AddGroupRequestResource request);
	
	public BaseResponseResource updateGroup(UpdateGroupRequestResource request);
	
	public GetUserGroupResponseResource listAllGroups();
	
	public BaseResponseResource removeGroup(Long groupId);
	
	public AddGroupUserResponseResource addGroupUser(AddGroupUserRequestResource request);
	
	public BaseResponseResource removeGroupUser(AddGroupUserRequestResource request);
	
	public GetGroupProfileResponseResource groupProfile(Long gorupId);
	
	public BaseResponseResource forgotPassword(ForgotPasswordRequestResource request); 
	
	public BaseResponseResource logout(LogoutRequestResource request);
	
	public GetContactDetailsResponseResource getContactDetails(ContactDetailsRequestResource request);
	
	public MediaUploadResponseResource uploadFlag(MultipartFile multipartFile, String title,Long userId);
	
	public AttachmentResponseResource getFlag(Long uploadId);

}
