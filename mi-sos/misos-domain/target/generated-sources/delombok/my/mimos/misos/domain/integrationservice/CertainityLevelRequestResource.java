// Generated by delombok at Fri Jan 12 10:14:29 SGT 2018
package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CertainityLevelRequestResource extends BaseRequestResource {
	@JsonProperty("Code")
	private String certainityLevelCode;

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getCertainityLevelCode() {
		return this.certainityLevelCode;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setCertainityLevelCode(final String certainityLevelCode) {
		this.certainityLevelCode = certainityLevelCode;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public java.lang.String toString() {
		return "CertainityLevelRequestResource(certainityLevelCode=" + this.getCertainityLevelCode() + ")";
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof CertainityLevelRequestResource)) return false;
		final CertainityLevelRequestResource other = (CertainityLevelRequestResource) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$certainityLevelCode = this.getCertainityLevelCode();
		final java.lang.Object other$certainityLevelCode = other.getCertainityLevelCode();
		if (this$certainityLevelCode == null ? other$certainityLevelCode != null : !this$certainityLevelCode.equals(other$certainityLevelCode)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof CertainityLevelRequestResource;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $certainityLevelCode = this.getCertainityLevelCode();
		result = result * PRIME + ($certainityLevelCode == null ? 43 : $certainityLevelCode.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public CertainityLevelRequestResource() {
	}
}
