package my.mimos.mdc.resources.query;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import my.mimos.mdc.resources.BaseResponseResource;


@Data
@EqualsAndHashCode(callSuper=false)
public class SearchCommentResponseResource extends BaseResponseResource{

	private List<CommentResource> comments; 
}