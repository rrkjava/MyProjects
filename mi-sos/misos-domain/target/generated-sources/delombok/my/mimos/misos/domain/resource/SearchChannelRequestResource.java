// Generated by delombok at Fri Jan 12 10:14:29 SGT 2018
/**
 * 
 */
package my.mimos.misos.domain.resource;

/**
 * @author krishna.redabotu
 */
public class SearchChannelRequestResource extends BaseResource {
	private String iocMessageId;
	private String channelType;
	private String severityLevel;
	private String fromDate;
	private String toDate;

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getIocMessageId() {
		return this.iocMessageId;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getChannelType() {
		return this.channelType;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getSeverityLevel() {
		return this.severityLevel;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getFromDate() {
		return this.fromDate;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getToDate() {
		return this.toDate;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setIocMessageId(final String iocMessageId) {
		this.iocMessageId = iocMessageId;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setChannelType(final String channelType) {
		this.channelType = channelType;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setSeverityLevel(final String severityLevel) {
		this.severityLevel = severityLevel;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setFromDate(final String fromDate) {
		this.fromDate = fromDate;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setToDate(final String toDate) {
		this.toDate = toDate;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public java.lang.String toString() {
		return "SearchChannelRequestResource(iocMessageId=" + this.getIocMessageId() + ", channelType=" + this.getChannelType() + ", severityLevel=" + this.getSeverityLevel() + ", fromDate=" + this.getFromDate() + ", toDate=" + this.getToDate() + ")";
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof SearchChannelRequestResource)) return false;
		final SearchChannelRequestResource other = (SearchChannelRequestResource) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$iocMessageId = this.getIocMessageId();
		final java.lang.Object other$iocMessageId = other.getIocMessageId();
		if (this$iocMessageId == null ? other$iocMessageId != null : !this$iocMessageId.equals(other$iocMessageId)) return false;
		final java.lang.Object this$channelType = this.getChannelType();
		final java.lang.Object other$channelType = other.getChannelType();
		if (this$channelType == null ? other$channelType != null : !this$channelType.equals(other$channelType)) return false;
		final java.lang.Object this$severityLevel = this.getSeverityLevel();
		final java.lang.Object other$severityLevel = other.getSeverityLevel();
		if (this$severityLevel == null ? other$severityLevel != null : !this$severityLevel.equals(other$severityLevel)) return false;
		final java.lang.Object this$fromDate = this.getFromDate();
		final java.lang.Object other$fromDate = other.getFromDate();
		if (this$fromDate == null ? other$fromDate != null : !this$fromDate.equals(other$fromDate)) return false;
		final java.lang.Object this$toDate = this.getToDate();
		final java.lang.Object other$toDate = other.getToDate();
		if (this$toDate == null ? other$toDate != null : !this$toDate.equals(other$toDate)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof SearchChannelRequestResource;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $iocMessageId = this.getIocMessageId();
		result = result * PRIME + ($iocMessageId == null ? 43 : $iocMessageId.hashCode());
		final java.lang.Object $channelType = this.getChannelType();
		result = result * PRIME + ($channelType == null ? 43 : $channelType.hashCode());
		final java.lang.Object $severityLevel = this.getSeverityLevel();
		result = result * PRIME + ($severityLevel == null ? 43 : $severityLevel.hashCode());
		final java.lang.Object $fromDate = this.getFromDate();
		result = result * PRIME + ($fromDate == null ? 43 : $fromDate.hashCode());
		final java.lang.Object $toDate = this.getToDate();
		result = result * PRIME + ($toDate == null ? 43 : $toDate.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public SearchChannelRequestResource() {
	}
}
