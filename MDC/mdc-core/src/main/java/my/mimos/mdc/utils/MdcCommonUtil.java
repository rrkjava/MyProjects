package my.mimos.mdc.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class MdcCommonUtil {

	public String generateTemporaryPassword(){
		String password = null;
		try{
			password = UUID.randomUUID().toString();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return password;		
	}
	
	public String userApprovalEmailContent(String username,String password, String appDownloadURL){
		StringBuilder content = new StringBuilder();
		try{
			content.append("<html dir=\"ltr\"><head>");
			content.append("<style type=\"text/css\" id=\"owaParaStyle\"></style><style type=\"text/css\" id=\"owaTempEditStyle\"></style></head>");
			content.append("<body fpstyle=\"1\" ocsi=\"1\">");
			
			content.append("<table cellpadding=\"8\" cellspacing=\"8\" style=\"table-layout:fixed; text-align: center; font-family:Tahoma,sans-serif; width:600px;border-top: 10px solid #c1c1c1;border-left: 10px solid #c1c1c1;border-right: 10px solid #c1c1c1;\">");
			content.append("<tbody><tr><td style=\"font-size:17px; font-weight:bold; color:#888\"><p>Moha Diplomacy Channel</p><p>Ministry of Home Affairs , Malaysia</p></td>");		
			content.append("<td style=\"text-align:right; width:100px\"><img src=\"cid:image\" style=\"border:none\"></td>");
			content.append("</tr></tbody></table>");
			
			content.append("<table cellpadding=\"8\" cellspacing=\"8\" style=\"table-layout:fixed; text-align:left; font-family:Tahoma,sans-serif; font-size:15px; color:#000; width:600px; border-top: 2px solid #c1c1c1; border-left: 10px solid #c1c1c1; border-right: 10px solid #c1c1c1; border-bottom: 10px solid #c1c1c1 \">");
			content.append("<tbody>");
			content.append("<tr><td colspan=\"3\">").append("Your request for user registration has been approved. Download the mobile application from the below URL and login with the username and temporary password.").append("</td></tr>");
			content.append("<tr><td colspan=\"3\">").append("username : " + username).append("</td></tr>");
			content.append("<tr><td colspan=\"3\">").append("password : " + password).append("</td></tr>");
			content.append("<tr><td colspan=\"3\">").append("Mobile App Download : " + appDownloadURL).append("</td></tr>");
			content.append("<tr><td colspan=\"3\">Regards,<br>MDC Admin</td></tr></tbody></table>");
			
			content.append("<table style=\"table-layout:fixed; font-family:Tahoma,sans-serif; width:660px\">");
			content.append("<tbody><tr><td style=\"text-align:center; color:#666; font-size:12px\">© 2017 MDC. All rights reserved.</td></tr></tbody>");
			content.append("</table></body></html>");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return content.toString();
	}
	
	public String userRejectedEmailContent(String comment){
		StringBuilder content = new StringBuilder();
		try{
			content.append("<html dir=\"ltr\"><head>");
			content.append("<style type=\"text/css\" id=\"owaParaStyle\"></style><style type=\"text/css\" id=\"owaTempEditStyle\"></style></head>");
			content.append("<body fpstyle=\"1\" ocsi=\"1\">");
			
			content.append("<table cellpadding=\"8\" cellspacing=\"8\" style=\"table-layout:fixed; text-align: center; font-family:Tahoma,sans-serif; width:600px;border-top: 10px solid #c1c1c1;border-left: 10px solid #c1c1c1;border-right: 10px solid #c1c1c1;\">");
			content.append("<tbody><tr><td style=\"font-size:17px; font-weight:bold; color:#888\"><p>Moha Diplomacy Channel</p><p>Ministry of Home Affairs , Malaysia</p></td>");		
			content.append("<td style=\"text-align:right; width:100px\"><img src=\"cid:image\" style=\"border:none\"></td>");
			content.append("</tr></tbody></table>");
			
			content.append("<table cellpadding=\"8\" cellspacing=\"8\" style=\"table-layout:fixed; text-align:left; font-family:Tahoma,sans-serif; font-size:15px; color:#000; width:600px; border-top: 2px solid #c1c1c1; border-left: 10px solid #c1c1c1; border-right: 10px solid #c1c1c1; border-bottom: 10px solid #c1c1c1 \">");
			content.append("<tbody>");
			content.append("<tr><td colspan=\"3\">").append("Your request for user registration has been rejected.").append("</td></tr>");
			content.append("<tr><td colspan=\"3\">").append("Reason : "+ comment).append("</td></tr>");
			
			content.append("<table style=\"table-layout:fixed; font-family:Tahoma,sans-serif; width:660px\">");
			content.append("<tbody><tr><td style=\"text-align:center; color:#666; font-size:12px\">© 2017 MDC. All rights reserved.</td></tr></tbody>");
			content.append("</table></body></html>");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return content.toString();
		
	}
	
	
	
	public String resetPasswordEmailContent(String password){
		StringBuilder content = new StringBuilder();
		try{
			content.append("<html dir=\"ltr\"><head>");
			content.append("<style type=\"text/css\" id=\"owaParaStyle\"></style><style type=\"text/css\" id=\"owaTempEditStyle\"></style></head>");
			content.append("<body fpstyle=\"1\" ocsi=\"1\">");
			
			content.append("<table cellpadding=\"8\" cellspacing=\"8\" style=\"table-layout:fixed; text-align: center; font-family:Tahoma,sans-serif; width:600px;border-top: 10px solid #c1c1c1;border-left: 10px solid #c1c1c1;border-right: 10px solid #c1c1c1;\">");
			content.append("<tbody><tr><td style=\"font-size:17px; font-weight:bold; color:#888\"><p>Moha Diplomacy Channel</p><p>Ministry of Home Affairs , Malaysia</p></td>");		
			content.append("<td style=\"text-align:right; width:100px\"><img src=\"cid:image\" style=\"border:none\"></td>");
			content.append("</tr></tbody></table>");
			
			content.append("<table cellpadding=\"8\" cellspacing=\"8\" style=\"table-layout:fixed; text-align:left; font-family:Tahoma,sans-serif; font-size:15px; color:#000; width:600px; border-top: 2px solid #c1c1c1; border-left: 10px solid #c1c1c1; border-right: 10px solid #c1c1c1; border-bottom: 10px solid #c1c1c1 \">");
			content.append("<tbody>");
			content.append("<tr><td colspan=\"3\">").append("Your new password.").append("</td></tr>");
			content.append("<tr><td colspan=\"3\">").append("password : " + password).append("</td></tr>");
			content.append("<tr><td colspan=\"3\">Regards,<br>MDC Admin</td></tr></tbody></table>");
			
			content.append("<table style=\"table-layout:fixed; font-family:Tahoma,sans-serif; width:660px\">");
			content.append("<tbody><tr><td style=\"text-align:center; color:#666; font-size:12px\">© 2017 MDC. All rights reserved.</td></tr></tbody>");
			content.append("</table></body></html>");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return content.toString();
	}
}
