// Generated by delombok at Fri Jan 12 10:14:29 SGT 2018
package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SeverityLevelResponseResource extends BaseResponseResource {
	@JsonProperty("SeverityLevel")
	private SeverityLevelResource severityLevel;

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public SeverityLevelResource getSeverityLevel() {
		return this.severityLevel;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setSeverityLevel(final SeverityLevelResource severityLevel) {
		this.severityLevel = severityLevel;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public java.lang.String toString() {
		return "SeverityLevelResponseResource(severityLevel=" + this.getSeverityLevel() + ")";
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof SeverityLevelResponseResource)) return false;
		final SeverityLevelResponseResource other = (SeverityLevelResponseResource) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$severityLevel = this.getSeverityLevel();
		final java.lang.Object other$severityLevel = other.getSeverityLevel();
		if (this$severityLevel == null ? other$severityLevel != null : !this$severityLevel.equals(other$severityLevel)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof SeverityLevelResponseResource;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $severityLevel = this.getSeverityLevel();
		result = result * PRIME + ($severityLevel == null ? 43 : $severityLevel.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public SeverityLevelResponseResource() {
	}
}