/**
 * 
 */
package my.mimos.misos.dissemination.service.impl;

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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.command.handler.DisseminationCommandHandler;
import my.mimos.misos.common.enums.StatusType;
import my.mimos.misos.dissemination.config.MobileGatewayConfig;
import my.mimos.misos.dissemination.service.MobileService;
import my.mimos.misos.domain.channel.MobileChannelRequestResource;
import my.mimos.misos.domain.channel.MobileChannelResponseResource;
import my.mimos.misos.domain.channel.MobileGatewayDeviceResource;
import my.mimos.misos.domain.channel.MobileGatewayRequestResource;
import my.mimos.misos.domain.entity.trx.Message;
import my.mimos.misos.domain.integrationservice.DisseminationChannel;
import my.mimos.misos.domain.integrationservice.MobileChannelResource;
import my.mimos.misos.domain.integrationservice.Poi;
import my.mimos.misos.domain.integrationservice.RecipientRequestResource;
import my.mimos.misos.domain.integrationservice.RecipientResponseResource;
import my.mimos.misos.mapper.ChannelMapper;
import my.mimos.misos.service.IntegrationService;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
@Log4j
@Component
public class MobileServiceImpl implements MobileService {

	@Autowired
	MobileGatewayConfig config;
	
	@Autowired
	ChannelMapper mapper;
	
	@Autowired
	IntegrationService integrationService;
	
	@Autowired
	private DisseminationCommandHandler disseminationCommand;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * my.mimos.misos.dissemination.service.MobileService#pushNotification(my.
	 * mimos.misos.domain.channel.MobileChannelRequestResource)
	 * 
	 * 1. Send the message to Mobile Gateway service. 2. Mobile Gateway will
	 * send the push notification to mobile device
	 * 
	 */
	@Override
	public MobileChannelResponseResource pushNotification(MobileChannelRequestResource req) {

		MobileChannelResponseResource res = new MobileChannelResponseResource();
		Long recipientCount = 0L;
		try {
			RestTemplate rest = new RestTemplate();
			String url = config.getUrl() + config.getService();
			MobileGatewayRequestResource request = mapper.getMapperFacade().map(req,MobileGatewayRequestResource.class);
			List<MobileGatewayDeviceResource> deviceIdList = resolveRecipients(req);
			recipientCount = Long.valueOf(deviceIdList.size());
			log.info(" Recipient count : " + recipientCount);
			if (!deviceIdList.isEmpty()) {
				request.setDeviceList(deviceIdList);
				res = rest.postForObject(url, request, MobileChannelResponseResource.class);
				if(res.getStatus()==null){
					throw new RuntimeException("Mobile Gateway service error");
				}
			} else {
				res.setStatusCode("S0010");
				res.setStatusType(StatusType.SUCCESS);
				res.setStatus("Dissemination success no recipients.");
			}
		} catch (RuntimeException ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			res.setStatusCode("E0021");
			res.setStatusType(StatusType.ERROR);
			res.setStatus("Mobile Gateway service error");
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			res.setStatusCode("E0021");
			res.setStatusType(StatusType.ERROR);
			res.setStatus("Mobile Gateway service unreachable.");
		} finally {
			res.setChannelType("CH_04");
			res.setRecipientCount(String.valueOf(recipientCount));
			res.setTargetUserGroups(req.getTargetUserGroups());
			res.setMessageFormat(req.getMessageFormat());
			
		}
		return res;
	}
	
	
	/**
	 * Resolve recipients for email dissemination based on target user group and poi
	 * @param req
	 * @return
	 */
	public List<MobileGatewayDeviceResource> resolveRecipients(MobileChannelRequestResource req) {

		List<MobileGatewayDeviceResource> deviceList = new ArrayList<MobileGatewayDeviceResource>();

		try {
			Message message = disseminationCommand.findByIocMessageId(req.getIocMessageId());

			RecipientRequestResource request = null;
			RecipientResponseResource recipients = null;
			List<MobileChannelResource> mobileChannelResource = null;

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
			if (!dbPoiList.isEmpty()) {
				for (String str : dbPoiList) {
					Poi poi = new Poi();
					poi.setPoiId(str);
					poiList.add(poi);
				}
			}
			request.setPoiList(poiList);
			recipients = integrationService.getRecipients(request);
			log.info(" recipient list : " + recipients);
			if (recipients.getStatus().getStatusCode().equals("200")) {
				DisseminationChannel disseminationChannel = recipients.getDisseminationChannel();
				mobileChannelResource = disseminationChannel.getMobileChannel();
			} else if (recipients.getStatus().getStatusCode().equals("400")) {
				throw new RuntimeException("Request data invalid");
			} else if (recipients.getStatus().getStatusCode().equals("500")) {
				throw new RuntimeException("Exception in server side");
			}

			if (!mobileChannelResource.isEmpty()) {
				log.info("The integration service returned all users!");
				for (MobileChannelResource user : mobileChannelResource) {
					if (StringUtils.isNotEmpty(user.getTokenId()) && StringUtils.isNotEmpty(user.getDeviceType())) {
						MobileGatewayDeviceResource device = new MobileGatewayDeviceResource();
						device.setDeviceId(user.getDeviceId());
						device.setDeviceType(user.getDeviceType());
						device.setTokenId(user.getTokenId());
						deviceList.add(device);
					}
				}
			}
		} catch (Exception ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			throw new RuntimeException(ex.getMessage());
		}

		return deviceList;
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
