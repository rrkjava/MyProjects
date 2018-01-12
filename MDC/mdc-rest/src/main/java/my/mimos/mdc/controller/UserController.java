package my.mimos.mdc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

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
import my.mimos.mdc.service.UserManagementService;

@RestController
@RequestMapping(value="/")
public class UserController {
	
	@Autowired
	UserManagementService userService;		
	
	@RequestMapping(value="admin/user/registration",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseBody
	public ResponseEntity<UserRegistrationResponseResource> registerUser(@Valid @RequestBody UserResource user){	
		UserRegistrationResponseResource response=userService.registerUser(user);;
		return new ResponseEntity<UserRegistrationResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="admin/user/approval",			
	        produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseBody
	public ResponseEntity<UserActivationResponseResource> approveUser(@Valid @RequestBody UserActivationRequestResource request){	
		UserActivationResponseResource response = userService.approveUser(request);
		return new ResponseEntity<UserActivationResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/profile/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserProfileResponseResource> userProfile(@PathVariable("id") String userId) {
		UserProfileResponseResource response = userService.getUserProfile(userId);
		return new ResponseEntity<UserProfileResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/user/search", method = RequestMethod.POST)
	public ResponseEntity<SearchUsersResponseResource> searchUsers(@RequestBody SearchUsersRequestResource request){
		SearchUsersResponseResource response = userService.searchUsers(request);
		return new ResponseEntity<SearchUsersResponseResource>(response,         HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/user/list", method = RequestMethod.GET)
	public ResponseEntity<SearchUsersResponseResource> listAllUsers(){
		SearchUsersResponseResource response = userService.listAllUsers();
		return new ResponseEntity<SearchUsersResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "user/changepassword", method = RequestMethod.POST)
	public ResponseEntity<BaseResponseResource> changePassword(@Valid @RequestBody ChangePasswordRequestResource request){
		BaseResponseResource response = userService.changePassword(request);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "user/updateprofile", method = RequestMethod.POST)
	public ResponseEntity<BaseResponseResource> updateUserProfile(@Valid @RequestBody UpdateProfileRequestResource request){
		BaseResponseResource response = userService.updateUserProfile(request);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);		
	}

	@RequestMapping(value = "admin/role/add", method = RequestMethod.POST)
	public ResponseEntity<BaseResponseResource> addRole(@Valid @RequestBody AddRoleRequestResource request){
		BaseResponseResource response = userService.addRole(request);
		return new ResponseEntity<BaseResponseResource>(response,      HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/role/{id}", method = RequestMethod.POST)
	public ResponseEntity<GetRoleResponseResource> getRole(@PathVariable("id") Long roleId){
		GetRoleResponseResource response = userService.getRole(roleId);
		return new ResponseEntity<GetRoleResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/role/list", method = RequestMethod.GET)
	public ResponseEntity<GetRolesResponseResource> listAllRoles(){
		GetRolesResponseResource response = userService.listAllRoles();
		return new ResponseEntity<GetRolesResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/role/update", method = RequestMethod.POST)
	public ResponseEntity<BaseResponseResource> updateRole(@Valid @RequestBody UpdateRoleRequestResource request){
		BaseResponseResource response = userService.updateRole(request);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/role/delete/{id}", method = RequestMethod.POST)
	public ResponseEntity<BaseResponseResource> removeRole(@PathVariable("id") Long roleId){
		BaseResponseResource response = userService.removeRole(roleId);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/user/assignrole", method = RequestMethod.POST)
	public ResponseEntity<BaseResponseResource> assignRole(@Valid @RequestBody AssignRoleRequestResource request){
		BaseResponseResource response = userService.assignRole(request);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/user/deassignrole", method = RequestMethod.POST)
	public ResponseEntity<BaseResponseResource> deassignRole(@Valid @RequestBody AssignRoleRequestResource request){
		BaseResponseResource response = userService.deassignRole(request);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/department/add", method = RequestMethod.POST)
	public ResponseEntity<BaseResponseResource> addDepartment(@Valid @RequestBody AddDeptRequestResource request){
		BaseResponseResource response = userService.addDepartment(request);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/department/{id}", method = RequestMethod.GET)
	public ResponseEntity<GetDeptResponseResource> getDepartment(@PathVariable("id") Long deptId){
		GetDeptResponseResource response = userService.getDepartment(deptId);
		return new ResponseEntity<GetDeptResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/department/list", method = RequestMethod.GET)
	public ResponseEntity<GetDeptsResponseResource> listAllDepartments(){
		GetDeptsResponseResource response = userService.listAllDepartments();
		return new ResponseEntity<GetDeptsResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/department/update", method = RequestMethod.POST)
	public ResponseEntity<BaseResponseResource> updateDepartment(@Valid @RequestBody UpdateDeptRequestResource request){
		BaseResponseResource response = userService.updateDepartment(request);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/department/delete/{id}", method = RequestMethod.GET)
	public ResponseEntity<BaseResponseResource> removeDepartment(@PathVariable("id") Long deptId){
		BaseResponseResource response = userService.removeDepartment(deptId);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/group/add", method = RequestMethod.POST)
	public ResponseEntity<BaseResponseResource> addGroup(@Valid @RequestBody AddGroupRequestResource request){
		BaseResponseResource response = userService.addGroup(request);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/group/update", method = RequestMethod.POST)
	public ResponseEntity<BaseResponseResource> updateGroup(@Valid @RequestBody UpdateGroupRequestResource request){
		BaseResponseResource response = userService.updateGroup(request);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/group/list", method = RequestMethod.GET)
	public ResponseEntity<GetUserGroupResponseResource> listGroup(){
		GetUserGroupResponseResource response = userService.listAllGroups();
		return new ResponseEntity<GetUserGroupResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/group/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity<BaseResponseResource> removeGroup(@PathVariable("id") Long groupId){
		BaseResponseResource response = userService.removeGroup(groupId);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/group/adduser", method = RequestMethod.POST)
	public ResponseEntity<AddGroupUserResponseResource> addGroupUser(@Valid @RequestBody AddGroupUserRequestResource request){
		AddGroupUserResponseResource response = userService.addGroupUser(request);
		return new ResponseEntity<AddGroupUserResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/group/removeuser", method = RequestMethod.POST)
	public ResponseEntity<BaseResponseResource> removeGroupUser(@Valid @RequestBody AddGroupUserRequestResource request){
		BaseResponseResource response = userService.removeGroupUser(request);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "admin/group/{id}", method = RequestMethod.GET)
	public ResponseEntity<GetGroupProfileResponseResource> removeGroupUser(@PathVariable("id") Long groupId){
		GetGroupProfileResponseResource response = userService.groupProfile(groupId);
		return new ResponseEntity<GetGroupProfileResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "user/logout", method = RequestMethod.POST)
	public ResponseEntity<BaseResponseResource> logout(@Valid @RequestBody LogoutRequestResource request){
		BaseResponseResource response = userService.logout(request);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "user/forgotpassword", method = RequestMethod.POST)
	public ResponseEntity<BaseResponseResource> forgotPassword(@Valid @RequestBody ForgotPasswordRequestResource request){
		BaseResponseResource response = userService.forgotPassword(request);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);		
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/admin/flag/upload",
			produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.POST})
	public ResponseEntity<MediaUploadResponseResource> uploadFlag(
			@RequestParam("file") MultipartFile multipartFile,
			@RequestParam("title") String title,
			@RequestParam( value="userId",required=true) Long userId) throws MultipartException {
		MediaUploadResponseResource response = userService.uploadFlag(multipartFile, title,userId);
		return new ResponseEntity<MediaUploadResponseResource>(response, HttpStatus.OK);
	} 
	
	@RequestMapping(value = "user/flag/{id}",
			produces = MediaType.APPLICATION_JSON_VALUE, 
	        method = {RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<AttachmentResponseResource> getAttachment(@Valid @PathVariable("id") Long uploadId) {
		AttachmentResponseResource response = userService.getFlag(uploadId);
		return new ResponseEntity<AttachmentResponseResource>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "user/contacts", method = RequestMethod.POST)
	public ResponseEntity<GetContactDetailsResponseResource> getContacts(
			@Valid @RequestBody ContactDetailsRequestResource request) {
		GetContactDetailsResponseResource response = userService.getContactDetails(request);
		return new ResponseEntity<GetContactDetailsResponseResource>(response, HttpStatus.OK);
	}
}