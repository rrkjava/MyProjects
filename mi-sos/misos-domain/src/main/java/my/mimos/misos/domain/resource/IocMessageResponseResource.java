package my.mimos.misos.domain.resource;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class IocMessageResponseResource extends BaseResponseResource {
	
	List<IocMessageResource> iocMessages;

}
