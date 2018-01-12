/**
 * 
 */
package my.mimos.misos.dissemination.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.command.handler.DisseminationCommandHandler;
import my.mimos.misos.common.enums.StatusType;
import my.mimos.misos.dissemination.domain.SirenConfig;
import my.mimos.misos.dissemination.service.SirenService;
import my.mimos.misos.domain.channel.SirenChannelRequestResource;
import my.mimos.misos.domain.channel.SirenChannelResponseResource;
import my.mimos.misos.domain.channel.sms.DapatBulkSMS;
import my.mimos.misos.domain.channel.sms.DapatSms;
import my.mimos.misos.domain.channel.sms.DestAddr;
import my.mimos.misos.domain.channel.sms.MessageType;
import my.mimos.misos.domain.entity.trx.Message;
import my.mimos.misos.domain.integrationservice.DisseminationChannel;
import my.mimos.misos.domain.integrationservice.Poi;
import my.mimos.misos.domain.integrationservice.RecipientRequestResource;
import my.mimos.misos.domain.integrationservice.RecipientResponseResource;
import my.mimos.misos.domain.integrationservice.SirenChannelResource;
import my.mimos.misos.service.IntegrationService;

/**
 * @author krishna.redabotu
 *
 */
@Log4j
@Component
public class SirenServiceImpl implements SirenService {
	
	@Autowired
	private SirenConfig sirenConfig;
	
	public final static int MESSAGE_LENGTH = 142;
	
	@Autowired
	private DisseminationCommandHandler disseminationCommand;
	
	@Autowired
	IntegrationService integrationService;
	
