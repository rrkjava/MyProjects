package my.mimos.mdc.resources.broadcast;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.resources.BaseResponseResource;

@Data
@EqualsAndHashCode(callSuper=false)
public class SendBroadCastResponseResource extends BaseResponseResource{

	private Long responseId;
}
