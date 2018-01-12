/**
 * 
 */
package my.mimos.misos.web.service.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;

import lombok.extern.log4j.Log4j;
import my.mimos.misos.common.enums.StatusType;
import my.mimos.misos.domain.parser.Channel;
import my.mimos.misos.domain.parser.Channels;
import my.mimos.misos.domain.parser.Header;
import my.mimos.misos.domain.parser.Rule;
import my.mimos.misos.domain.resource.DisseminationResponseResource;
import my.mimos.misos.web.service.RuleService;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
@Log4j
@Component
public class RuleServiceImpl implements RuleService {

	private Map<String, Header> headerRule;
	private Map<String, Channel> channelRule;

	/**
	 * 
	 */
	public RuleServiceImpl() throws RuntimeException {
		super();
		// TODO Auto-generated constructor stub

		try {

			log.debug("Loading rule file...");

			ClassLoader classLoader = this.getClass().getClassLoader();
			File ruleFile = new File(classLoader.getResource("rule.json").getFile());

			ObjectMapper mapper = new ObjectMapper();
			Rule rule = mapper.readValue(ruleFile, Rule.class);

			// Create the header map
			this.headerRule = new LinkedHashMap<String, Header>();
			for (Header header : rule.getHeader()) {
				headerRule.put(header.getAttributeName(), header);
			}

			// Create the channel map
			this.channelRule = new LinkedHashMap<String, Channel>();
			for (Channels channels : rule.getChannels()) {
				for (Channel channel : channels.getChannel()) {
					channelRule.put(channel.getAttributeName(), channel);
				}
			}

			log.debug("Loading rule file completed.");

		} catch (Exception e) {

			throw new RuntimeException(e);

		} finally {

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * my.mimos.misos.parser.service.RuleService#parseJson(my.mimos.misos.domain
	 * .resource.DisseminationRequestResource)
	 */
	@Override
	public void parseJson(String jsonString, DisseminationResponseResource response) throws RuntimeException {
		// TODO Auto-generated method stub

		try {

			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(jsonString.getBytes());

			Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
			while (iterator.hasNext()) {

				Map.Entry<String, JsonNode> item = iterator.next();
				
				Header header = headerRule.get(item.getKey());
				log.debug(header);
				
				// Validate
				if (item.getValue().isArray()) {
				
					for (JsonNode channelNode : item.getValue()) {
						Iterator<Map.Entry<String, JsonNode>> channelIterator = channelNode.fields();
						
						while (channelIterator.hasNext()) {
							
							Map.Entry<String, JsonNode> channelItem = channelIterator.next();
							Channel channel = channelRule.get(channelItem.getKey());
							validate(channel, channelItem);			
							
						}
					}
					
				} else {
					validate(header, item);
				}
				
			}

		} catch (RuntimeException re) {
			
			response.setStatusType(StatusType.ERROR);
			response.setStatusCode("E0030");			
			response.setStatus(re.getMessage());
			
			throw re;
			
		} catch (Exception e) {

			throw new RuntimeException(e);

		} finally {

		}

		//return response;
	}

	private void validate(Header header, Map.Entry<String, JsonNode> node) throws Exception {

		try {

			log.debug("Key : " + node.getKey() + "; Value : " + node.getValue());
			boolean mandatory = false;

			if(header != null) {

				if(header.getStatus().equalsIgnoreCase("M")){
					mandatory = true;
				}
				else{
					mandatory= false;
				}

				if(node.getValue()!=null && (!node.getValue().toString().equalsIgnoreCase("null"))){

					if(header.getDataType().equals("String")) {
						if(header.getRegex() != null &&  header.getRegex().length() > 0) {

							Pattern pattern = Pattern.compile(header.getRegex());
							TextNode textNode = (TextNode) node.getValue();

							log.debug("Matching " + textNode.asText() + " with " + header.getRegex());

							Matcher matcher = pattern.matcher(textNode.asText());
							if (!matcher.find()) {
								log.debug("Not match.");
								throw new RuntimeException(node.getKey() + " format is invalid.");
							} else {
								log.debug("Match.");
							}
						}
					} else if(header.getDataType().equals("Timestamp")) {

						try {

							SimpleDateFormat dateFormat = new SimpleDateFormat(header.getFormat());
							TextNode textNode = (TextNode) node.getValue();
							dateFormat.parse(textNode.asText());

						} catch (NullPointerException ne) {

							throw new RuntimeException(node.getKey() + " pattern is null.");

						} catch (IllegalArgumentException  ie) {

							throw new RuntimeException(node.getKey() + " pattern is invalid");

						} catch (ParseException pe) {

							throw new RuntimeException(node.getKey() + " format is not a valid date.");

						}
					}

				}else{
					if(mandatory == true){
						throw new RuntimeException(node.getKey().toString() + " is mandatory!");
					}
				}
			}


		} finally {

		}
	}
}
