package my.mimos.mdc.resources.query;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.resources.BaseResponseResource;

@Data
@EqualsAndHashCode(callSuper=false)
public class QueryDetailResource extends BaseResponseResource{
	
	private QueryResource query;
	private List<AckResource> acknowledgments;
	private List<CommentResource> comments;
	private List<QueryResponseResource> responses;

}
