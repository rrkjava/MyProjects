package my.mimos.mdc.utils;

public final class MdcConstants {
	
	public static final String STATUS_ACTIVE = "ACTIVE";
	public static final String STATUS_INACTIVE = "INACTIVE";
	public static final String STATUS_BLOCKED = "BLOCKED";
	
	public static final String STATUS_PENDING = "PENDING";	
	public static final String STATUS_APPROVE = "APPROVED";
	public static final String STATUS_ACCEPT = "ACCEPTED";
	public static final String STATUS_REJECT = "REJECTED";
	
	public static final String STATUS_PENDING_AT_SUPERVISOR = "PENDING_AT_SUPERVISOR";
	public static final String STATUS_PENDING_AT_ADMIN = "PENDING_AT_ADMIN";
	public static final String STATUS_PENDING_AT_KLN = "PENDING_AT_KLN";
	
	public static final String RECIPIENT_TYPE_USER = "USER";
	public static final String RECIPIENT_TYPE_AGENCY = "AGENCY";
	public static final String RECIPIENT_TYPE_GROUP = "GROUP";
	
	public static final String QUERY_URGENCY_NORMAL = "NORMAL";
	public static final String QUERY_URGENCY_URGENT = "URGENT";
	
	public static final String LOGIN_SUCCESS = "SUCCESS";
	public static final String LOGIN_FAILED = "FAILED";
	public static final int LOGIN_FAIL_LIMIT = 3;
	
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_EMBASSY = "ROLE_EMBASSY";
	public static final String ROLE_FOCAL = "ROLE_FOCAL";
	public static final String ROLE_FOCAL_SUPERVISOR = "ROLE_FOCAL_SUPERVISOR";
	public static final String ROLE_KLN = "ROLE_KLN";
	
	public static final String DEVICE_TYPE_ANDROID = "ANDROID";
	public static final String DEVICE_TYPE_IOS = "IOS";
	
	public static final String NOTIFY_TYPE_QUERY = "QUERY";
	public static final String NOTIFY_TYPE_BROADCAST = "BROADCAST";
	
	public static final String NOTIFY_NEW_BROADCAST = "You have received a new broadcast";
	public static final String NOTIFY_BROADCAST_REPLY= "You have received a reply to a broadcast";
	
	public static final String NOTIFY_NEW_QUERY = "You have received a new query";
	public static final String NOTIFY_ACCEPT_QUERY = "Your query has been accepted";
	public static final String NOTIFY_REJECT_QUERY = "Your query has been rejected";
	public static final String NOTIFY_EDIT_QUERY= "Query has been updated";
	
	public static final String NOTIFY_ACK_QUERY ="You have received an acknowledgment for a query";
	
	public static final String NOTIFY_REPLY= "You have received a new reply to a query";	
	public static final String NOTIFY_FINAL_REPLY= " The final reply has been sent to FDM";	
	public static final String NOTIFY_APPROVE_RESPONSE="Your reply has been accepted";	
	public static final String NOTIFY_REJECT_RESPONSE="Your reply has been rejected";
	
	public static final String NOTIFY_REMINDER= "Reminder - Please respond to the pending query(s) in your inbox.";
	
	public static final String NOTIFY_REMINDER_ACK= "Reminder - Awaiting your acknowledge for the query";
	public static final String NOTIFY_REMINDER_REPLY= "Reminder - Awaiting your reply for the query";
	public static final String NOTIFY_REMINDER_APPROVAL= "Reminder - Awaiting your approval for the response of the query";
	
	public static final String ACTION_FOCAL_ACK_QUERY= "ACTION_FOCAL_ACK_QUERY";
	public static final String ACTION_ADMIN_ACK_QUERY= "ACTION_ADMIN_ACK_QUERY";
	
	public static final String ACTION_ADMIN_REPLY= "ACTION_ADMIN_REPLY";
	public static final String ACTION_FOCAL_REPLY= "ACTION_FOCAL_REPLY";
	
	public static final String ACTION_FOCAL_SUP_APPROVE= "ACTION_FOCAL_SUP_APPROVE";
	public static final String ACTION_ADMIN_APPROVE= "ACTION_ADMIN_APPROVE";
	
	public static final String ACTION_KLN_CLARIFY= "ACTION_KLN_CLARIFY";
	
	
	public static final String ADMINACTION = "ADMINACTION";
	
}
