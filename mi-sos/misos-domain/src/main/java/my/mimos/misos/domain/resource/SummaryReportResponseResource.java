/**
 * 
 */
package my.mimos.misos.domain.resource;

import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author krishna.redabotu
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
public class SummaryReportResponseResource extends BaseResponseResource {
	
	private List<SummaryReportResource> summaryReportList;
	private String fromDate;
	private String toDate;
	
}
