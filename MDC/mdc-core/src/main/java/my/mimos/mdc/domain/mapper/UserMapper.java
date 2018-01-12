package my.mimos.mdc.domain.mapper;

import org.springframework.stereotype.Component;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import my.mimos.mdc.domain.entity.Attachment;
import my.mimos.mdc.domain.entity.Group;
import my.mimos.mdc.domain.entity.Role;
import my.mimos.mdc.domain.entity.User;
import my.mimos.mdc.resources.broadcast.BroadCastByResource;
import my.mimos.mdc.resources.query.AttachmentResource;
import my.mimos.mdc.resources.query.DisplayUserResource;
import my.mimos.mdc.resources.user.ConcatDetailsResource;
import my.mimos.mdc.resources.user.GetGroupProfileResponseResource;
import my.mimos.mdc.resources.user.GroupResource;
import my.mimos.mdc.resources.user.RoleResource;
import my.mimos.mdc.resources.user.UserResource;
import my.mimos.mdc.security.CustomUserDetails;

@Component
public class UserMapper extends ConfigurableMapper {	
	
	MapperFacade mapperFacade;
	
	protected void configure(MapperFactory factory) {
		factory.classMap(UserResource.class, User.class)
		
		    .field("firstName", "firstName")
		    .field("lastName", "lastName")
		    .field("username", "username")		    
		    .field("emailId", "emailId")
		    .field("phone", "phone")
		    .field("address", "address")
		    .field("approvalStatus", "approvalStatus")
		    .field("activatedStatus", "activatedStatus")		    
		    .field("department", "department")
		    .field("department.deptType","department.departmentType")
		    .field("role", "role")	
		    .field("uploadedFlag","upload.uploadId")
		    .byDefault()
		    .register(); 
		
		factory.classMap(UserResource.class, CustomUserDetails.class)
		
		    .field("userId", "userId")
		    .field("firstName", "firstName")
		    .field("lastName", "lastName")
		    .field("username", "username")
		    //.field("password", "password")		    
		    .field("emailId", "emailId")			    
		    .field("approvalStatus", "approvalStatus")
		    .field("activatedStatus", "activatedStatus")		    
		    .field("department", "department")
		    .field("department.deptType","department.departmentType")
		    .field("role", "role")		  
		    .byDefault()
		    .register(); 
		
		factory.classMap(User.class, CustomUserDetails.class)		
		    .field("userId", "userId")
		    .field("firstName", "firstName")
		    .field("lastName", "lastName")
		    .field("username", "username")
		    .field("password", "password")		    
		    .field("emailId", "emailId")			    
		    .field("approvalStatus", "approvalStatus")
		    .field("activatedStatus", "activatedStatus")		    
		    .field("department", "department")
		    .field("role", "role")		  
		    .byDefault()
		    .register(); 
		
		factory.classMap(DisplayUserResource.class, User.class)		
		    .field("firstName", "firstName")
		    .field("username", "username")	    
		    .field("userId", "userId")	
		    .field("department","department.deptName")
		    .field("role","role")
		    .byDefault()
		    .register(); 
		
		factory.classMap(RoleResource.class, Role.class)			
		    .field("roleId",  "roleId")
		    .field("roleName", "roleName")
		    .field("roleDesc", "roleDesc")
		    .byDefault()
		    .register(); 
		
		factory.classMap(Group.class,GroupResource.class)
			.field("department.deptId","deptId")
			.byDefault()
			.register();
		
		factory.classMap(Group.class,GetGroupProfileResponseResource.class)
			.byDefault()
			.register();
		
		factory.classMap(BroadCastByResource.class, User.class)		
	    .field("firstName", "firstName")
	    .field("username", "username")	    
	    .field("userId", "userId")	
	    .field("department","department.deptName")
	    .field("role","role")
	    .byDefault()
	    .register(); 
		
		factory.classMap(ConcatDetailsResource.class, User.class)	
	    .customize(new CustomMapper<ConcatDetailsResource,User>() {
	    	@Override
            public void mapBtoA(User b,ConcatDetailsResource a, MappingContext mappingContext) {
	    		a.setFullName(b.getFirstName()+" "+b.getLastName());
	    	}
	    })
	    .field("phone", "phone")	    
	    .field("emailId", "emailId")	
	    .field("address","address")
	    .field("emabssyName","username")
	    .field("uploadedFlag","upload.uploadId")
	    .byDefault()
	    .register(); 
		
		factory.classMap(AttachmentResource.class, Attachment.class)		
	    .field("uploadId", "uploadId")
	    .field("uploadTitle", "uploadTitle")
	    .field("content", "content")
	    .byDefault()
	    .register(); 
		
		mapperFacade = factory.getMapperFacade();
	}
	
	public MapperFacade getMapperFacade(){
		return mapperFacade;
	}
	
	
}
