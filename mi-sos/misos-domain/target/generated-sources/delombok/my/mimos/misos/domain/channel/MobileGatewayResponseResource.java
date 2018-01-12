// Generated by delombok at Fri Jan 12 10:14:29 SGT 2018
/**
 * 
 */
package my.mimos.misos.domain.channel;

import my.mimos.misos.common.enums.StatusType;

/**
 * @author Shaiful Hisham Mat Jali
 */
public class MobileGatewayResponseResource {
	protected StatusType statusType;
	protected String statusCode;
	protected String status;

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public MobileGatewayResponseResource() {
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public StatusType getStatusType() {
		return this.statusType;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getStatusCode() {
		return this.statusCode;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public String getStatus() {
		return this.status;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setStatusType(final StatusType statusType) {
		this.statusType = statusType;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setStatusCode(final String statusCode) {
		this.statusCode = statusCode;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public void setStatus(final String status) {
		this.status = status;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public boolean equals(final java.lang.Object o) {
		if (o == this) return true;
		if (!(o instanceof MobileGatewayResponseResource)) return false;
		final MobileGatewayResponseResource other = (MobileGatewayResponseResource) o;
		if (!other.canEqual((java.lang.Object) this)) return false;
		final java.lang.Object this$statusType = this.getStatusType();
		final java.lang.Object other$statusType = other.getStatusType();
		if (this$statusType == null ? other$statusType != null : !this$statusType.equals(other$statusType)) return false;
		final java.lang.Object this$statusCode = this.getStatusCode();
		final java.lang.Object other$statusCode = other.getStatusCode();
		if (this$statusCode == null ? other$statusCode != null : !this$statusCode.equals(other$statusCode)) return false;
		final java.lang.Object this$status = this.getStatus();
		final java.lang.Object other$status = other.getStatus();
		if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
		return true;
	}

	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	protected boolean canEqual(final java.lang.Object other) {
		return other instanceof MobileGatewayResponseResource;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final java.lang.Object $statusType = this.getStatusType();
		result = result * PRIME + ($statusType == null ? 43 : $statusType.hashCode());
		final java.lang.Object $statusCode = this.getStatusCode();
		result = result * PRIME + ($statusCode == null ? 43 : $statusCode.hashCode());
		final java.lang.Object $status = this.getStatus();
		result = result * PRIME + ($status == null ? 43 : $status.hashCode());
		return result;
	}

	@java.lang.Override
	@java.lang.SuppressWarnings("all")
	@javax.annotation.Generated("lombok")
	public java.lang.String toString() {
		return "MobileGatewayResponseResource(statusType=" + this.getStatusType() + ", statusCode=" + this.getStatusCode() + ", status=" + this.getStatus() + ")";
	}
}