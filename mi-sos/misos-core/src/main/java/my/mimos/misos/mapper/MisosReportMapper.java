package my.mimos.misos.mapper;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ma.glasnost.orika.MapperFactory;
import my.mimos.misos.domain.entity.trx.MisosReportView;
import my.mimos.misos.domain.resource.SearchChannelMessageResource;
/**
 * 
 * @author beaula.fernandez
 *
 */

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Component
public class MisosReportMapper extends MapperBase {

	@Override
	protected void configure(MapperFactory factory) {
		factory.classMap(SearchChannelMessageResource.class, MisosReportView.class)
				.field("iocMessageId", "iocMessageId")
				.field("messageContent", "messageContent")
				.field("channelType", "targetChannel")
				.field("severityLevel", "severityLevel")
				.field("publishStatus", "publishStatus")
				.field("publishDate", "publishDate")
				.field("recipientCount", "recipientCount")
				.field("notificationType", "notificationType")
				.field("targetGroup", "targetGroup")
				.field("createdDate", "createdDate")
				.field("originator", "originator")
				.register();
				mapperFacade = factory.getMapperFacade();
	}

}
