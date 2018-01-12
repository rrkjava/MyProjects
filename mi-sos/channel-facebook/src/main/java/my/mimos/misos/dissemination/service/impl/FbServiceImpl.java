/**
 * 
 */
package my.mimos.misos.dissemination.service.impl;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagePostData;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.common.enums.MessageFormat;
import my.mimos.misos.common.enums.StatusType;
import my.mimos.misos.dissemination.config.FbConfig;
import my.mimos.misos.dissemination.service.FbService;
import my.mimos.misos.domain.channel.FbChannelRequestResource;
import my.mimos.misos.domain.channel.FbChannelResponseResource;
import my.mimos.misos.domain.integrationservice.ImageRequestResource;
import my.mimos.misos.domain.integrationservice.ImageResource;
import my.mimos.misos.domain.integrationservice.SocialAccountRequestResource;
import my.mimos.misos.domain.integrationservice.SocialAccountResouce;
import my.mimos.misos.domain.integrationservice.SocialAccountResponseResource;
import my.mimos.misos.service.IntegrationService;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
@Component
@Log4j
public class FbServiceImpl implements FbService {
	
	
	@Autowired
	FbConfig facebookConfig;
	
	@Autowired
	IntegrationService integrationService;
	
	@Override
	public FbChannelResponseResource postStatus(FbChannelRequestResource req) {

		FbChannelResponseResource res = new FbChannelResponseResource();
		SocialAccountRequestResource request = new SocialAccountRequestResource();
		Long recipientCount = 0L;
		try {
			request.setType("facebook");
			request.setDescription("ACCOUNT_TYPE");
			List<SocialAccountResouce> socialAccounts = getSocialAccountFromIntegrationService(request);
			if (!socialAccounts.isEmpty()) {
				recipientCount=Long.valueOf(socialAccounts.size());
				for (SocialAccountResouce account : socialAccounts) {
					PagePostData pagePostData = facebookConfig.pagePostData(account.getFacebookPageId());
					Facebook facebook = facebookConfig.facebbookTemplate(account.getAccessToken());
					
					if (req.getMessageFormat().equalsIgnoreCase(MessageFormat.MF_TXT.toString())) {

						// POST TEXT MESSAGE TO WALL
						String msg = StringUtils.replace(req.getFbMessage(), "<br>", "\n");
						pagePostData.message(StringEscapeUtils.unescapeJava(msg));
						facebook.pageOperations().post(pagePostData);

					} else if (req.getMessageFormat().equalsIgnoreCase(MessageFormat.MF_JPG.toString())) {

						// GET IMAGE TEMPLATE FROM INTEGRATION SERVICE
						ImageResource image = getImageFromIntegrationService(req);
						if (!StringUtils.isEmpty(image.getImageData())) {
							String imageData = image.getImageData().replaceFirst("^data:image/[^;]*;base64,?", "");

							// POST IMAGE TEMPLATE TO WALL
							byte[] bytearray = Base64.getDecoder().decode(imageData);
							Resource photo = new ByteArrayResource(bytearray) {
								@Override
								public String getFilename() {
									return "Amaran-Banjir";
								}
							};

							facebook.pageOperations().postPhoto(account.getFacebookPageId(),
									account.getFacebookPageId(), photo, "");
						} else {
							throw new RuntimeException("No image data from integration service");
						}
					}
				}

			} 

			log.debug("Message posted to FB wall.");
			res.setStatusType(StatusType.SUCCESS);
			res.setStatusCode("S0010");
			res.setStatus("Dissemination success.");
			res.setRecipientCount(String.valueOf(recipientCount));

		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			res.setStatusType(StatusType.ERROR);
			res.setStatusCode("E0021");
			res.setStatus("Unable to post to Facebook Wall," + e.getMessage());
			res.setRecipientCount("0");
		} finally {
			res.setChannelType(req.getChannelType());
			res.setTargetUserGroups(req.getTargetUserGroups());
			res.setMessageFormat(req.getMessageFormat());
		}

		return res;
	}
	
	public ImageResource getImageFromIntegrationService(FbChannelRequestResource req) {
		ImageResource image = null;
		List<String> messageList = new ArrayList<String>(Arrays.asList(req.getFbMessage().split("<br>")));
		ImageRequestResource imageRequestResource = null;
		try {
			imageRequestResource = new ImageRequestResource();
			imageRequestResource.setMessageId(req.getIocMessageId());
			imageRequestResource.setTargetChannelTypeCode(req.getChannelType());
			imageRequestResource.setUserGroupCode(req.getTargetUserGroups());
			imageRequestResource.setMessages(messageList);
			disableSslVerification();
			image=integrationService.getImage(imageRequestResource);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return image;
	}
	
	public List<SocialAccountResouce> getSocialAccountFromIntegrationService(SocialAccountRequestResource req) {
		List<SocialAccountResouce> socialAcountList = new ArrayList<SocialAccountResouce>();
		
		try {
			SocialAccountResponseResource resp=integrationService.getsocialAccount(req);
			if (resp.getStatus().getStatusCode().equals("200")) {
				List<SocialAccountResouce> socialAccounts = resp.getListSocialAccount();
				socialAcountList.addAll(socialAccounts);
			} else {
				throw new RuntimeException(resp.getStatus().getStatusCode() + resp.getStatus().getStatus());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return socialAcountList;
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
