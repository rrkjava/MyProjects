/**
 * 
 */
package my.mimos.misos.dissemination.service.impl;


import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.command.handler.DisseminationCommandHandler;
import my.mimos.misos.common.enums.MessageFormat;
import my.mimos.misos.common.enums.StatusType;
import my.mimos.misos.dissemination.FaxConfig;
import my.mimos.misos.dissemination.service.FaxService;
import my.mimos.misos.domain.channel.FaxChannelRequestResource;
import my.mimos.misos.domain.channel.FaxChannelResponseResource;
import my.mimos.misos.domain.integrationservice.DisseminationChannel;
import my.mimos.misos.domain.integrationservice.FaxChannelResource;
import my.mimos.misos.domain.integrationservice.ImageRequestResource;
import my.mimos.misos.domain.integrationservice.ImageResource;
import my.mimos.misos.domain.integrationservice.Poi;
import my.mimos.misos.domain.integrationservice.RecipientRequestResource;
import my.mimos.misos.domain.integrationservice.RecipientResponseResource;
import my.mimos.misos.mapper.ChannelMapper;
import my.mimos.misos.service.IntegrationService;

/**
 * @author krishna.redabotu
 *
 */

@Log4j
@Component
public class FaxServiceImpl implements FaxService {

	@Autowired
	FaxConfig faxConfig;
	
	@Autowired
	ChannelMapper mapper;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private DisseminationCommandHandler disseminationCommand;
	
	@Autowired
	IntegrationService integrationService;
	
