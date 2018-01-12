// Generated by delombok at Fri Jan 12 10:14:29 SGT 2018
package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CertainityLevelResource {
	@JsonProperty("CertaintyLevel")
	private String certainityLevel;
	@JsonProperty("CertaintyLevelCode")
	private String certainityLevelCode;
	@JsonProperty("Status")
	private String status;

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getCertainityLevel() {
		return this.certainityLevel;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getCertainityLevelCode() {
		return this.certainityLevelCode;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getStatus() {
		return this.status;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setCertainityLevel(final String certainityLevel) {
		this.certainityLevel = certainityLevel;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setCertainityLevelCode(final String certainityLevelCode) {
		this.certainityLevelCode = certainityLevelCode;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setStatus(final String status) {
		this.status = status;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public java.lang.String toString() {
		return "CertainityLevelResource(certainityLevel=" + this.getCertainityLevel() + ", certainityLevelCode=" + this.getCertainityLevelCode() + ", status=" + this.getStatus() + ")";
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof CertainityLevelResource)) return false;
		final CertainityLevelResource other = (CertainityLevelResource) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$certainityLevel = this.getCertainityLevel();
		final java.lang.Object other$certainityLevel = other.getCertainityLevel();
		if (this$certainityLevel == null ? other$certainityLevel != null : !this$certainityLevel.equals(other$certainityLevel)) return false;
		final java.lang.Object this$certainityLevelCode = this.getCertainityLevelCode();
		final java.lang.Object other$certainityLevelCode = other.getCertainityLevelCode();
		if (this$certainityLevelCode == null ? other$certainityLevelCode != null : !this$certainityLevelCode.equals(other$certainityLevelCode)) return false;
		final java.lang.Object this$status = this.getStatus();
		final java.lang.Object other$status = other.getStatus();
		if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof CertainityLevelResource;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $certainityLevel = this.getCertainityLevel();
		result = result * PRIME + ($certainityLevel == null ? 43 : $certainityLevel.hashCode());
		final java.lang.Object $certainityLevelCode = this.getCertainityLevelCode();
		result = result * PRIME + ($certainityLevelCode == null ? 43 : $certainityLevelCode.hashCode());
		final java.lang.Object $status = this.getStatus();
		result = result * PRIME + ($status == null ? 43 : $status.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public CertainityLevelResource() {
	}
}
