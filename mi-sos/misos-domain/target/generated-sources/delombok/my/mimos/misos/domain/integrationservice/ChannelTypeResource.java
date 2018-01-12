// Generated by delombok at Fri Jan 12 10:14:29 SGT 2018
package my.mimos.misos.domain.integrationservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChannelTypeResource {
	@JsonProperty("TargetChannelTypeCode")
	private String targetChannelTypeCode;
	@JsonProperty("TargetChannelType")
	private String targetChannelType;
	@JsonProperty("Status")
	private String status;

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getTargetChannelTypeCode() {
		return this.targetChannelTypeCode;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getTargetChannelType() {
		return this.targetChannelType;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getStatus() {
		return this.status;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setTargetChannelTypeCode(final String targetChannelTypeCode) {
		this.targetChannelTypeCode = targetChannelTypeCode;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setTargetChannelType(final String targetChannelType) {
		this.targetChannelType = targetChannelType;
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
		return "ChannelTypeResource(targetChannelTypeCode=" + this.getTargetChannelTypeCode() + ", targetChannelType=" + this.getTargetChannelType() + ", status=" + this.getStatus() + ")";
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof ChannelTypeResource)) return false;
		final ChannelTypeResource other = (ChannelTypeResource) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$targetChannelTypeCode = this.getTargetChannelTypeCode();
		final java.lang.Object other$targetChannelTypeCode = other.getTargetChannelTypeCode();
		if (this$targetChannelTypeCode == null ? other$targetChannelTypeCode != null : !this$targetChannelTypeCode.equals(other$targetChannelTypeCode)) return false;
		final java.lang.Object this$targetChannelType = this.getTargetChannelType();
		final java.lang.Object other$targetChannelType = other.getTargetChannelType();
		if (this$targetChannelType == null ? other$targetChannelType != null : !this$targetChannelType.equals(other$targetChannelType)) return false;
		final java.lang.Object this$status = this.getStatus();
		final java.lang.Object other$status = other.getStatus();
		if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof ChannelTypeResource;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $targetChannelTypeCode = this.getTargetChannelTypeCode();
		result = result * PRIME + ($targetChannelTypeCode == null ? 43 : $targetChannelTypeCode.hashCode());
		final java.lang.Object $targetChannelType = this.getTargetChannelType();
		result = result * PRIME + ($targetChannelType == null ? 43 : $targetChannelType.hashCode());
		final java.lang.Object $status = this.getStatus();
		result = result * PRIME + ($status == null ? 43 : $status.hashCode());
		return result;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public ChannelTypeResource() {
	}
}