	@Override
	public FaxChannelResponseResource sendFax(FaxChannelRequestResource req) throws RuntimeException {

		FaxChannelResponseResource res = new FaxChannelResponseResource();
		MimeMessage message = javaMailSender.createMimeMessage();
		List<String> emails = new ArrayList<String>();
		Long recipientCount = 0L;

		try {

			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			// RESOLVE RECIPIENTS
			emails = resolveRecipients(req);
			recipientCount = Long.valueOf(emails.size());
			log.info(" Recipient count : " + recipientCount);

			if (emails.size() > 0) {

				// PREPARE THE EMAIL RECIPIENTS
				InternetAddress[] address = new InternetAddress[emails.size()];
				for (int i = 0; i < emails.size(); i++) {
					address[i] = new InternetAddress(emails.get(i));
				}

				// EMAIL SETTINGS
				InternetAddress senderAddress = new InternetAddress(faxConfig.getUsername().trim());
				message.setRecipients(Message.RecipientType.BCC, address);
				message.setSender(senderAddress);
				helper.setFrom("misos@mimos.my");
				helper.setSubject(faxConfig.getSubjectMalay());

				// HTML CONTENT IN BODY
				MimeMultipart multipart = new MimeMultipart("related");
				BodyPart messageBodyPart = new MimeBodyPart();
				DataSource dataSource = null;

				if (req.getMessageFormat().equalsIgnoreCase(MessageFormat.MF_TXT.toString())) {

					// HTML CONTENT IN BODY
					messageBodyPart.setContent(formHTMLcontent(req.getFaxMessage()), "text/html");
					multipart.addBodyPart(messageBodyPart);

					// ATTACH INLINE IMAGE IN HTML
					ClassLoader classLoader = this.getClass().getClassLoader();
					dataSource = new FileDataSource(classLoader.getResource("kemen.png").getFile());
					messageBodyPart = new MimeBodyPart();

				} else if (req.getMessageFormat().equalsIgnoreCase(MessageFormat.MF_JPG.toString())) {

					// HTML CONTENT IN BODY
					messageBodyPart.setContent("<img src=\"cid:image\">", "text/html");
					multipart.addBodyPart(messageBodyPart);

					// ATTACH INLINE IMAGE IN HTML
					messageBodyPart = new MimeBodyPart();
					ImageResource image = getImageResource(req);
					String base64String = image.getImageData().replaceFirst("^data:image/[^;]*;base64,?", "");
					byte[] bytearray = Base64.getDecoder().decode(base64String);
					dataSource = new ByteArrayDataSource(bytearray, "image/*");

				}
				
				//EAMIL HEADERS
				messageBodyPart.setDataHandler(new DataHandler(dataSource));
				messageBodyPart.setHeader("Content-ID", "<image>");
				messageBodyPart.addHeader("Content-Transfer-Encoding", "base64");
				messageBodyPart.addHeader("Content-Type", "image/*");
				messageBodyPart.setFileName("image.png");
				messageBodyPart.setDisposition(MimeBodyPart.INLINE);

				// SEND EMAIL
				multipart.addBodyPart(messageBodyPart);
				message.setContent(multipart);
				javaMailSender.send(message);

				res.setStatusType(StatusType.SUCCESS);
				res.setStatusCode("S0010");
				res.setStatus("Dissemination success.");
			} else {
				res.setStatusType(StatusType.SUCCESS);
				res.setStatusCode("S0010");
				res.setStatus("No Recepients for the user group and POI.");
			}
		} catch (MailSendException e) {
			e.printStackTrace();
			res.setStatusType(StatusType.ERROR);
			res.setStatusCode("E0021");
			res.setRecipientCount("0");
			res.setStatus(e.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			res.setStatusType(StatusType.ERROR);
			res.setStatusCode("E0021");
			res.setRecipientCount("0");
			res.setStatus(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatusType(StatusType.ERROR);
			res.setStatusCode("E0021");
			res.setStatus("Unable to send email");
			res.setRecipientCount("0");

		} finally {
			res.setRecipientCount(String.valueOf(recipientCount));
			res.setChannelType(req.getChannelType());
			res.setTargetUserGroups(req.getTargetUserGroups());
			res.setMessageFormat(req.getMessageFormat());
		}

		log.info("-------------------------------------------------");
		log.info(" Email Channel status : " + res.getStatus() + " for total of " + res.getRecipientCount()
				+ " recipients");
		log.info("-------------------------------------------------");
		return res;
	}
	
	public String formHTMLcontent(String message){
		StringBuilder htmlContent = new StringBuilder();
		htmlContent.append("<html dir=\"ltr\"><head>");
		htmlContent.append("<style type=\"text/css\" id=\"owaParaStyle\"></style><style type=\"text/css\" id=\"owaTempEditStyle\"></style></head>");
		htmlContent.append("<body fpstyle=\"1\" ocsi=\"1\">");
		
		htmlContent.append("<table cellpadding=\"8\" cellspacing=\"8\" style=\"table-layout:fixed; text-align: center; font-family:Tahoma,sans-serif; width:600px;border-top: 10px solid #c1c1c1;border-left: 10px solid #c1c1c1;border-right: 10px solid #c1c1c1;\">");
		htmlContent.append("<tbody><tr><td style=\"font-size:17px; font-weight:bold; color:#888\"><p>Jabatan Pengairan Dan Saliran</p><p>Department of irrigation and drainage , Malaysia</p></td>");		
		htmlContent.append("<td style=\"text-align:right; width:100px\"><img src=\"cid:image\" style=\"border:none\"></td>");
		htmlContent.append("</tr></tbody></table>");
		
		htmlContent.append("<table cellpadding=\"8\" cellspacing=\"8\" style=\"table-layout:fixed; text-align:left; font-family:Tahoma,sans-serif; font-size:15px; color:#000; width:600px; border-top: 2px solid #c1c1c1; border-left: 10px solid #c1c1c1; border-right: 10px solid #c1c1c1; border-bottom: 10px solid #c1c1c1 \">");
		htmlContent.append("<tbody><tr><td colspan=\"3\">").append(message).append("</td></tr>");
		htmlContent.append("<tr><td colspan=\"3\">Regards,<br>JPS</td></tr></tbody></table>");
		
		htmlContent.append("<table style=\"table-layout:fixed; font-family:Tahoma,sans-serif; width:660px\">");
		htmlContent.append("<tbody><tr><td style=\"text-align:center; color:#666; font-size:12px\">© 2016-2017 JPS. All rights reserved.</td></tr></tbody>");
		htmlContent.append("</table></body></html>");
		return htmlContent.toString();
	}
	
	
	
	
	/**
	 * Resolve recipients for email dissemination based on target user group and poi
	 * @param req
	 * @return
	 */
	public List<String> resolveRecipients(FaxChannelRequestResource req) {

		List<String> emails = new ArrayList<String>();

		try {
			my.mimos.misos.domain.entity.trx.Message message = disseminationCommand.findByIocMessageId(req.getIocMessageId());

			RecipientRequestResource request = null;
			RecipientResponseResource recipients = null;
			List<FaxChannelResource> faxChannelResource = null;

			disableSslVerification();

			request = new RecipientRequestResource();
			request.setTransactionFrom("Dissemination");
			request.setMethodType("1");
			request.setTargetChannelTypeCode(req.getChannelType());
			request.setUserGroupCode(req.getTargetUserGroups());
			request.setNotificationTypeCode(message.getNotificationTypeId());
			request.setSeverityLevelCode(message.getSeverityLevelId());
			request.setImpactedArea(message.getImpactedArea());
			List<Poi> poiList = new ArrayList<Poi>();
			List<String> dbPoiList = message.getPoi();
			if (!dbPoiList.isEmpty()) {
				for (String str : dbPoiList) {
					Poi poi = new Poi();
					poi.setPoiId(str);
					poiList.add(poi);
				}
			}
			request.setPoiList(poiList);
			recipients = integrationService.getRecipients(request);
			if (recipients.getStatus().getStatusCode().equals("200")) {
				DisseminationChannel disseminationChannel = recipients.getDisseminationChannel();
				faxChannelResource = disseminationChannel.getFaxChannel();
			} else if (recipients.getStatus().getStatusCode().equals("400")) {
				throw new RuntimeException("Error in intergration service for recipients : Request data invalid");
			} else if (recipients.getStatus().getStatusCode().equals("500")) {
				throw new RuntimeException("Error in intergration service for recipients : Internal server error");
			}

			if (!faxChannelResource.isEmpty()) {
				log.info("The integration service returned all users!");
				faxChannelResource.forEach(recipient -> {
					if (validate(recipient.getFaxEmailAddress())) {
						emails.add(recipient.getFaxEmailAddress());
					}
				});
			}
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			throw new RuntimeException(ex.getMessage());
		}

		return emails;
	}
	
	/**
	 * Email Validation
	 */
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}
	
	public ImageResource getImageResource(FaxChannelRequestResource req) {
		ImageResource image = null;
		ImageRequestResource imageRequestResource = null;
		List<String> messageList = new ArrayList<String>(Arrays.asList(req.getFaxMessage().split("<br>")));
		try {
			imageRequestResource = new ImageRequestResource();
			imageRequestResource.setMessageId(req.getIocMessageId());
			imageRequestResource.setTargetChannelTypeCode(req.getChannelType());
			imageRequestResource.setUserGroupCode(req.getTargetUserGroups());
			imageRequestResource.setMessages(messageList);
			disableSslVerification();
			image = integrationService.getImage(imageRequestResource);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return image;
	}
	
	
	public static void disableSslVerification() {
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}

}

	


