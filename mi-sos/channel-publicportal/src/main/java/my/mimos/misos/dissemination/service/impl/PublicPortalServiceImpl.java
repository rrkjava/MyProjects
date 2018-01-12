/**
 * 
 */
package my.mimos.misos.dissemination.service.impl;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.common.enums.StatusType;
import my.mimos.misos.dissemination.config.PublicPortalConfig;
import my.mimos.misos.dissemination.service.PublicPortalService;
import my.mimos.misos.domain.channel.PublicPortalBodyRequest;
import my.mimos.misos.domain.channel.PublicPortalChannelRequestResource;
import my.mimos.misos.domain.channel.PublicPortalChannelResponseResource;
import my.mimos.misos.domain.channel.PublicPortalHeaderRequest;
import my.mimos.misos.domain.channel.PublicPortalRequest;
import my.mimos.misos.mapper.ChannelMapper;

/**
 * @author krishna.redabotu
 *
 */
@Log4j
@Component
public class PublicPortalServiceImpl implements PublicPortalService {

	@Autowired
	PublicPortalConfig config;
	
	@Autowired
	ChannelMapper mapper;
	
	@Override
	public PublicPortalChannelResponseResource sendToPublicPortal(PublicPortalChannelRequestResource req) {

		PublicPortalChannelResponseResource res = new PublicPortalChannelResponseResource();

		try {
			Date date = new Date();
			Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(date);
			
			RestTemplate rest = new RestTemplate();
			String url = config.getUrl() + config.getService();

			// MTSB service header creation
			PublicPortalHeaderRequest header = new PublicPortalHeaderRequest();
			header.setDateTime(dateString);
			header.setRequestId(req.getMessageId());
			header.setTransCode("DISSEMINATION");

			// MTSB service body creation
			PublicPortalBodyRequest body = new PublicPortalBodyRequest();
			body.setId("");
			body.setIndicator("2");

			// MTSB service DisseminationRequest creation
			req.setMessage(StringUtils.replace(req.getMessage(), "<br>", "\n"));
			PublicPortalChannelRequestResource request = mapper.getMapperFacade().map(req,PublicPortalChannelRequestResource.class);
			request.setStatus("");
			request.setStatusType("");
		
			//change in request format
			PublicPortalRequest requestBody = new PublicPortalRequest();
			requestBody.setDisseminationRequest(request);
			requestBody.setTransactionFrom("mimos");
			requestBody.setMethodType(2);
			
			//send request to MTSB public portal
			String portalRequest = new ObjectMapper().writeValueAsString(requestBody);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(portalRequest, headers);
			disableSslVerification();
			log.info(" request to public portal : "+ portalRequest );
			HttpEntity<String> response = rest.exchange(url, HttpMethod.POST, entity, String.class);
			
			//Reading MTSB service response in Json
			JsonNode responseJson = new ObjectMapper().readTree(response.getBody());
			
			int status = 0;
			String statusCode = null;
			
			if( null != responseJson.get("Status")){
				status = responseJson.get("Status").asInt();
			}
						
			if( null != responseJson.get("Code")){
				statusCode = responseJson.get("Code").asText();
				if(statusCode.equalsIgnoreCase("200")){
					res.setStatus("public portal service request successful.");
					res.setStatusCode("S0010");
					res.setStatusType(StatusType.SUCCESS);
					res.setCode(statusCode);
					if( null != responseJson.get("Desc")){
						res.setDesc(responseJson.get("Desc").asText());
					}
				}
			}

			if (status == 1) {
				res.setStatus("public portal service request successful.");
				res.setStatusCode("S0010");
				res.setStatusType(StatusType.SUCCESS);
			} else if (status == 2) {
				throw new IllegalStateException("2");
			} else if (status == 3) {
				throw new IllegalStateException("3");
			} else if (status == 4) {
				throw new IllegalStateException("4");
			} else if (status == 5) {
				throw new IllegalStateException("5");
			} else if (status == -101) {
				throw new IllegalStateException("-101");
			} else if (status == -102) {
				throw new IllegalStateException("-102");
			}

		} catch (Exception e) {
			e.printStackTrace();
			String errorCode=e.getMessage();
			
			if ("2".equals(errorCode)) {
				res.setStatus("public portal service unreachable.");
				res.setStatusCode("E0020");
				res.setStatusType(StatusType.ERROR);
			} else if ("3".equals(errorCode)) {
				res.setStatus("public portal error occurred when searching.");
				res.setStatusCode("E0030");
				res.setStatusType(StatusType.ERROR);
			}else if ("4".equals(errorCode)){
				res.setStatus("public portal invalid request body.");
				res.setStatusCode("E0021");
				res.setStatusType(StatusType.ERROR);
			}else if ("5".equals(errorCode)){
				res.setStatus("public portal  You are not authenticated.");
				res.setStatusCode("E0010");
				res.setStatusType(StatusType.ERROR);
			} else if(errorCode.equals("-101")){
				res.setStatus("public portal Message header not specified.");
				res.setStatusCode("E0021");
				res.setStatusType(StatusType.ERROR);
			} else if(errorCode.equals("-102")){
				res.setStatus("public portal Message header not specified.");
				res.setStatusCode("E0021");
				res.setStatusType(StatusType.ERROR);
			} else {
				res.setStatus("public portal not reaching.");
				res.setStatusCode("E0021");
				res.setStatusType(StatusType.ERROR);
			}
			log.error(ExceptionUtils.getStackTrace(e));
		} finally {
			res.setChannelType(req.getTargetChannelTypeCode());
			res.setTargetUserGroups(req.getTargetUserGroups());	
			res.setRecipientCount("1");
		}
		return res;
	}
	
	public static void disableSslVerification() {
	    try
	    {
	        // Create a trust manager that does not validate certificate chains
	        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	            public void checkClientTrusted(X509Certificate[] certs, String authType) {
	            }
	            public void checkServerTrusted(X509Certificate[] certs, String authType) {
	            }
	        }
	        };

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