	@Override
	public SirenChannelResponseResource sendSiren(SirenChannelRequestResource req) {

		SirenChannelResponseResource res = new SirenChannelResponseResource();
		Long recipientCount = 0L;
		try {
			
			String message = StringUtils.replace(req.getSirenMessage(), "<br>", "\n");// line breaks to newline
			List<String> messageBatches = new ArrayList<>();
			
			// validate the message length. split into batches
			if (null != message && message.length() > MESSAGE_LENGTH) {
				//throw new IllegalStateException("Unable to post message to SMS provider : Message too long.");							
				while(message.length() > MESSAGE_LENGTH) {					   
						messageBatches.add(message.substring(0, MESSAGE_LENGTH));
					    message = message.substring(MESSAGE_LENGTH);
				}				
			}	
			if(StringUtils.isNotBlank(message)){
				messageBatches.add(message);
			}

			// Get the recipients for sms dissemination
			List<DestAddr> listAddr = resolveRecipients(req);
			recipientCount = Long.valueOf(listAddr.size());
			log.info(" recipient count : " + recipientCount);		
			

			if (listAddr.size() > 0) {

				// Read sms connector properties
				final String keyword = sirenConfig.getKeyword();
				final String userName = sirenConfig.getUsername();
				final String password = sirenConfig.getPassword();

				XStream xstream = new XStream();
				// Tell XStream to process annotation
				xstream.processAnnotations(DapatBulkSMS.class);
				xstream.processAnnotations(DapatSms.class);
				xstream.processAnnotations(DestAddr.class);
				xstream.processAnnotations(MessageType.class);

				// Construct the SMS object
				DapatBulkSMS bulkSms = new DapatBulkSMS();
				bulkSms.setAction("BULK");
				bulkSms.setUserName(userName);
				bulkSms.setPassword(password);
			
				//send messages in batches
				if(messageBatches.size()>0){
					
					for(String textMessage : messageBatches){
						// Set the message to be sent
						MessageType mesageType = new MessageType();
						mesageType.setType("Text");			
						mesageType.setMessage(StringEscapeUtils.unescapeJava(textMessage));	
								
						// Create SMS
						DapatSms sms = new DapatSms();
						sms.setKeyword(keyword);				
						sms.setDestAddr(listAddr);
						sms.setMessage(mesageType);
						bulkSms.setSms(sms);	

						// Convert the SMS to XML
						String dataXml = xstream.toXML(bulkSms);
						log.debug("Bulk XML : " + dataXml);
						System.out.println("Bulk XML : " + dataXml);
		
						// Send the XML to DAPAT SMS provider
						CloseableHttpClient client = HttpClients.createDefault();
						HttpPost httpPost = new HttpPost("http://mtsms.15888.my/Receiver_xml.aspx");
		
						StringEntity entity = new StringEntity(dataXml);
						httpPost.setHeader("Content-type", "application/xml");
						httpPost.setEntity(entity);
		
						CloseableHttpResponse response = client.execute(httpPost);
						log.debug("IOC_MESSAGE_ID : " + req.getIocMessageId());
						log.debug("HTTP_STATUS_CODE : " + response.getStatusLine().getStatusCode());
		
						InputStreamReader streamReader = new InputStreamReader(response.getEntity().getContent());
						BufferedReader reader = new BufferedReader(streamReader);
		
						StringBuffer responseCode = new StringBuffer();
						String tempStr;
						while ((tempStr = reader.readLine()) != null) {
							responseCode.append(tempStr);
						}
		
						log.debug("RESPONSE_CODE : " + responseCode);
		
						if ("0001".equals(responseCode.toString())) {
							throw new IllegalStateException("Unable to post message to SMS provider : Authentication Failed.");
						} else if ("0002".equals(responseCode.toString())) {
							throw new IllegalStateException("Unable to post message to SMS provider : Invalid Shortcode.");
						} else if ("0003".equals(responseCode.toString())) {
							throw new IllegalStateException("Unable to post message to SMS provider : Keyword Not Allowed.");
						} else if ("0004".equals(responseCode.toString())) {
							throw new IllegalStateException("Unable to post message to SMS provider : Malformed Destination.");
						} else if ("0005".equals(responseCode.toString())) {
							throw new IllegalStateException("Unable to post message to SMS provider : Malformed SMS lenght.");
						} else if ("0006".equals(responseCode.toString())) {
							throw new IllegalStateException("Unable to post message to SMS provider : Malformed Telco.");
						} else if ("0007".equals(responseCode.toString())) {
							throw new IllegalStateException("Unable to post message to SMS provider : Invalid Charge Field.");
						} else if ("0008".equals(responseCode.toString())) {
							throw new IllegalStateException("Unable to post message to SMS provider : No Record Found.");
						} else if ("0009".equals(responseCode.toString())) {
							throw new IllegalStateException("Unable to post message to SMS provider : Blacklist No.");
						} else if ("0011".equals(responseCode.toString())) {
							throw new IllegalStateException("Unable to post message to SMS provider : Insufficient credit.");
						} else if ("0012".equals(responseCode.toString())) {
							throw new IllegalStateException("Unable to post message to SMS provider : Block Account.");
						} else if ("500".equals(responseCode.toString())) {
							throw new IllegalStateException("Unable to post message to SMS provider : Unsuccessful.");
						}
						client.close();

					}
				}
				res.setStatusType(StatusType.SUCCESS);
				res.setStatusCode("S0010");
				res.setStatus("Siren Dissemination success.");

			} else {
				res.setStatusType(StatusType.SUCCESS);
				res.setStatusCode("S0010");
				res.setStatus("No Recepients for the user group and POI.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			res.setStatusType(StatusType.ERROR);
			res.setStatusCode("E0021");
			res.setStatus(e.getMessage());

		} finally {
			res.setRecipientCount(String.valueOf(recipientCount));
			res.setChannelType(req.getChannelType());
			res.setTargetUserGroups(req.getTargetUserGroups());
		}
		return res;
	}
	
	
	@Override
	public List<DestAddr> resolveRecipients(SirenChannelRequestResource req) {

		List<DestAddr> listAddr = new ArrayList<DestAddr>(0);

		try {
			Message message = disseminationCommand.findByIocMessageId(req.getIocMessageId());

			// 1. call integration service for user list
			RecipientRequestResource request = null;
			RecipientResponseResource recipients = null;
			List<SirenChannelResource> sirenChannelResource = null;

			disableSslVerification();

			request = new RecipientRequestResource();
			request.setTransactionFrom("mobile");
			request.setMethodType("1");
			request.setTargetChannelTypeCode(req.getChannelType());
			request.setUserGroupCode(req.getTargetUserGroups());
			request.setNotificationTypeCode(message.getNotificationTypeId());
			request.setSeverityLevelCode(message.getSeverityLevelId());
			request.setImpactedArea(message.getImpactedArea());
			List<Poi> poiList = new ArrayList<Poi>();
			List<String> dbPoiList = message.getPoi();
			if ( null !=dbPoiList && !dbPoiList.isEmpty()) {
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
				sirenChannelResource = disseminationChannel.getSirenChannel();
			} else if (recipients.getStatus().getStatusCode().equals("400")) {
				throw new RuntimeException("Error in intergration service for recipients : Request data invalid");
			} else if (recipients.getStatus().getStatusCode().equals("500")) {
				throw new RuntimeException("Error in intergration service for recipients : Internal server error");
			}

			if (!sirenChannelResource.isEmpty()) {
				log.info("The integration service returned all users!");
				sirenChannelResource.forEach(recipient -> {
					listAddr.add(new DestAddr(recipient.getSirenMobileNo()));
				});
			}

		} catch (Exception ex) {
			throw new IllegalStateException(ex.getMessage());
		}

		return listAddr;
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
